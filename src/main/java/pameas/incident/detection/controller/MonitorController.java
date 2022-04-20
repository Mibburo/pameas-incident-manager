package pameas.incident.detection.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pameas.incident.detection.model.Location;
import pameas.incident.detection.service.IncidentManagementService;

@Slf4j
@RestController
public class MonitorController {

    @Autowired
    private IncidentManagementService incidentManagementService;

    @RequestMapping("/monitorPassenger")
    public void monitorPassenger(@RequestBody Location location) throws UnirestException {
        incidentManagementService.monitorPassengers(location);
    }
}
