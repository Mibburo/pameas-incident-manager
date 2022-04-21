package pameas.incident.detection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinLocationTO implements Serializable {

    @JsonProperty("hashedMacAddress")
    private String hashedMacAddress;
    @JsonProperty("location")
    private MinLocationUnitTO location;
    @JsonProperty("geofence")
    private MinGeofenceUnitTO geofence;
    @JsonProperty("gfName")
    private String gfName;

}
