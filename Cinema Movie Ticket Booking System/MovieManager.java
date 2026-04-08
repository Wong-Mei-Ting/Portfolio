package movie;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {
    private List<Hall> halls;
    private List<Movie> movies;
    private List<Screening> screenings;
    private List<Booking> bookings;
    private static final LocalTime OPENING_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSING_TIME = LocalTime.of(1, 0);
    private static final Duration BUFFER_TIME = Duration.ofMinutes(30);

    public MovieManager() {
        halls = new ArrayList<>();
        movies = new ArrayList<>();
        screenings = new ArrayList<>();
        bookings = new ArrayList<>();
        
        // Initialize 6 halls
        for (int i = 1; i <= 6; i++) {
            halls.add(new Hall(i));
        }
        // Add sample movies
        movies.add(new Movie("The Matrix", "Wachowskis", 1999, "Sci-Fi", 136, 12.99));
        movies.add(new Movie("Inception", "Christopher Nolan", 2010, "Sci-Fi", 148, 12.99));
    }

    public List<Hall> getHalls() { return halls; }
    public List<Movie> getMovies() { return movies; }
    public List<Screening> getScreenings() { return screenings; }
    public List<Booking> getBookings() { return bookings; }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    private boolean isWithinBusinessHours(LocalDateTime dateTime) {
        LocalTime time = dateTime.toLocalTime();
        
        if (CLOSING_TIME.isBefore(OPENING_TIME)) {
            return time.isAfter(OPENING_TIME) || time.isBefore(CLOSING_TIME);
        } else {
            return time.isAfter(OPENING_TIME) && time.isBefore(CLOSING_TIME);
        }
    }

    public void addScreening(Screening screening) {
        if (!isWithinBusinessHours(screening.getStartTime()) || 
            !isWithinBusinessHours(screening.getEndTime())) {
            throw new IllegalArgumentException("Screening must be within business hours");
        }

        boolean isAvailable = true;
        LocalDateTime newStart = screening.getStartTime();
        LocalDateTime newEnd = screening.getEndTime().plus(BUFFER_TIME);

        for (Screening existing : screenings) {
            if (existing.getHallNumber() == screening.getHallNumber()) {
                LocalDateTime existingStart = existing.getStartTime();
                LocalDateTime existingEnd = existing.getEndTime().plus(BUFFER_TIME);
                if (!(newEnd.isBefore(existingStart) || newStart.isAfter(existingEnd))) {
                    isAvailable = false;
                    break;
                }
            }
        }

        if (isAvailable) {
            screenings.add(screening);
            halls.get(screening.getHallNumber() - 1).resetSeats();
        } else {
            throw new IllegalArgumentException("Hall is not available for this time slot");
        }
    }

    public boolean bookSeat(Screening screening, int seatNumber, String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        Hall hall = halls.get(screening.getHallNumber() - 1);
        if (seatNumber < 1 || seatNumber > hall.getSeats().length) {
            throw new IllegalArgumentException("Invalid seat number");
        }

        Seat seat = hall.getSeats()[seatNumber - 1];
        if (!seat.isBooked()) {
            seat.setBooked(true);
            bookings.add(new Booking(screening, seatNumber, customerName, 
                        screening.getMovie().getTicketPrice()));
            return true;
        }
        return false;
    }
}
