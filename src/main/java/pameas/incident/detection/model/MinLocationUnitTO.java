package pameas.incident.detection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MinLocationUnitTO {

    @JsonProperty("xLocation")
    private String xLocation;
    @JsonProperty("yLocation")
    private String yLocation;
    private String timestamp;
}
