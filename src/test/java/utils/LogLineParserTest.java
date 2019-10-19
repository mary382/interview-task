package utils;

import entity.LogEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(MockitoJUnitRunner.class)
public class LogLineParserTest {

    @Mock
    private LogEntity logEntity;

    @Test
    public void doEntityFromLogLinePositiveTest() {

        var inputLogLine = "2019-10-16T06:45:23;test_user;test_message";

        var output = logEntity.builder()
                .dateTime(LocalDateTime.parse("2019-10-16T06:45:23"))
                .message("test_message")
                .username("test_user")
                .build();

        var expected = LogLineParser.toEntity(inputLogLine);

        assertEquals("Check parser for parsing string to Object Positive Test", expected, output);
    }

    @Test
    public void doEntityFromLogLineNegativeTest() {

        var inputLogLine = "2019-10-16T06:45:23;test_user;test_message";

        var output = logEntity;

        var expected = LogLineParser.toEntity(inputLogLine);

        assertNotEquals("Check parser for parsing string to Object Negative Test", expected, output);
    }

}

