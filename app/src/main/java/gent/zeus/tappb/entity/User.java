package gent.zeus.tappb.entity;

import android.graphics.Bitmap;

public class User {
    private String username;
    private String tabToken;
    private String tapToken;
    private double balance = 12.34;
    private Bitmap profilePicture;

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

    public void setProfilePicture(Bitmap icon) {
        // TODO send this to the Zeus servers
        profilePicture = icon;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }
}
