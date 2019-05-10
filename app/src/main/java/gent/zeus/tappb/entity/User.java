package gent.zeus.tappb.entity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.api.TapAPI;

public class User {
    private String username;
    private String tabToken;
    private String tapToken;


    //private constructor to avoid client applications to use constructor


    public User(String username, String tabToken, String tapToken) {
        this.username = username;
        this.tabToken = tabToken;
        this.tapToken = tapToken;
    }

    public String getUsername() {
        return username;
    }

    public String getTabToken() {
        return tabToken;
    }

    public String getTapToken() {
        return tapToken;
    }

}
