package observer;

import domain.Order;

// Observer Pattern — three independent parties watch the same order
// Customer, Business, Partner all react differently to same events
// Adding WhatsApp notifier tomorrow = zero changes to existing code
public interface DeliveryObserver {
    void onOrderEvent(Order order, String event);
}