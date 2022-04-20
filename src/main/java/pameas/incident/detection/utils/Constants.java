package pameas.incident.detection.utils;

public class Constants {

    public static final String DBPROXY_URL = "http://dss.aegean.gr:8090";
    public static final String MUSTER_DETAILS_URL = DBPROXY_URL + "/getPassengerMusteringDetails";
    public static final String GEOFENCE_STATUS_URL = DBPROXY_URL + "/getGeofenceStatus";
    public static final String GEOFENCE_UPDATE_URL = DBPROXY_URL + "/updateGeofence";
    public static final String DECLARE_PASSENGER_URL = DBPROXY_URL + "/updateGeofence";
    public static final String GET_PASSENGERS_URL = DBPROXY_URL + "/getPassengers";
    public static final String GET_PERSON_URL = DBPROXY_URL + "/getPerson";

    public static final String RTLS_URL = "http://localhost:7011";
    public static final String LOCATION_SERVICE_SPEED_URL = RTLS_URL + "/getPassengerSpeed";

    public static final String RULES_ENGINE_URL = "";
    public static final String EVAL_PASSENGER_URL = RULES_ENGINE_URL + "/evaluatePassenger";

    public static final String NOTIFICATION_TOPIC = "pameas-notification";
    public static final String LOCATION_TOPIC = "pameas-location";
}
