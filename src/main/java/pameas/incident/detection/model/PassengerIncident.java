package pameas.incident.detection.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PassengerIncident {

    private String id;
    private String passengerName;
    private String passengerSurname;
    private String healthIssues;
    private String mobilityIssues;
    private String pregnancyStatus;
    private String assignedCrewMemberId;
    private List<String> preferredLanguage;
    private String geofence;
    private String deck;
    private String timestamp;
    private String status;
    @JsonProperty("x_loc")
    private String x_loc;
    @JsonProperty("y_loc")
    private String y_loc;
    private String macAddress;
    private String type;
    private String incidentId;
}
