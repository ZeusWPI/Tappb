package gent.zeus.tappb.entity;

import androidx.annotation.Nullable;

public class TapUser {
    private int id;
    private String profilePictureURL;
    @Nullable
    private Integer favoriteItemId;

    public TapUser(int id, String profilePictureURL, Integer favoriteItem) {
        this.id = id;
        this.profilePictureURL = profilePictureURL;
        this.favoriteItemId = favoriteItem;
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
}
