package state;

import domain.Order;
import enums.OrderStatus;

public class AssignedState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": ASSIGNED → PICKED_UP");
        order.setStatus(OrderStatus.PICKED_UP);
        order.setCurrentState(new PickedUpState());
        order.notifyObservers("PICKED_UP");
    }

    @Override
    public void fail(Order order) {
        System.out.println("  [STATE] " + order.getOrderId() + ": ASSIGNED → FAILED");
        order.setStatus(OrderStatus.FAILED);
        order.setCurrentState(new FailedState());
        order.notifyObservers("FAILED");
    }

    @Override
    public String getStateName() { return "ASSIGNED"; }
}