package movie;

import java.util.Scanner;

public class MovieRentalSystem {
    private static Scanner scanner = new Scanner(System.in);
    private MovieManager movieManager;
    private UserManager userManager;
    private Admin admin;
    private Customer customer;
    private static final int CONSOLE_WIDTH_up = 79; // Standard console width
    private static final int CONSOLE_WIDTH = 80; // Standard console width

    public MovieRentalSystem() {
        movieManager = new MovieManager();
        userManager = new UserManager();
        admin = new Admin(movieManager);
        customer = new Customer(movieManager);
    }

    // Method to clear screen
    private void clearScreen() {
        // Simply print newlines for visual separation
        System.out.println("\n".repeat(50));
    }


    // Method to center text
    private String centerText(String text) {
        int padding = (CONSOLE_WIDTH - text.length()) / 2;
        return " ".repeat(Math.max(0, padding)) + text;
    }

    public void start() {
        boolean exit = false;
        while (!exit) {
            clearScreen();
            displayMainMenu();
            int choice = getIntInput("Enter your choice (1-3): ");

            switch (choice) {
                case 1:
                    clearScreen();
                    handleLogin();
                    break;
                case 2:
                    clearScreen();
                    registerNewCustomer();
                    break;
                case 3:
                    clearScreen();
                    System.out.println(centerText("Thank you for using Movie Rental System!"));
                    exit = true;
                    break;
                default:
                    System.out.println(centerText("Invalid choice. Please try again."));
                    sleep(2000); // Pause to show error message
            }
        }
        scanner.close();
    }

    private void displayMainMenu() {
        String border = "=".repeat(CONSOLE_WIDTH);
        System.out.println(border);
        System.out.println(centerText("Movie Booking System"));
        System.out.println(border);
        System.out.println();
        
        // Center each menu option
        System.out.println(centerText("1. Login"));
        System.out.println(centerText("2. Register (New Customer)"));
        System.out.println(centerText("3. Exit"));
        System.out.println();

        // Optional: Add a centered bottom border for better visual appeal
        System.out.println(border);
    }

    private void handleLogin() {
        System.out.println(centerText("=== Login ==="));
        System.out.println();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        User user = userManager.authenticateUser(username, password);

        if (user != null) {
            clearScreen();
            if (user.getRole().equals("ADMIN")) {
                System.out.println(centerText("Welcome, Admin!"));
                sleep(1000);
                clearScreen();
                admin.displayMenu();
            } else {
                customer.setCurrentUser(user);  // Set the current user before displaying menu
                System.out.println(centerText("Welcome, " + user.getFullName() + "!"));
                sleep(1000);
                clearScreen();
                customer.displayMenu();
            }
        } else {
            System.out.println(centerText("Invalid username or password!"));
            sleep(2000);
        }
    }

    private void registerNewCustomer() {
        System.out.println(centerText("=== New Customer Registration ==="));
        System.out.println();
        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter desired username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Create new user with full name
        User newUser = new User(username, password, "CUSTOMER", fullName);
        userManager.addUser(newUser);
        System.out.println(centerText("Registration successful! Please login to continue."));
        sleep(2000); // Show success message
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(centerText("Please enter a valid number."));
                sleep(1500);
                clearScreen();
                displayMainMenu();
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

    public static void main(String[] args) {
        MovieRentalSystem system = new MovieRentalSystem();
        system.start();
    }
}
