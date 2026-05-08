package controller;

import adapter.PaymentAdapter;
import builder.OrderBuilder;
import domain.Customer;
import domain.Order;
import domain.Zone;
import enums.PaymentMode;
import repository.OrderRepository;
import service.BillingService;
import service.DispatchService;

// Entry point for order-related operations
// Controller → Service → Repository (clean layered flow)
public class OrderController {

    private final DispatchService dispatchService;
    private final BillingService billingService;
    private final OrderRepository orderRepository;

    public OrderController(DispatchService dispatchService,
                           BillingService billingService,
                           OrderRepository orderRepository) {
        this.dispatchService = dispatchService;
        this.billingService = billingService;
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(Customer customer, String item,
                            double amount, Zone zone,
                            PaymentMode mode, boolean isPriority) {

        Order order = new OrderBuilder()
                .setCustomer(customer)
                .setItemDescription(item)
                .setBaseAmount(amount)
                .setDeliveryZone(zone)
                .setPaymentMode(mode)
                .setPriority(isPriority)
                .build();

        orderRepository.save(order);
        System.out.println("  [ORDER] Placed: " + order);
        return order;
    }

    public void processOrder(Order order, PaymentAdapter paymentAdapter) {
        boolean dispatched = dispatchService.dispatch(order);
        if (dispatched) {
            billingService.processPayment(order, paymentAdapter);
            dispatchService.completeDelivery(order);
        }
    }

    public void failOrder(Order order) {
        dispatchService.failDelivery(order);
    }
}