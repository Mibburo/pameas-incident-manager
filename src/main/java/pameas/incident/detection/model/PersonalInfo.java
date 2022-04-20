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
public class PersonalInfo {

    private String name;
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String personalId;
    private String ticketNumber;
    private List<TicketInfo> ticketInfo;
    private Boolean crew;
    private String embarkationPort;
    private String disembarkationPort;
    private String email;
    private String postalAddress;
    private String emergencyContact;
    private String countryOfResidence;
    private String medicalCondition;
    private String mobilityIssues;
    private String prengencyData;
    private String role;
    private String emergencyDuty;
    private List<String> preferredLanguage;
    private Boolean inPosition;
    private String assignmentStatus;
    private List<DutySchedule> dutyScheduleList;
    private String assignedMusteringStation;
}
