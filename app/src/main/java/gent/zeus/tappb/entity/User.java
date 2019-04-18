package gent.zeus.tappb.entity;

import android.graphics.Bitmap;

public class User {
    private static final User instance = new User();
    private String username;
    private String tabToken;
    private String tapToken;
    private boolean loaded = false;
    private Bitmap profilePicture;

    private double balance;
    private Product favoriteItem;

    public void load(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
        this.loaded = true;
    }

    //private constructor to avoid client applications to use constructor
    private User(){}

    public double getBalance() {
        // TODO: get from API
        balance = 12.34;
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

    public Product getFavoriteItem() {
        //TODO get from API
        favoriteItem = new Product(7730, "Drenk", 1.33);
        return favoriteItem;
    }

    public void setFavoriteItem(Product favoriteItem) {
        this.favoriteItem = favoriteItem;
    }

    public static User getInstance(){
        return instance;
    }

    private void assertLoaded() {
        if (!this.loaded) {
            throw new RuntimeException("User is not loaded yet");
        }
    }

    public String getTabToken() {
        assertLoaded();
        return tabToken;
    }

    public String getTapToken() {
        assertLoaded();
        return tapToken;
    }

    public boolean isLoaded() {
        return loaded;
    }
}
