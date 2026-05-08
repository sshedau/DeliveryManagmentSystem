package domain;

public class Zone {
    private String zoneId;
    private String zoneName;
    private double surgeMultiplier; // 1.0 = normal, 1.5 = peak hour

    public Zone(String zoneId, String zoneName, double surgeMultiplier) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.surgeMultiplier = surgeMultiplier;
    }

    public String getZoneId() { return zoneId; }
    public String getZoneName() { return zoneName; }
    public double getSurgeMultiplier() { return surgeMultiplier; }
    public void setSurgeMultiplier(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public String toString() {
        return zoneName + " (surge: " + surgeMultiplier + "x)";
    }
}