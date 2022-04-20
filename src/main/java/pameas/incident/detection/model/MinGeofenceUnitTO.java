package pameas.incident.detection.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinGeofenceUnitTO {

    private String gfId;
    private String gfName;
    private String timestamp;
}
