package movie;

public class Booking {
    private Screening screening;
    private int seatNumber;
    private String customerName;
    private double amount;

    public Booking(Screening screening, int seatNumber, String customerName, double amount) {
        this.screening = screening;
        this.seatNumber = seatNumber;
        this.customerName = customerName;
        this.amount = amount;
    }

    public Screening getScreening() { return screening; }
    public int getSeatNumber() { return seatNumber; }
    public String getCustomerName() { return customerName; }
    public double getAmount() { return amount; }
}