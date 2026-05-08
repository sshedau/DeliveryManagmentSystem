package service;

import domain.DeliveryPartner;
import domain.Order;
import enums.PartnerStatus;
import observer.BusinessNotifier;
import observer.CustomerNotifier;
import observer.PartnerNotifier;
import repository.OrderRepository;
import repository.PartnerRepository;
import strategy.AssignmentStrategy;
import java.util.List;

// DispatchService is the heart of the system
// It orchestrates Strategy + Observer + State together
public class DispatchService {

    private final OrderRepository orderRepository;
    private final PartnerRepository partnerRepository;
    private AssignmentStrategy assignmentStrategy; // pluggable

    public DispatchService(OrderRepository orderRepository,
                           PartnerRepository partnerRepository,
                           AssignmentStrategy assignmentStrategy) {
        this.orderRepository = orderRepository;
        this.partnerRepository = partnerRepository;
        this.assignmentStrategy = assignmentStrategy;
    }

    // Strategy can be swapped at runtime — peak hours, priority orders
    public void setAssignmentStrategy(AssignmentStrategy strategy) {
        System.out.println("  [DISPATCH] Strategy switched to: "
                + strategy.getStrategyName());
        this.assignmentStrategy = strategy;
    }

    public boolean dispatch(Order order) {
        System.out.println("\n  [DISPATCH] Processing order: " + order.getOrderId()
                + " | Strategy: " + assignmentStrategy.getStrategyName());

        // Register all three observers before any events fire
        order.addObserver(new CustomerNotifier());
        order.addObserver(new BusinessNotifier());
        order.addObserver(new PartnerNotifier());

        List<DeliveryPartner> available = partnerRepository.findAvailable();

        if (available.isEmpty()) {
            System.out.println("  [DISPATCH] No partners available. Order failed.");
            order.failOrder();
            orderRepository.save(order);
            return false;
        }

        // Strategy decides which partner gets this order
        DeliveryPartner assigned = assignmentStrategy.assign(order, available);

        if (assigned == null) {
            order.failOrder();
            orderRepository.save(order);
            return false;
        }

        // Update partner and order state
        order.setAssignedPartner(assigned);
        assigned.setStatus(PartnerStatus.BUSY);

        // State transition: PLACED → ASSIGNED (triggers all observers)
        order.nextState();
        orderRepository.save(order);
        return true;
    }

    public void completeDelivery(Order order) {
        // ASSIGNED → PICKED_UP → IN_TRANSIT → DELIVERED
        order.nextState(); // PICKED_UP
        sleep(400);
        order.nextState(); // IN_TRANSIT
        sleep(400);
        order.nextState(); // DELIVERED

        // Free up the partner
        if (order.getAssignedPartner() != null) {
            order.getAssignedPartner().setStatus(PartnerStatus.AVAILABLE);
        }
        orderRepository.save(order);
    }

    public void failDelivery(Order order) {
        order.failOrder();
        if (order.getAssignedPartner() != null) {
            order.getAssignedPartner().setStatus(PartnerStatus.AVAILABLE);
        }
        orderRepository.save(order);
    }

    private void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}