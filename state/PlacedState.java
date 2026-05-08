package state;

import domain.Order;
import enums.OrderStatus;

public class PlacedState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": PLACED → ASSIGNED");
        order.setStatus(OrderStatus.ASSIGNED);
        order.setCurrentState(new AssignedState());
        order.notifyObservers("ASSIGNED");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": PLACED → FAILED");
        order.setStatus(OrderStatus.FAILED);
        order.setCurrentState(new FailedState());
        order.notifyObservers("FAILED");
    }

    @Override
    public String getStateName() { return "PLACED"; }
}