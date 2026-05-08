package strategy;

import domain.DeliveryPartner;
import domain.Order;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

// Assigns partner with shortest distance to pickup point
// Used during normal hours for fast dispatch
public class NearestPartnerStrategy implements AssignmentStrategy {

    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartners) {
        if (availablePartners.isEmpty()) return null;

        // Simulate distance calculation (in real system: use Maps API)
        Random random = new Random();
        availablePartners.forEach(p ->
            p.setDistanceFromCurrentOrder(1.0 + random.nextDouble() * 9.0)
        );

        DeliveryPartner nearest = availablePartners.stream()
                .min(Comparator.comparingDouble(
                        DeliveryPartner::getDistanceFromCurrentOrder))
                .orElse(null);

        if (nearest != null) {
            System.out.println("  [STRATEGY: NEAREST] Assigned "
                    + nearest.getName()
                    + " | Distance: "
                    + String.format("%.1f", nearest.getDistanceFromCurrentOrder())
                    + " km");
        }
        return nearest;
    }

    @Override
    public String getStrategyName() { return "Nearest Partner"; }
}