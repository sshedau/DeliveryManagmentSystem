package controller;

import domain.DeliveryPartner;
import repository.PartnerRepository;
import service.RatingService;
import service.TrackingService;

public class AdminController {

    private final PartnerRepository partnerRepository;
    private final TrackingService trackingService;
    private final RatingService ratingService;

    public AdminController(PartnerRepository partnerRepository,
                           TrackingService trackingService,
                           RatingService ratingService) {
        this.partnerRepository = partnerRepository;
        this.trackingService = trackingService;
        this.ratingService = ratingService;
    }

    public void printAllPartners() {
        System.out.println("\n  ── PARTNER STATUS ──────────────────────");
        partnerRepository.findAll().forEach(p ->
                System.out.println("  " + p));
        System.out.println("  ────────────────────────────────────────");
    }

    public void ratePartner(DeliveryPartner partner, double rating) {
        ratingService.rateDelivery(partner, rating);
    }

    public void printAllOrders() {
        trackingService.printAllOrders();
    }
}