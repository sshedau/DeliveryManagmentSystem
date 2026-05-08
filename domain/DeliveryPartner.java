package domain;

import enums.PartnerStatus;

public class DeliveryPartner {
    private String partnerId;
    private String name;
    private String phone;
    private Zone currentZone;
    private PartnerStatus status;
    private Rating rating;
    private double distanceFromCurrentOrder; // in km, used by NearestPartnerStrategy

    public DeliveryPartner(String partnerId, String name,
                           String phone, Zone currentZone) {
        this.partnerId = partnerId;
        this.name = name;
        this.phone = phone;
        this.currentZone = currentZone;
        this.status = PartnerStatus.AVAILABLE;
        this.rating = new Rating();
        this.distanceFromCurrentOrder = 0.0;
    }

    public String getPartnerId() { return partnerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Zone getCurrentZone() { return currentZone; }
    public PartnerStatus getStatus() { return status; }
    public Rating getRating() { return rating; }
    public double getDistanceFromCurrentOrder() { return distanceFromCurrentOrder; }

    public void setStatus(PartnerStatus status) { this.status = status; }
    public void setCurrentZone(Zone zone) { this.currentZone = zone; }
    public void setDistanceFromCurrentOrder(double distance) {
        this.distanceFromCurrentOrder = distance;
    }

    public boolean isAvailable() {
        return this.status == PartnerStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return name + " [" + partnerId + "] | "
                + rating + " | Zone: " + currentZone.getZoneName()
                + " | Status: " + status;
    }
}