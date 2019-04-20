package gent.zeus.tappb.entity;

import android.graphics.Bitmap;

public class TapUser {
    private int id;
    private Bitmap profilePicture;

    public TapUser(int id, Bitmap profilePicture) {
        this.id = id;
        this.profilePicture = profilePicture;
    }

    public int getId() {
        return id;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }
}
