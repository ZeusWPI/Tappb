package gent.zeus.tappb.entity;

import android.graphics.Bitmap;

public class TapUser {
    private int id;
    private Bitmap profilePicture;
    private Product favoriteItem;

    public TapUser(int id, Bitmap profilePicture, Product favoriteItem) {
        this.id = id;
        this.profilePicture = profilePicture;
        this.favoriteItem = favoriteItem;
    }

    public int getId() {
        return id;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public Product getFavoriteItem() {
        return favoriteItem;
    }
}
