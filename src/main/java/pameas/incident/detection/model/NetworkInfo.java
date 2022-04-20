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
public class NetworkInfo {

    private List<DeviceInfo> deviceInfoList;
    private String messagingAppClientId;
}
