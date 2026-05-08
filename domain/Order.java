package domain;

import enums.OrderStatus;
import enums.PaymentMode;
import state.OrderState;
import state.PlacedState;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private Customer customer;
    private String itemDescription;
    private double baseAmount;
    private double finalAmount;
    private PaymentMode paymentMode;
    private Zone deliveryZone;
    private DeliveryPartner assignedPartner;
    private OrderStatus status;
    private LocalDateTime placedAt;
    private String specialInstructions;
    private boolean isPriority;

    // State pattern — current state object handles transitions
    private OrderState currentState;

    // Observer pattern — list of observers watching this order
    private List<observer.DeliveryObserver> observers = new ArrayList<>();

    // Private constructor — forced to use Builder
    private Order() {}

    // Called by OrderBuilder only
    public static Order createFromBuilder(String orderId, Customer customer,
                                          String itemDescription, double baseAmount,
                                          PaymentMode paymentMode, Zone deliveryZone,
                                          String specialInstructions, boolean isPriority) {
        Order order = new Order();
        order.orderId = orderId;
        order.customer = customer;
        order.itemDescription = itemDescription;
        order.baseAmount = baseAmount;
        order.paymentMode = paymentMode;
        order.deliveryZone = deliveryZone;
        order.specialInstructions = specialInstructions;
        order.isPriority = isPriority;
        order.status = OrderStatus.PLACED;
        order.placedAt = LocalDateTime.now();
        order.currentState = new PlacedState(); // starts in PLACED state
        return order;
    }

    // State pattern — delegates transition to current state object
    public void nextState() {
        currentState.next(this);
    }

    public void failOrder() {
        currentState.fail(this);
    }

    // Observer pattern methods
    public void addObserver(observer.DeliveryObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String event) {
        for (observer.DeliveryObserver obs : observers) {
            obs.onOrderEvent(this, event);
        }
    }

    // Getters
    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public String getItemDescription() { return itemDescription; }
    public double getBaseAmount() { return baseAmount; }
    public double getFinalAmount() { return finalAmount; }
    public PaymentMode getPaymentMode() { return paymentMode; }
    public Zone getDeliveryZone() { return deliveryZone; }
    public DeliveryPartner getAssignedPartner() { return assignedPartner; }
    public OrderStatus getStatus() { return status; }
    public String getSpecialInstructions() { return specialInstructions; }
    public boolean isPriority() { return isPriority; }
    public String getPlacedAt() {
        return placedAt.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    // Setters
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setCurrentState(OrderState state) { this.currentState = state; }
    public void setAssignedPartner(DeliveryPartner partner) {
        this.assignedPartner = partner;
    }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    @Override
    public String toString() {
        return "Order#" + orderId + " | " + itemDescription
                + " | Customer: " + customer.getName()
                + " | Zone: " + deliveryZone.getZoneName()
                + " | Status: " + status;
    }
}