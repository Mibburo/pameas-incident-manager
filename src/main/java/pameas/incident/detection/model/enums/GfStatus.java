package pameas.incident.detection.model.enums;

public enum GfStatus {

    OPEN("OPEN"),
    BLOCKED("BLOCKED"),
    ALMOST_BLOCKED("ALMOST_BLOCKED");

    private String value;

    private GfStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
