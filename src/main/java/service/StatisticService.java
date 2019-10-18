package service;

import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import utils.DataTimePeriod;

public interface StatisticService {

    void getStatistic(String groupingParam, DataTimePeriod period, String username,
                      String message, int threadsCount) throws GetFilesPathsException, FilterLogsThreadException, IllegalAccessException;

}
