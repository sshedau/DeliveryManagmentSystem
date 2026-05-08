package state;

import domain.Order;

public class FailedState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] Cannot advance a FAILED order.");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] Order " + order.getOrderId() + " already FAILED.");
    }

    @Override
    public String getStateName() { return "FAILED"; }
}