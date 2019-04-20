package gent.zeus.tappb.entity;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.graphics.Bitmap;

import gent.zeus.tappb.api.TapAPI;

public class User {
    private static MutableLiveData<User> liveInstance = new MutableLiveData<>();
    private TapUser tapUser;
    private String username;
    private String tabToken;
    private String tapToken;
    private boolean loaded = false;

    private Product favoriteItem;

    public void load(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
        this.loaded = true;
    }

    //private constructor to avoid client applications to use constructor
    private User() {}
    
    public Product getFavoriteItem() {
        //TODO get from API
        if (favoriteItem == null) {
            favoriteItem = new Product(7730, "Drenk", 1.33);
        }
        return favoriteItem;
    }

    public void setFavoriteItem(Product favoriteItem) {
        this.favoriteItem = favoriteItem;
    }

    public static User getInstance(){
        return liveInstance.getValue();
    }

    public static LiveData<User> getLiveInstance() {
        if (liveInstance.getValue() == null) {
            liveInstance.setValue(new User());
        }
        return liveInstance;
    }

    private static void assertLoaded() {
        if (liveInstance.getValue() == null || !liveInstance.getValue().loaded) {
            throw new RuntimeException("User is not loaded yet");
        }
    }

    public String getUsername() {
        assertLoaded();
        return username;
    }

    public String getTabToken() {
        assertLoaded();
        return tabToken;
    }

    public String getTapToken() {
        assertLoaded();
        return tapToken;
    }

    public TapUser getTapUser() {
        if (tapUser == null) {
            this.tapUser = TapAPI.getTapUser(this);
        }
        return tapUser;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public static void logout() {
        User.assertLoaded();
        liveInstance.setValue(new User());
    }
}
