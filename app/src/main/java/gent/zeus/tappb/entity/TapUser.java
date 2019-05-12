package gent.zeus.tappb.entity;

import androidx.annotation.Nullable;

public class TapUser {
    private int id;
    private String profilePictureURL;
    @Nullable
    private Integer favoriteItemId;
    private boolean isPrivate, favoriteItemHidden;

    public TapUser(int id, String profilePictureURL, Integer favoriteItem, boolean isPrivate, boolean favoriteItemHidden) {
        this.id = id;
        this.profilePictureURL = profilePictureURL;
        this.favoriteItemId = favoriteItem;
        this.isPrivate = isPrivate;
        this.favoriteItemHidden = favoriteItemHidden;
    }

    public int getId() {
        return id;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    @Nullable
    public Integer getFavoriteItemId() {
        return favoriteItemId;
    }


    public boolean isPrivate() {
        return isPrivate;
    }


    public boolean isFavoriteItemHidden() {
        return favoriteItemHidden;
    }

}
