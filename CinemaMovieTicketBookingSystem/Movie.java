package movie;

public class Movie {
    private String title;
    private String director;
    private int year;
    private String genre;
    private int durationMinutes;
    private double ticketPrice;

    public Movie(String title, String director, int year, String genre, int durationMinutes, double ticketPrice) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.ticketPrice = ticketPrice;
    }

    public String getTitle() { return title; }
    public String getDirector() { return director; }
    public int getYear() { return year; }
    public String getGenre() { return genre; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getTicketPrice() { return ticketPrice; }
}