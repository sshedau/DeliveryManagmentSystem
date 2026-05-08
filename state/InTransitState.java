package state;

import domain.Order;
import enums.OrderStatus;

public class InTransitState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": IN_TRANSIT → DELIVERED ✓");
        order.setStatus(OrderStatus.DELIVERED);
        order.setCurrentState(new DeliveredState());
        order.notifyObservers("DELIVERED");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": IN_TRANSIT → FAILED");
        order.setStatus(OrderStatus.FAILED);
        order.setCurrentState(new FailedState());
        order.notifyObservers("FAILED");
    }

    @Override
    public String getStateName() { return "IN_TRANSIT"; }
}