package gent.zeus.tappb.entity;

import java.text.DecimalFormat;
import java.time.OffsetDateTime;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import gent.zeus.tappb.repositories.UserRepository;

public class Transaction {

    private int id;

    private OffsetDateTime date;
    private String debtor;
    private String creditor;
    private String message;
    private double amount;

    //TODO
    private boolean userIsDebtor;


    public Transaction(int id, OffsetDateTime date, String debtor, String creditor, String message, double amount) {
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

    public OffsetDateTime getDate() {
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

    public String getFormattedAmount() {
        DecimalFormat costFormatter = new DecimalFormat("#0.00");
        String formatted = "€" + costFormatter.format(amount);
        if (userIsDebtor) {
            formatted = "-" + formatted;
        }
        return formatted;
    }

    public String getPersonDescription() {
        if (userIsDebtor) {
            return "to: " + creditor;
        }
        return "from: " + debtor;
    }

    public String getDayString() {
        int day = date.getDayOfMonth();
        return day > 9 ? Integer.toString(day) : "0" + day;
    }
}
