package gent.zeus.tappb.entity;

import gent.zeus.tappb.api.TapAPI;

public class TapUser {
    private int id;
    private String profilePictureURL;
    private Product favoriteItem;
    private boolean isPrivate, favoriteItemHidden;

    public TapUser(int id, String profilePictureURL, Product favoriteItem, boolean isPrivate, boolean favoriteItemHidden) {
        this.id = id;
        this.profilePictureURL = profilePictureURL;
        this.favoriteItem = favoriteItem;
        this.isPrivate = isPrivate;
        this.favoriteItemHidden = favoriteItemHidden;
    }

    public int getId() {
        return id;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public Product getFavoriteItem() {
        return favoriteItem;
    }

    public void setFavoriteItem(Product p) {
        TapAPI.setFavoriteItem(p);
        this.favoriteItem = p;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        TapAPI.setPrivate(aPrivate);
        isPrivate = aPrivate;
    }

    public boolean isFavoriteItemHidden() {
        return favoriteItemHidden;
    }

    public void setFavoriteItemHidden(boolean favoriteItemHidden) {
        TapAPI.setFavoriteItemHidden(favoriteItemHidden);
        this.favoriteItemHidden = favoriteItemHidden;
    }
}
