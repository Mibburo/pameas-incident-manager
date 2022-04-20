package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PassengerEvalDTO {

    private String currentTimestamp;
    private String entrTimestamp;
    private String health_issues;
    private String mobility_issues;
    private String pregnancy_status;
    private String geofenceStatus;
    private String macAddress;

}
