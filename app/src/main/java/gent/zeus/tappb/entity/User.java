package gent.zeus.tappb.entity;

public class User {
    private String username;
    private String tabToken;
    private String tapToken;

    public User(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
    }

    public double getBalance() {
        return -12.34;
    }

    public String getUsername() {
        return username;
    }
}
