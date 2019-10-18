package service.impl;

import entity.LogEntity;
import exception.FilterLogsThreadException;
import exception.GetFilesPathsException;
import repository.LogsRepository;
import repository.impl.LogsRepositoryImpl;
import service.StatisticService;
import utils.DataTimePeriod;
import utils.LogLineParser;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticServiceImpl implements StatisticService {

    public static final String MONTH_PATTERN = "yyyy/MM";
    public static final String DAY_PATTERN = "yyyy/MM/dd";
    public static final String HOUR_PATTERN = "yyyy/MM/dd-HH";

    private LogsRepository logsRepository = new LogsRepositoryImpl();

    @Override
    public void getStatistic(String groupingParam, DataTimePeriod period, String username,
                             String message, int threadsCount) throws GetFilesPathsException, FilterLogsThreadException, IllegalAccessException {

        Map<String, List<LogEntity>> logMap = groupByParam(groupingParam, period, username, message, threadsCount);

        System.out.format("%16s%7s\n", groupingParam, "count");
        System.out.format("%16s%7s\n", "----------------", "-----");
        for (Map.Entry<String, List<LogEntity>> entry : logMap.entrySet()) {
            System.out.format("%16s%7s\n", entry.getKey(), entry.getValue().size());
        }
    }


    private Map<String, List<LogEntity>> groupByParam(String groupingParam, DataTimePeriod period, String username,
                                                      String message, int threadsCount) throws GetFilesPathsException, FilterLogsThreadException, IllegalAccessException {

        List<String> stringList = logsRepository.getFileLogs(period, username, message, threadsCount);

        List<LogEntity> entityList = stringList.stream()
                .map(LogLineParser::toEntity)
                .collect(Collectors.toList());

        switch (groupingParam) {
            case "username":
                return entityList.stream()
                        .collect(Collectors.groupingBy(LogEntity::getUsername));
            case "hour":
                return entityList.stream()
                        .collect(Collectors.groupingBy(logObject -> logObject.getDateTime().format(DateTimeFormatter.ofPattern(HOUR_PATTERN))));
            case "day":
                return entityList.stream()
                        .collect(Collectors.groupingBy(logObject -> logObject.getDateTime().format(DateTimeFormatter.ofPattern(DAY_PATTERN))));
            case "month":
                return entityList.stream()
                        .collect(Collectors.groupingBy(logObject -> logObject.getDateTime().format(DateTimeFormatter.ofPattern(MONTH_PATTERN))));
            default:
                throw new IllegalAccessException("Wrong input parameter");
        }
    }
}
