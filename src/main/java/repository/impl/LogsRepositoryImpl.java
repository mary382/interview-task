package repository.impl;

import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import filter.LogFilter;
import repository.LogsRepository;
import repository.PathsRepository;
import utils.DataTimePeriod;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class LogsRepositoryImpl implements LogsRepository {

    private PathsRepository pathsRepository = new PathsRepositoryImpl();

    @Override
    public List<String> getFileLogs(DataTimePeriod period, String username, String message, int threadsCount) throws GetFilesPathsException, FilterLogsThreadException {
        var executorService = Executors.newFixedThreadPool(threadsCount);
        try {
            var paths = pathsRepository.getFilesPaths();
            var futures = getAllFilesWithAllLogs(paths, period, username, message, executorService);
            var filesListWithLogsList = getFilteredFilesWithFilteredLogs(futures);

            return filesListWithLogsList
                    .stream()
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

        } catch (GetFilesPathsException e) {
            throw new GetFilesPathsException("Error to get files paths");
        } catch (InterruptedException | ExecutionException e) {
            throw new FilterLogsThreadException("Thread error with filter files and long");
        } finally {
            executorService.shutdown();
        }
    }

    private List<Future<List<String>>> getAllFilesWithAllLogs(List<Path> paths, DataTimePeriod period, String username,
                                                              String message, ExecutorService executorService) {
        return paths.stream()
                .map(filePath -> new LogFilter(filePath, period, username, message))
                .map(executorService::submit)
                .collect(Collectors.toList());
    }

    private List<List<String>> getFilteredFilesWithFilteredLogs(List<Future<List<String>>> futures) throws ExecutionException, InterruptedException {
        List<List<String>> listOfFilteredFilesWithFilteredLogs = new ArrayList<>();

        for (Future<List<String>> future : futures) {
            listOfFilteredFilesWithFilteredLogs.addAll(Collections.singleton(future.get()));

        }
        return listOfFilteredFilesWithFilteredLogs;
    }

}
