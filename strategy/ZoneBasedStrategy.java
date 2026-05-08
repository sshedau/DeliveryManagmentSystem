package strategy;

import domain.DeliveryPartner;
import domain.Order;
import java.util.List;
import java.util.stream.Collectors;

// Assigns partner from same zone first — used during peak hours
// Reduces cross-zone travel, improves delivery time
public class ZoneBasedStrategy implements AssignmentStrategy {

    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartners) {
        if (availablePartners.isEmpty()) return null;

        String orderZoneId = order.getDeliveryZone().getZoneId();

        // Prefer same zone partners first
        List<DeliveryPartner> sameZonePartners = availablePartners.stream()
                .filter(p -> p.getCurrentZone().getZoneId().equals(orderZoneId))
                .collect(Collectors.toList());

        List<DeliveryPartner> pool = sameZonePartners.isEmpty()
                ? availablePartners   // fallback to any available
                : sameZonePartners;

        DeliveryPartner assigned = pool.get(0);
        System.out.println("  [STRATEGY: ZONE BASED] Assigned "
                + assigned.getName()
                + " | Zone match: "
                + (sameZonePartners.isEmpty() ? "NO (fallback)" : "YES")
                + " | Zone: " + assigned.getCurrentZone().getZoneName());

        return assigned;
    }

    @Override
    public String getStrategyName() { return "Zone Based"; }
}