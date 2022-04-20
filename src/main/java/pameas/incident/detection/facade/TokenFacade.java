package pameas.incident.detection.facade;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

public class TokenFacade {

    public static String getAccessToken() throws UnirestException {
        HttpResponse<String> response = Unirest.post("https://dss1.aegean.gr/auth/realms/palaemon/protocol/openid-connect/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("client_id=palaemonRegistration&client_secret=bdbbb8d5-3ee7-4907-b95c-2baae17bd10f&grant_type=client_credentials&scope=openid")
                .asString();

        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject.get("access_token"));
    }
}
