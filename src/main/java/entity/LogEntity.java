package entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogEntity {

    private LocalDateTime dateTime;
    private String username;
    private String message;

}
