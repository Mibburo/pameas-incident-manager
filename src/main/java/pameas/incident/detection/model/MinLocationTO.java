package pameas.incident.detection.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinLocationTO implements Serializable {

    private String hashedMacAddress;
    private MinLocationUnitTO location;
    private MinGeofenceUnitTO geofence;
    private String gfName;

}
