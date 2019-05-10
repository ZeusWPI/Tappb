package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;

public class UserRepository {
    private static final UserRepository ourInstance = new UserRepository();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private LiveData<TapUser> tapUser;

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
    }

    public static void logout() {
    }

    public void load(String username, String tab_token, String tap_token) {
        user.setValue(new User(username, tab_token, tap_token));
    }
    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<TapUser> getTapUser() {
        if (tapUser == null) {
            tapUser = TapAPI.getTapUser(user.getValue());
        }
        return tapUser;
    }

    public String getUsername() {
        return user.getValue().getUsername();
    }

    public String getTabToken() {
        return user.getValue().getTabToken();
    }
}
