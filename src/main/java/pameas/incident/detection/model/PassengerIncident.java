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

    @JsonProperty("id")
    private String id;
    @JsonProperty("passengerName")
    private String passengerName;
    @JsonProperty("passengerSurname")
    private String passengerSurname;
    @JsonProperty("healthIssues")
    private String healthIssues;
    @JsonProperty("mobilityIssues")
    private String mobilityIssues;
    @JsonProperty("pregnancyStatus")
    private String pregnancyStatus;
    @JsonProperty("assignedCrewMemberId")
    private String assignedCrewMemberId;
    @JsonProperty("preferredLanguage")
    private List<String> preferredLanguage;
    @JsonProperty("geofence")
    private String geofence;
    @JsonProperty("deck")
    private String deck;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("status")
    private String status;
    @JsonProperty("x_loc")
    private String x_loc;
    @JsonProperty("y_loc")
    private String y_loc;
    @JsonProperty("macAddress")
    private String macAddress;
    @JsonProperty("type")
    private String type;
    @JsonProperty("incidentId")
    private String incidentId;

}
