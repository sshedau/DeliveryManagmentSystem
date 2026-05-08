package strategy;

import domain.DeliveryPartner;
import domain.Order;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// Assigns highest rated partner — used for priority/fragile orders
public class HighestRatedStrategy implements AssignmentStrategy {

    @Override
    public DeliveryPartner assign(Order order, List<DeliveryPartner> availablePartners) {
        if (availablePartners.isEmpty()) return null;

        DeliveryPartner best = availablePartners.stream()
                .max(Comparator.comparingDouble(p ->
                        p.getRating().getAverageRating()))
                .orElse(null);

        if (best != null) {
            System.out.println("  [STRATEGY: HIGHEST RATED] Assigned "
                    + best.getName()
                    + " | Rating: " + best.getRating());
        }
        return best;
    }

    @Override
    public String getStrategyName() { return "Highest Rated Partner"; }
}