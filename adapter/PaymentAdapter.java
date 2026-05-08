package adapter;

// Adapter Pattern — UPI and Cash have completely different
// real-world interfaces. This unified contract lets BillingService
// call pay() without caring which mode is active.
public interface PaymentAdapter {
    boolean pay(String orderId, double amount);
    String getModeName();
}