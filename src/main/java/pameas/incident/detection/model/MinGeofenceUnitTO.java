package pameas.incident.detection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinGeofenceUnitTO {

    @JsonProperty("gfId")
    private String gfId;
    @JsonProperty("gfName")
    private String gfName;
    @JsonProperty("timestamp")
    private String timestamp;
}
