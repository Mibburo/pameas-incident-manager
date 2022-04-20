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
public class CommunicationDetails {
    private String macAddress;
    private List<String> preferredLanguage;
}
