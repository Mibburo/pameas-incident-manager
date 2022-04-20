package pameas.incident.detection.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pameas.incident.detection.model.Location;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LocationMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String hashedMacAddress;
    private List<Location> locations;
    private Boolean hasIncident;

}
