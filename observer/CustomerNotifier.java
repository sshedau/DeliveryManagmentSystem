package observer;

import domain.Order;

// Notifies the customer placing the order
public class CustomerNotifier implements DeliveryObserver {
    @Override
    public void onOrderEvent(Order order, String event) {
        String customerPhone = order.getCustomer().getPhone();
        String customerName  = order.getCustomer().getName();

        switch (event) {
            case "ASSIGNED":
                System.out.println("  [SMS → Customer " + customerName + " | "
                        + customerPhone + "] Your order " + order.getOrderId()
                        + " is assigned to " + order.getAssignedPartner().getName());
                break;
            case "PICKED_UP":
                System.out.println("  [SMS → Customer " + customerName + " | "
                        + customerPhone + "] Order picked up! On its way to you.");
                break;
            case "IN_TRANSIT":
                System.out.println("  [SMS → Customer " + customerName + " | "
                        + customerPhone + "] Order is in transit. ETA: 20 mins.");
                break;
            case "DELIVERED":
                System.out.println("  [SMS → Customer " + customerName + " | "
                        + customerPhone + "] Order DELIVERED! Rate your experience.");
                break;
            case "FAILED":
                System.out.println("  [SMS → Customer " + customerName + " | "
                        + customerPhone + "] Sorry! Delivery failed. Refund initiated.");
                break;
        }
    }
}