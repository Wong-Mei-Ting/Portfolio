package movie;

public class Hall {
    private int hallNumber;
    private Seat[] seats;
    private static final int SEATS_PER_HALL = 40;

    public Hall(int hallNumber) {
        this.hallNumber = hallNumber;
        this.seats = new Seat[SEATS_PER_HALL];
        for (int i = 0; i < SEATS_PER_HALL; i++) {
            seats[i] = new Seat(i + 1);
        }
    }

    public int getHallNumber() { return hallNumber; }
    public Seat[] getSeats() { return seats; }
    
    public void resetSeats() {
        for (Seat seat : seats) {
            seat.setBooked(false);
        }
    }
}

