package adapter;

// Cash has NO network dependency — always succeeds
// This behavioral difference is the whole point of Adapter pattern
public class CashAdapter implements PaymentAdapter {

    @Override
    public boolean pay(String orderId, double amount) {
        System.out.println("  [CASH] Payment collected by partner | Order: "
                + orderId + " | Amount: ₹" + String.format("%.2f", amount));
        System.out.println("  [CASH] SUCCESS | No network required");
        return true;
    }

    @Override
    public String getModeName() { return "CASH"; }
}