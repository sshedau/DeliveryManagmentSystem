package builder;

import domain.Customer;
import domain.Order;
import domain.Zone;
import enums.PaymentMode;
import java.util.UUID;

// Builder Pattern — Order has many optional fields
// Without Builder: new Order(customer, item, amount, mode, zone, instructions, priority)
// Hard to read, easy to pass wrong argument in wrong position
// With Builder: clear, readable, safe construction
public class OrderBuilder {

    private Customer customer;
    private String itemDescription;
    private double baseAmount;
    private PaymentMode paymentMode = PaymentMode.CASH; // default
    private Zone deliveryZone;
    private String specialInstructions = "";            // optional
    private boolean isPriority = false;                 // optional

    public OrderBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public OrderBuilder setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public OrderBuilder setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
        return this;
    }

    public OrderBuilder setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public OrderBuilder setDeliveryZone(Zone zone) {
        this.deliveryZone = zone;
        return this;
    }

    public OrderBuilder setSpecialInstructions(String instructions) {
        this.specialInstructions = instructions;
        return this;
    }

    public OrderBuilder setPriority(boolean isPriority) {
        this.isPriority = isPriority;
        return this;
    }

    public Order build() {
        if (customer == null || itemDescription == null || deliveryZone == null) {
            throw new IllegalStateException(
                "Order must have customer, item description, and delivery zone");
        }

        String orderId = "ORD_"
                + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        return Order.createFromBuilder(orderId, customer, itemDescription,
                baseAmount, paymentMode, deliveryZone,
                specialInstructions, isPriority);
    }
}
