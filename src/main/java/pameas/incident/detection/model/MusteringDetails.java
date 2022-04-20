package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class MusteringDetails {

    private GeneralInfo general_info;
    private LastKnownLocation last_known_location;
    private CommunicationDetails communication_details;
}
