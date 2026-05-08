package state;

import domain.Order;

// State Pattern — each state knows its own valid transitions
// This prevents illegal transitions like DELIVERED -> PLACED
public interface OrderState {
    void next(Order order);
    void fail(Order order);
    String getStateName();
}