package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneralInfo {

    private String name;
    private String surname;
    private String gender;
    private String identifier;
    private String age;
    private List<TicketInfo> connectedPassengers;
    private String ticketNumber;
    private String email;
    private String role;
    private Boolean crew;
    private String embarkation_port;
    private String disembarkation_port;
    private String postal_address;
    private String emergency_contact_details;
    private String country_of_residence;
    private String medical_condnitions;
    private String mobility_issues;
    private String pregnency_data;
    private String emergency_duty;
    private List<DutySchedule> duty_schedule;
    private List<String> preferred_language;
    private boolean in_position;
    private String assignment_status;
    private String assigned_muster_station;
}
