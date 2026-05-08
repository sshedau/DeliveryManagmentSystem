package strategy;

import domain.DeliveryPartner;
import domain.Order;
import java.util.List;

// Strategy Pattern — assignment algorithm is pluggable
// DispatchService doesn't care WHICH strategy is active
// It just calls assign() and gets back the best partner
public interface AssignmentStrategy {
    DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartners);
    String getStrategyName();
}