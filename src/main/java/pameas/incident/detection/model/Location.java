package pameas.incident.detection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Location implements Serializable {

    public String errorLevel;
    public String isAssociated;
    public String campusId;
    public String buildingId;
    public String floorId;
    public String hashedMacAddress;
    public String geofenceId;
    public List<String> geofenceNames;
    public String timestamp;
    @JsonProperty("xLocation")
    public String xLocation;
    @JsonProperty("yLocation")
    public String yLocation;
}
