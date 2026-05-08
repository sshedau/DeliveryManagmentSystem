package service;

import adapter.PaymentAdapter;
import domain.Order;

public class BillingService {

    // Adapter pattern — BillingService doesn't know if it's UPI or Cash
    // It just calls pay() on whichever adapter is passed
    public boolean processPayment(Order order, PaymentAdapter paymentAdapter) {
        double surgeMultiplier = order.getDeliveryZone().getSurgeMultiplier();
        double finalAmount = order.getBaseAmount() * surgeMultiplier;
        order.setFinalAmount(finalAmount);

        System.out.println("\n  [BILLING] Base: ₹" + order.getBaseAmount()
                + " | Surge: " + surgeMultiplier + "x"
                + " | Final: ₹" + String.format("%.2f", finalAmount)
                + " | Mode: " + paymentAdapter.getModeName());

        return paymentAdapter.pay(order.getOrderId(), finalAmount);
    }
}