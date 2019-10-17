package service.impl;

import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import repository.LogsRepository;
import repository.impl.LogsRepositoryImpl;
import service.FilterService;
import utils.DataTimePeriod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilterServiceImpl implements FilterService {

    private LogsRepository logsRepository = new LogsRepositoryImpl();

    @Override
    public void getFileWithFilteredLogs(Path path, DataTimePeriod period, String username, String message, int threadsCount)
            throws GetFilesPathsException, FilterLogsThreadException, IOException {
        Files.write(path, logsRepository.getFileLogs(period, username, message, threadsCount));
    }

}
