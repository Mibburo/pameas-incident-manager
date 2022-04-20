package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DutySchedule {

    private LocalDateTime dutyStartDateTime;
    private LocalDateTime dutyEndDateTime;
}
