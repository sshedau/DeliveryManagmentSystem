package domain;

public class Rating {
    private double averageRating;
    private int totalDeliveries;

    public Rating() {
        this.averageRating = 5.0;
        this.totalDeliveries = 0;
    }

    // Called after every completed delivery
    public void addRating(double newRating) {
        this.averageRating = ((averageRating * totalDeliveries) + newRating)
                              / (totalDeliveries + 1);
        this.totalDeliveries++;
    }

    public double getAverageRating() { return averageRating; }
    public int getTotalDeliveries() { return totalDeliveries; }

    @Override
    public String toString() {
        return String.format("%.1f★ (%d deliveries)", averageRating, totalDeliveries);
    }
}