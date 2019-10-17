package repository;

import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import utils.DataTimePeriod;

import java.util.List;

public interface LogsRepository {

    List<String> getFileLogs(DataTimePeriod period, String username, String message, int threadsCount)
            throws GetFilesPathsException, FilterLogsThreadException;

}
