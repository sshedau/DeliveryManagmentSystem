package simulation;

import adapter.CashAdapter;
import adapter.UPIAdapter;
import controller.AdminController;
import controller.OrderController;
import domain.*;
import enums.PaymentMode;
import repository.*;
import service.*;
import strategy.*;

// ══════════════════════════════════════════════════════════════
//   DELIVERY MANAGEMENT SYSTEM — SIMULATION
//   Three real-world scenarios demonstrating all 5 patterns
// ══════════════════════════════════════════════════════════════
public class DeliverySimulation {

    // Shared infrastructure
    static OrderRepository   orderRepo   = new OrderRepository();
    static PartnerRepository partnerRepo = new PartnerRepository();
    static ZoneRepository    zoneRepo    = new ZoneRepository();

    static BillingService  billingService  = new BillingService();
    static RatingService   ratingService   = new RatingService();
    static TrackingService trackingService = new TrackingService(orderRepo);

    // Zones — with surge multipliers
    static Zone zoneA = new Zone("Z1", "Dharampeth",    1.0);
    static Zone zoneB = new Zone("Z2", "Sitabuldi",     1.5); // peak surge
    static Zone zoneC = new Zone("Z3", "Manewada",      1.2);

    // Customers
    static Customer rahul = new Customer("C001", "Rahul Sharma",
            "+91-9800001111", "12 Wardha Road, Dharampeth", zoneA);
    static Customer priya = new Customer("C002", "Priya Desai",
            "+91-9800002222", "45 Sitabuldi Main, Nagpur", zoneB);
    static Customer amit  = new Customer("C003", "Amit Joshi",
            "+91-9800003333", "78 Manewada Ring Road", zoneC);

    // Delivery Partners
    static DeliveryPartner ravi   = new DeliveryPartner("P001", "Ravi",
            "+91-9900001111", zoneA);
    static DeliveryPartner suresh = new DeliveryPartner("P002", "Suresh",
            "+91-9900002222", zoneB);
    static DeliveryPartner mohan  = new DeliveryPartner("P003", "Mohan",
            "+91-9900003333", zoneC);

    public static void main(String[] args) throws InterruptedException {

        // Pre-seed ratings to make strategies more interesting
        ravi.getRating().addRating(4.8);
        ravi.getRating().addRating(4.9);
        suresh.getRating().addRating(3.5);
        mohan.getRating().addRating(4.2);

        // Save zones and partners to repositories
        zoneRepo.save(zoneA); zoneRepo.save(zoneB); zoneRepo.save(zoneC);
        partnerRepo.save(ravi);
        partnerRepo.save(suresh);
        partnerRepo.save(mohan);

        printHeader();

        scenario1_NormalDelivery();
        Thread.sleep(500);

        scenario2_PriorityOrderWithRating();
        Thread.sleep(500);

        scenario3_PeakHourZoneBased();
        Thread.sleep(500);

        printFinalSummary();
    }

    // ──────────────────────────────────────────────────────────
    // SCENARIO 1 — Normal delivery, Nearest Partner, UPI payment
    // ──────────────────────────────────────────────────────────
    static void scenario1_NormalDelivery() {
        printScenario("SCENARIO 1: Normal Delivery | Strategy: Nearest Partner | Payment: UPI");

        DispatchService dispatch = new DispatchService(
                orderRepo, partnerRepo, new NearestPartnerStrategy());
        OrderController orderController = new OrderController(
                dispatch, billingService, orderRepo);

        Order order = orderController.placeOrder(
                rahul, "Medicine Package", 120.0,
                zoneA, PaymentMode.UPI, false);

        orderController.processOrder(order, new UPIAdapter());

        AdminController admin = new AdminController(
                partnerRepo, trackingService, ratingService);
        admin.ratePartner(order.getAssignedPartner(), 4.7);
    }

    // ──────────────────────────────────────────────────────────
    // SCENARIO 2 — Priority order, Highest Rated strategy, Cash
    // Strategy switches at runtime based on order priority
    // ──────────────────────────────────────────────────────────
    static void scenario2_PriorityOrderWithRating() {
        printScenario("SCENARIO 2: Priority Order | Strategy: Highest Rated | Payment: Cash");

        DispatchService dispatch = new DispatchService(
                orderRepo, partnerRepo, new NearestPartnerStrategy());

        // Runtime strategy switch — priority order needs best partner
        System.out.println("\n  [SYSTEM] Priority order detected → switching strategy");
        dispatch.setAssignmentStrategy(new HighestRatedStrategy());

        OrderController orderController = new OrderController(
                dispatch, billingService, orderRepo);

        Order order = orderController.placeOrder(
                priya, "Fragile Electronics", 850.0,
                zoneB, PaymentMode.CASH, true);

        orderController.processOrder(order, new CashAdapter());

        AdminController admin = new AdminController(
                partnerRepo, trackingService, ratingService);
        admin.ratePartner(order.getAssignedPartner(), 5.0);
    }

    // ──────────────────────────────────────────────────────────
    // SCENARIO 3 — Peak hour, Zone Based strategy, UPI with surge
    // Shows surge pricing (1.5x) + zone-based assignment together
    // ──────────────────────────────────────────────────────────
    static void scenario3_PeakHourZoneBased() {
        printScenario("SCENARIO 3: Peak Hour | Strategy: Zone Based | Surge Pricing Active");

        System.out.println("  [SYSTEM] Peak hour detected → Zone Based strategy activated");
        System.out.println("  [SYSTEM] Surge multiplier for " + zoneB.getZoneName()
                + ": " + zoneB.getSurgeMultiplier() + "x");

        DispatchService dispatch = new DispatchService(
                orderRepo, partnerRepo, new ZoneBasedStrategy());
        OrderController orderController = new OrderController(
                dispatch, billingService, orderRepo);

        Order order = orderController.placeOrder(
                amit, "Grocery Bundle", 300.0,
                zoneC, PaymentMode.UPI, false);

        orderController.processOrder(order, new UPIAdapter());

        AdminController admin = new AdminController(
                partnerRepo, trackingService, ratingService);
        admin.ratePartner(order.getAssignedPartner(), 4.3);
    }

    // ──────────────────────────────────────────────────────────
    // FINAL SUMMARY
    // ──────────────────────────────────────────────────────────
    static void printFinalSummary() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║              SYSTEM SUMMARY                 ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        AdminController admin = new AdminController(
                partnerRepo, trackingService, ratingService);
        admin.printAllOrders();
        admin.printAllPartners();

        System.out.println("\n  Patterns demonstrated:");
        System.out.println("  ✓ Builder  — Order constructed with readable, safe fluent API");
        System.out.println("  ✓ State    — Order lifecycle enforced (no illegal transitions)");
        System.out.println("  ✓ Strategy — Assignment algorithm swapped at runtime per scenario");
        System.out.println("  ✓ Observer — Customer + Business + Partner notified independently");
        System.out.println("  ✓ Adapter  — UPI and Cash unified behind single PaymentAdapter");
        System.out.println();
    }

    static void printHeader() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║      DELIVERY MANAGEMENT SYSTEM v1.0        ║");
        System.out.println("║      Nagpur Operations Simulation            ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("  Partners online: Ravi (" + ravi.getRating()
                + "), Suresh (" + suresh.getRating()
                + "), Mohan (" + mohan.getRating() + ")");
    }

    static void printScenario(String title) {
        System.out.println("\n┌──────────────────────────────────────────────┐");
        System.out.println("│ " + title);
        System.out.println("└──────────────────────────────────────────────┘");
    }
}