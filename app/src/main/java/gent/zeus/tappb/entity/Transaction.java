package gent.zeus.tappb.entity;

import java.time.LocalDate;

public class Transaction {

    private int id;

    private LocalDate date;
    private String description;
    private double cost;


    public Transaction(int id, String description, double cost, LocalDate date) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDayString() {
        int day = date.getDayOfMonth();
        return day > 9 ? Integer.toString(day) : "0" + Integer.toString(day);
    }
}
