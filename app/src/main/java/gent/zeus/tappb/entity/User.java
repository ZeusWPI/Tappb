package gent.zeus.tappb.entity;

public class User {
    private String username;
    private String tabToken;
    private String tapToken;
    private double balance = 12.34;

    public User(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
    }

    public double getBalance() {
        return balance;
    }

    public String getUsername() {
        return username;
    }

    public boolean hasDebt() {
        return balance <= 0;
    }
}
