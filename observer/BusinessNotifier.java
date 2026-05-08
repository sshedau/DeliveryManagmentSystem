package observer;

import domain.Order;

// Notifies the business/operations dashboard
public class BusinessNotifier implements DeliveryObserver {
    @Override
    public void onOrderEvent(Order order, String event) {
        switch (event) {
            case "ASSIGNED":
                System.out.println("  [DASHBOARD] Order " + order.getOrderId()
                        + " dispatched via strategy. Partner: "
                        + order.getAssignedPartner().getName());
                break;
            case "DELIVERED":
                System.out.println("  [DASHBOARD] Order " + order.getOrderId()
                        + " completed. Amount: ₹"
                        + String.format("%.2f", order.getFinalAmount()));
                break;
            case "FAILED":
                System.out.println("  [DASHBOARD] ⚠ Order " + order.getOrderId()
                        + " FAILED. Flagged for review.");
                break;
            default:
                System.out.println("  [DASHBOARD] Order " + order.getOrderId()
                        + " status: " + event);
        }
    }
}