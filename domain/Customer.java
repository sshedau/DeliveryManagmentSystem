package domain;

public class Customer {
    private String customerId;
    private String name;
    private String phone;
    private String address;
    private Zone zone;

    public Customer(String customerId, String name, String phone,
                    String address, Zone zone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.zone = zone;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public Zone getZone() { return zone; }

    @Override
    public String toString() {
        return name + " [" + customerId + "] | Zone: " + zone.getZoneName();
    }
}