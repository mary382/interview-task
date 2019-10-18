package filter;

import exception.ReadFileException;
import lombok.AllArgsConstructor;
import utils.DataTimePeriod;
import utils.LogLineParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@AllArgsConstructor
public class LogFilter implements Callable<List<String>> {

    private Path filePath;
    private DataTimePeriod period;
    private String username;
    private String message;

    @Override
    public List<String> call() throws ReadFileException {
        try {
            return Files.lines(filePath)
                    .filter(logLine -> {
                        if (period.getDateFrom() != null && period.getDateTo() != null) {
                            return LogLineParser.toEntity(logLine).getDateTime().isAfter(period.getDateFrom())
                                    && LogLineParser.toEntity(logLine).getDateTime().isBefore(period.getDateTo());
                        }
                        return true;
                    })
                    .filter(logLine -> LogLineParser.isConformLog(username, LogLineParser.toEntity(logLine).getUsername()))
                    .filter(logLine -> LogLineParser.isConformLog(message, LogLineParser.toEntity(logLine).getMessage()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ReadFileException(String.format("Error reading file from filePath: %s", filePath));
        }
    }

}
