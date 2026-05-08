package service;

import domain.DeliveryPartner;
import domain.Order;
import enums.OrderStatus;
import repository.OrderRepository;
import java.util.List;

public class RatingService {
    public void rateDelivery(DeliveryPartner partner, double rating) {
        partner.getRating().addRating(rating);
        System.out.println("  [RATING] " + partner.getName()
                + " rated " + rating + "★ | New avg: " + partner.getRating());
    }
}