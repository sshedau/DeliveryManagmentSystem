package state;

import domain.Order;

public class DeliveredState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] Order " + order.getOrderId()
                + " already DELIVERED. No further transitions allowed.");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] Cannot fail an already DELIVERED order.");
    }

    @Override
    public String getStateName() { return "DELIVERED"; }
}