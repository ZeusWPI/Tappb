package gent.zeus.tappb.entity;

import java.time.LocalDate;

public class Transaction {

    private int id;

    private LocalDate date;
    private String debtor;
    private String creditor;
    private String message;
    private double amount;


    public Transaction(int id, LocalDate date, String debtor, String creditor, String message, double amount) {
        this.id = id;
        this.date = date;
        this.debtor = debtor;
        this.creditor = creditor;
        this.message = message;
        this.amount = amount;

    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDebtor() {
        return debtor;
    }

    public String getCreditor() {
        return creditor;
    }

    public String getMessage() {
        return message;
    }

    public double getAmount() {
        return amount;
    }

    public String getDayString() {
        int day = date.getDayOfMonth();
        return day > 9 ? Integer.toString(day) : "0" + Integer.toString(day);
    }


}
