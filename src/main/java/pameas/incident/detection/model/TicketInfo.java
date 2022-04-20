package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TicketInfo {

    private String name;
    private String surname;
    private String dateOfBirth;
    private String gender;
    private String age;
    private String ticketNumber;
}
