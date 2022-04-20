package pameas.incident.detection.facade;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pameas.incident.detection.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pameas.incident.detection.utils.Constants.*;


@Slf4j
@Service
public class DbProxyFacade {

    public List<MusteringDetails> getPassengerMusteringDetails() throws UnirestException {
        String accessToken = TokenFacade.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List<MusteringDetails>> response = restTemplate.exchange( MUSTER_DETAILS_URL, HttpMethod.GET, request,
                new ParameterizedTypeReference<List<MusteringDetails>>() {}, 1);

        return response.getBody();
    }

    public GeofenceStatus getGeofenceStatus() throws UnirestException {
        String accessToken = TokenFacade.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<GeofenceStatus> response = restTemplate.exchange( GEOFENCE_STATUS_URL, HttpMethod.GET, request, GeofenceStatus.class, 1);

        return response.getBody();
    }

    public PassengerDTO getPerson(String id) throws UnirestException {
        String accessToken = TokenFacade.getAccessToken();

        Map<String, String> params = new HashMap<>();
        params.put("id", id);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders(accessToken);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<PassengerDTO> response = restTemplate.exchange( GET_PERSON_URL, HttpMethod.GET, request, PassengerDTO.class, params);

        return response.getBody();
    }

    public List<PassengerDTO> getPassengers() throws UnirestException {
        String accessToken = TokenFacade.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = setHeaders(accessToken);
        HttpEntity request = new HttpEntity(headers);
        ResponseEntity<List<PassengerDTO>> response = restTemplate.exchange( GET_PASSENGERS_URL, HttpMethod.GET, request,
                new ParameterizedTypeReference<List<PassengerDTO>>() {}, 1);

        return response.getBody();
    }

    public void updateGeofence(GeofenceDTO geofenceDTO) throws UnirestException, IOException, InterruptedException {
        String accessToken = TokenFacade.getAccessToken();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(geofenceDTO);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GEOFENCE_UPDATE_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+accessToken)
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

    public void declarePassengerIncident(PassengerIncident passengerIncident) throws UnirestException, IOException, InterruptedException {
        String accessToken = TokenFacade.getAccessToken();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(passengerIncident);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(DECLARE_PASSENGER_URL))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+accessToken)
                .method("POST", HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

    }

    private HttpHeaders setHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);

        return headers;
    }
}
