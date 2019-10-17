package service;

import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import utils.DataTimePeriod;

import java.io.IOException;
import java.nio.file.Path;

public interface FilterService {

    void getFileWithFilteredLogs(Path path, DataTimePeriod period, String username, String message, int threadsCount) throws GetFilesPathsException, FilterLogsThreadException, IOException;

}
