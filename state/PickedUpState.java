package state;

import domain.Order;
import enums.OrderStatus;

public class PickedUpState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": PICKED_UP → IN_TRANSIT");
        order.setStatus(OrderStatus.IN_TRANSIT);
        order.setCurrentState(new InTransitState());
        order.notifyObservers("IN_TRANSIT");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": PICKED_UP → FAILED");
        order.setStatus(OrderStatus.FAILED);
        order.setCurrentState(new FailedState());
        order.notifyObservers("FAILED");
    }

    @Override
    public String getStateName() { return "PICKED_UP"; }
}