package pameas.incident.detection.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PassengerDTO {

    private String id;
    private PersonalInfo personalInfo;
    private NetworkInfo networkInfo;
    private LocationInfo locationInfo;
}
