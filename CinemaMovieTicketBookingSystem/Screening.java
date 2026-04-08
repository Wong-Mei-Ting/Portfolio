package movie;
import java.time.LocalDateTime;

public class Screening {
    private Movie movie;
    private int hallNumber;  // Changed from 'hall' to 'hallNumber' to be consistent
    private Seat[] seats;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Screening(Movie movie, int hallNumber, LocalDateTime startTime) {
        this.movie = movie;
        this.hallNumber = hallNumber;  // Fixed: using hallNumber instead of hall
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getDurationMinutes());
        this.seats = new Seat[40];  // Initialize 40 seats
        for (int i = 0; i < 40; i++) {
            seats[i] = new Seat(i + 1);
        }
    }

    public Movie getMovie() { return movie; }
    public int getHallNumber() { return hallNumber; }
    public Seat[] getSeats() { return seats; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
}