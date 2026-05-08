package service;

import domain.Order;
import repository.OrderRepository;
import enums.OrderStatus;
import java.util.List;

public class TrackingService {
    private final OrderRepository orderRepository;

    public TrackingService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void printOrderStatus(Order order) {
        System.out.println("  [TRACKING] " + order.getOrderId()
                + " | Status: " + order.getStatus()
                + " | Customer: " + order.getCustomer().getName()
                + " | Partner: " + (order.getAssignedPartner() != null
                    ? order.getAssignedPartner().getName() : "UNASSIGNED"));
    }

    public void printAllOrders() {
        System.out.println("\n  ── ORDER SUMMARY ──────────────────────");
        List<Order> all = orderRepository.findAll();
        for (Order o : all) {
            System.out.println("  " + o.getOrderId()
                    + " | " + o.getStatus()
                    + " | " + o.getCustomer().getName()
                    + " | ₹" + String.format("%.2f", o.getFinalAmount()));
        }
        System.out.println("  ────────────────────────────────────────");
    }
}