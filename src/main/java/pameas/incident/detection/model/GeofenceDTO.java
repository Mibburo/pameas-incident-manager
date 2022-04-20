package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeofenceDTO {

    private String id;
    private String gfName;
    private String deck;
    private String status;
    private Boolean musteringStation;
}
