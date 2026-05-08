package adapter;

import java.util.Random;
import java.util.UUID;

// UPI has network dependency — can fail
public class UPIAdapter implements PaymentAdapter {

    @Override
    public boolean pay(String orderId, double amount) {
        System.out.println("  [UPI] Initiating payment | Order: "
                + orderId + " | Amount: ₹" + String.format("%.2f", amount));

        simulateNetworkCall();

        double outcome = new Random().nextDouble();
        if (outcome < 0.85) {
            System.out.println("  [UPI] SUCCESS | TxnId: UPI_"
                    + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            return true;
        } else if (outcome < 0.95) {
            System.out.println("  [UPI] FAILED | Reason: Insufficient balance");
            return false;
        } else {
            System.out.println("  [UPI] FAILED | Reason: Network timeout");
            return false;
        }
    }

    private void simulateNetworkCall() {
        try { Thread.sleep(300); } catch (InterruptedException e) {}
    }

    @Override
    public String getModeName() { return "UPI"; }
}