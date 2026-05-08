package observer;

import domain.Order;

// Notifies the delivery partner's app
public class PartnerNotifier implements DeliveryObserver {
    @Override
    public void onOrderEvent(Order order, String event) {
        if (order.getAssignedPartner() == null) return;

        String partnerName  = order.getAssignedPartner().getName();
        String partnerPhone = order.getAssignedPartner().getPhone();

        switch (event) {
            case "ASSIGNED":
                System.out.println("  [APP → Partner " + partnerName + " | "
                        + partnerPhone + "] New order! Pick up from: "
                        + order.getCustomer().getAddress());
                break;
            case "DELIVERED":
                System.out.println("  [APP → Partner " + partnerName + " | "
                        + partnerPhone + "] Delivery confirmed. You are now AVAILABLE.");
                break;
            case "FAILED":
                System.out.println("  [APP → Partner " + partnerName + " | "
                        + partnerPhone + "] Order failed. You are now AVAILABLE.");
                break;
        }
    }
}