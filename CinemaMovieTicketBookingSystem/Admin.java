package movie;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Admin {
    private static final int CONSOLE_WIDTH = 80;
    private MovieManager movieManager;
    private Scanner scanner;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Admin(MovieManager movieManager) {
        this.movieManager = movieManager;
        this.scanner = new Scanner(System.in);
    }

    private String centerText(String text) {
        int padding = (CONSOLE_WIDTH - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    private void clearScreen() {
        System.out.println("\n".repeat(50));
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void displayMenu() {
        boolean exit = false;
        while (!exit) {
            clearScreen();
            String border = "=".repeat(40);
            System.out.println(centerText(border));
            System.out.println(centerText("Admin Menu"));
            System.out.println(centerText("Welcome, Administrator!"));
            System.out.println(centerText(border));
            System.out.println();
            System.out.println(centerText("1. Add New Movie"));
            System.out.println(centerText("2. Add Movie Screening"));
            System.out.println(centerText("3. View All Screenings"));
            System.out.println(centerText("4. View Bookings"));
            System.out.println(centerText("5. Logout"));
            System.out.println();
            
            int choice = getIntInput(centerText("Enter your choice (1-5): "));
            
            switch (choice) {
                case 1:
                    clearScreen();
                    addMovie();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    clearScreen();
                    addScreening();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    clearScreen();
                    viewScreenings();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    clearScreen();
                    viewBookings();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 5:
                    clearScreen();
                    System.out.println(centerText("Logging out. Thank you!"));
                    sleep(1500);
                    exit = true;
                    break;
                default:
                    System.out.println(centerText("Invalid choice. Please try again."));
                    sleep(1500);
            }
        }
    }

    private void addMovie() {
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Add New Movie"));
        System.out.println(centerText(border));
        System.out.println();

        System.out.print(centerText("Enter title: "));
        String title = scanner.nextLine();
        System.out.print(centerText("Enter director: "));
        String director = scanner.nextLine();
        System.out.print(centerText("Enter year: "));
        int year = getIntInput("");
        System.out.print(centerText("Enter genre: "));
        String genre = scanner.nextLine();
        System.out.print(centerText("Enter duration (minutes): "));
        int duration = getIntInput("");
        System.out.print(centerText("Enter ticket price: RM "));
        double price = getDoubleInput("");

        Movie movie = new Movie(title, director, year, genre, duration, price);
        movieManager.addMovie(movie);
        
        System.out.println();
        System.out.println(centerText("Movie added successfully!"));
        sleep(1500);
    }

    private void addScreening() {
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Add New Screening"));
        System.out.println(centerText(border));
        System.out.println();

        System.out.println(centerText("Available Movies:"));
        System.out.println();
        List<Movie> movies = movieManager.getMovies();
        for (int i = 0; i < movies.size(); i++) {
            Movie movie = movies.get(i);
            System.out.println(centerText((i + 1) + ". " + movie.getTitle() + 
                             " (" + movie.getDurationMinutes() + " minutes)"));
        }
        System.out.println();
        
        System.out.print(centerText("Select movie number: "));
        int movieIndex = getIntInput("") - 1;
        if (movieIndex < 0 || movieIndex >= movies.size()) {
            System.out.println(centerText("Invalid movie selection."));
            sleep(1500);
            return;
        }

        System.out.print(centerText("Enter hall number (1-6): "));
        int hallNumber = getIntInput("");
        if (hallNumber < 1 || hallNumber > 6) {
            System.out.println(centerText("Invalid hall number."));
            sleep(1500);
            return;
        }

        System.out.print(centerText("Enter start time (yyyy-MM-dd HH:mm): "));
        String dateTimeStr = scanner.nextLine();
        LocalDateTime startTime;
        try {
            startTime = LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println(centerText("Invalid date/time format."));
            sleep(1500);
            return;
        }

        try {
            Movie selectedMovie = movies.get(movieIndex);
            Screening screening = new Screening(selectedMovie, hallNumber, startTime);
            movieManager.addScreening(screening);
            System.out.println();
            System.out.println(centerText("Screening added successfully!"));
            sleep(1500);
        } catch (IllegalArgumentException e) {
            System.out.println(centerText("Error: " + e.getMessage()));
            sleep(1500);
        }
    }

    private void viewScreenings() {
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("All Screenings"));
        System.out.println(centerText(border));
        System.out.println();

        List<Screening> screenings = movieManager.getScreenings();
        if (screenings.isEmpty()) {
            System.out.println(centerText("No screenings available."));
            return;
        }

        for (Screening screening : screenings) {
            System.out.println(centerText("-".repeat(40)));
            System.out.println(centerText("Movie: " + screening.getMovie().getTitle()));
            System.out.println(centerText("Hall: " + screening.getHallNumber()));
            System.out.println(centerText("Start Time: " + screening.getStartTime().format(formatter)));
            System.out.println(centerText("End Time: " + screening.getEndTime().format(formatter)));
            System.out.println(centerText("Price: RM " + String.format("%.2f", screening.getMovie().getTicketPrice())));
            System.out.println();
        }
    }

    private void viewBookings() {
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Hall Seat Availability"));
        System.out.println(centerText(border));
        System.out.println();

        // First, show all screenings
        List<Screening> screenings = movieManager.getScreenings();
        if (screenings.isEmpty()) {
            System.out.println(centerText("No screenings available."));
            return;
        }

        // Display screenings with index
        for (int i = 0; i < screenings.size(); i++) {
            Screening screening = screenings.get(i);
            System.out.println(centerText("-".repeat(40)));
            System.out.println(centerText((i + 1) + ". Movie: " + screening.getMovie().getTitle()));
            System.out.println(centerText("   Hall: " + screening.getHallNumber()));
            System.out.println(centerText("   Time: " + screening.getStartTime().format(formatter)));
        }

        // Get screening selection from admin
        System.out.print(centerText("\nSelect screening number (1-" + screenings.size() + "): "));
        int screeningChoice = getIntInput("") - 1;
        
        if (screeningChoice < 0 || screeningChoice >= screenings.size()) {
            System.out.println(centerText("Invalid screening selection."));
            return;
        }

        Screening selectedScreening = screenings.get(screeningChoice);
        List<Booking> bookings = movieManager.getBookings();
        
        // Create a boolean array to track occupied seats (assuming 50 seats per hall)
        boolean[] occupiedSeats = new boolean[50];
        
        // Mark occupied seats
        for (Booking booking : bookings) {
            if (booking.getScreening().equals(selectedScreening)) {
                occupiedSeats[booking.getSeatNumber() - 1] = true;
            }
        }

        // Display seat map
        System.out.println("\n" + centerText("Seat Map (O = Available, X = Occupied)"));
        System.out.println();
        
        for (int i = 0; i < 50; i++) {
            if (i % 10 == 0) {
                System.out.println();
                System.out.print(centerText(""));
            }
            System.out.print(String.format("%3d:%s ", (i + 1), occupiedSeats[i] ? "X" : "O"));
        }
        System.out.println("\n");

        // Allow admin to check specific seat details
        System.out.print(centerText("Enter seat number to view details (0 to exit): "));
        int seatChoice = getIntInput("");
        
        while (seatChoice != 0) {
            if (seatChoice < 1 || seatChoice > 50) {
                System.out.println(centerText("Invalid seat number."));
            } else {
                boolean found = false;
                for (Booking booking : bookings) {
                    if (booking.getScreening().equals(selectedScreening) && 
                        booking.getSeatNumber() == seatChoice) {
                        System.out.println("\n" + centerText("-".repeat(40)));
                        System.out.println(centerText("Booking Details for Seat " + seatChoice));
                        System.out.println(centerText("-".repeat(40)));
                        System.out.println(centerText("Customer Name: " + booking.getCustomerName()));
                        System.out.println(centerText("Amount Paid: RM " + String.format("%.2f", booking.getAmount())));
                        System.out.println(centerText("Movie: " + booking.getScreening().getMovie().getTitle()));
                        System.out.println(centerText("Time: " + booking.getScreening().getStartTime().format(formatter)));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(centerText("Seat " + seatChoice + " is available."));
                }
            }
            
            System.out.print(centerText("\nEnter another seat number (0 to exit): "));
            seatChoice = getIntInput("");
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(centerText("Please enter a valid number."));
                sleep(1500);
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(centerText("Please enter a valid number."));
                sleep(1500);
            }
        }
    }
}