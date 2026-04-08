package movie;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class Customer {
    private static final int CONSOLE_WIDTH = 80;
    private MovieManager movieManager;
    private Scanner scanner;
    private String customerName;
    private DateTimeFormatter formatter;
    private User currentUser;

    public Customer(MovieManager movieManager) {
        this.movieManager = movieManager;
        this.scanner = new Scanner(System.in);
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    private String centerText(String text) {
        int padding = (CONSOLE_WIDTH - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    private void clearScreen() {
        System.out.println("\n".repeat(50));
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.customerName = user.getFullName();
    }

    public void displayMenu() {
        if (currentUser == null) {
            System.out.println(centerText("Error: No user logged in!"));
            return;
        }

        boolean exit = false;
        while (!exit) {
            clearScreen();
            String border = "=".repeat(40);
            System.out.println(centerText(border));
            System.out.println(centerText("Customer Menu"));
            System.out.println(centerText("Welcome, " + customerName + "!"));
            System.out.println(centerText(border));
            System.out.println();
            System.out.println(centerText("1. View Movie Screenings"));
            System.out.println(centerText("2. Book Tickets"));
            System.out.println(centerText("3. View My Bookings"));
            System.out.println(centerText("4. Return to Main Menu"));
            System.out.println();
            
            int choice = getIntInput(centerText("Enter your choice (1-4): "));
            
            switch (choice) {
                case 1:
                    clearScreen();
                    viewScreenings();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    clearScreen();
                    bookTicket();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 3:
                    clearScreen();
                    viewMyBookings();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println(centerText("Invalid choice. Please try again."));
                    sleep(1500);
            }
        }
    }

    private void viewScreenings() {
        List<Screening> screenings = movieManager.getScreenings();
        if (screenings.isEmpty()) {
            System.out.println(centerText("No screenings available at the moment."));
            return;
        }

        String border = "=".repeat(60);
        System.out.println(centerText(border));
        System.out.println(centerText("Upcoming Screenings"));
        System.out.println(centerText(border));
        System.out.println();

        for (int i = 0; i < screenings.size(); i++) {
            Screening screening = screenings.get(i);
            System.out.println(centerText("Screening #" + (i + 1)));
            System.out.println(centerText("Movie: " + screening.getMovie().getTitle()));
            System.out.println(centerText("Hall: " + screening.getHallNumber()));
            System.out.println(centerText("Start Time: " + screening.getStartTime().format(formatter)));
            System.out.println(centerText("End Time: " + screening.getEndTime().format(formatter)));
            System.out.println(centerText("Price: RM " + String.format("%.2f", screening.getMovie().getTicketPrice())));
            System.out.println(centerText("Available Seats: " + countAvailableSeats(screening)));
            System.out.println(centerText("-".repeat(40)));
            System.out.println();
        }
    }

    private void viewMyBookings() {
        String border = "=".repeat(50);
        System.out.println(centerText(border));
        System.out.println(centerText("My Bookings"));
        System.out.println(centerText(border));
        System.out.println();

        boolean foundBookings = false;

        for (Booking booking : movieManager.getBookings()) {
            if (booking.getCustomerName().equals(customerName)) {
                foundBookings = true;
                System.out.println(centerText("-".repeat(40)));
                System.out.println(centerText("Movie: " + booking.getScreening().getMovie().getTitle()));
                System.out.println(centerText("Hall: " + booking.getScreening().getHallNumber()));
                System.out.println(centerText("Time: " + booking.getScreening().getStartTime().format(formatter)));
                System.out.println(centerText("Seat: " + booking.getSeatNumber()));
                System.out.println(centerText("Amount Paid: $" + String.format("%.2f", booking.getAmount())));
                System.out.println(centerText("-".repeat(40)));
                System.out.println();
            }
        }

        if (!foundBookings) {
            System.out.println(centerText("No bookings found."));
        }
    }

    private void displaySeatMap(Screening screening) {
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Seat Map for Hall " + screening.getHallNumber()));
        System.out.println(centerText("(X = Booked, O = Available)"));
        System.out.println(centerText(border));
        System.out.println(centerText("SCREEN"));
        System.out.println(centerText("-".repeat(32)));
        System.out.println();

        Seat[] seats = screening.getSeats();
        for (int row = 0; row < 5; row++) {
            StringBuilder rowStr = new StringBuilder();
            rowStr.append("Row ").append(row + 1).append(": ");
            for (int col = 0; col < 8; col++) {
                int seatIndex = row * 8 + col;
                if (seats[seatIndex].isBooked()) {
                    rowStr.append("X  ");
                } else {
                    rowStr.append(String.format("%2d ", seats[seatIndex].getSeatNumber()));
                }
            }
            System.out.println(centerText(rowStr.toString()));
        }
        System.out.println();
    }

    private void bookTicket() {
        List<Screening> screenings = movieManager.getScreenings();
        if (screenings.isEmpty()) {
            System.out.println(centerText("No screenings available for booking."));
            return;
        }

        viewScreenings();
        int screeningChoice = getIntInput(centerText("Select screening number: ")) - 1;

        if (screeningChoice < 0 || screeningChoice >= screenings.size()) {
            System.out.println(centerText("Invalid screening selection."));
            return;
        }

        clearScreen();
        Screening selectedScreening = screenings.get(screeningChoice);
        displaySeatMap(selectedScreening);

        int numTickets = getIntInput(centerText("How many tickets would you like to book? "));
        
        if (numTickets <= 0 || numTickets > countAvailableSeats(selectedScreening)) {
            System.out.println(centerText("Invalid number of tickets or not enough seats available."));
            return;
        }

        List<Integer> selectedSeats = new ArrayList<>();
        double totalCost = 0.0;

        for (int i = 0; i < numTickets; i++) {
            int seatNumber = getIntInput(centerText("Select seat number for ticket " + (i + 1) + ": "));

            if (seatNumber < 1 || seatNumber > 40 || 
                selectedScreening.getSeats()[seatNumber - 1].isBooked()) {
                System.out.println(centerText("Invalid seat selection or seat already booked."));
                return;
            }

            selectedSeats.add(seatNumber);
            totalCost += selectedScreening.getMovie().getTicketPrice();
        }

        clearScreen();
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Booking Summary"));
        System.out.println(centerText(border));
        System.out.println(centerText("Movie: " + selectedScreening.getMovie().getTitle()));
        System.out.println(centerText("Time: " + selectedScreening.getStartTime().format(formatter)));
        System.out.println(centerText("Hall: " + selectedScreening.getHallNumber()));
        System.out.println(centerText("Seats: " + selectedSeats));
        System.out.println(centerText("Total Cost: RM " + String.format("%.2f", totalCost)));
        System.out.println();

        System.out.print(centerText("Confirm booking (y/n)? "));
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            if (processPayment(totalCost)) {
                for (int seatNumber : selectedSeats) {
                    selectedScreening.getSeats()[seatNumber - 1].setBooked(true);
                    movieManager.addBooking(new Booking(selectedScreening, seatNumber, 
                                         customerName, selectedScreening.getMovie().getTicketPrice()));
                }
                System.out.println(centerText("Booking successful! Enjoy your movie!"));
            } else {
                System.out.println(centerText("Payment failed. Booking cancelled."));
            }
        } else {
            System.out.println(centerText("Booking cancelled."));
        }
    }

    private boolean processPayment(double amount) {
        clearScreen();
        String border = "=".repeat(40);
        System.out.println(centerText(border));
        System.out.println(centerText("Payment Processing"));
        System.out.println(centerText(border));
        System.out.println(centerText("Total Amount: $" + String.format("%.2f", amount)));
        System.out.println();
        System.out.println(centerText("1. Credit Card"));
        System.out.println(centerText("2. Debit Card"));
        System.out.println(centerText("3. Cash"));
        System.out.println();
        
        int paymentMethod = getIntInput(centerText("Select payment method (1-3): "));
        
        switch (paymentMethod) {
            case 1:
            case 2:
                System.out.print(centerText("Enter card number: "));
                String cardNumber = scanner.nextLine();
                System.out.print(centerText("Enter expiration date (MM/YY): "));
                String expDate = scanner.nextLine();
                System.out.print(centerText("Enter CVV: "));
                String cvv = scanner.nextLine();
                break;
            case 3:
                System.out.print(centerText("Enter amount tendered: RM "));
                double tendered = getDoubleInput("");
                if (tendered < amount) {
                    System.out.println(centerText("Insufficient payment."));
                    return false;
                }
                double change = tendered - amount;
                System.out.println(centerText("Change: RM " + String.format("%.2f", change)));
                break;
            default:
                System.out.println(centerText("Invalid payment method."));
                return false;
        }
        
        return true;
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(centerText("Please enter a valid number."));
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
            }
        }
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private int countAvailableSeats(Screening screening) {
        int count = 0;
        for (Seat seat : screening.getSeats()) {
            if (!seat.isBooked()) {
                count++;
            }
        }
        return count;
    }
}