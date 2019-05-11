package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.util.NotAuthenticatedException;

@SuppressWarnings("ConstantConditions")
public class UserRepository {
    private static final UserRepository ourInstance = new UserRepository();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<TapUser> tapUser = new MutableLiveData<>();
    private TapAPI api = new TapAPI();

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
        LiveData<TapUser> apiTapUser = api.getTapUser();
        apiTapUser.observeForever(tapusr -> tapUser.setValue(tapusr));
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
        assertLoggedIn();
        api.fetchTapUser(user.getValue());
        return tapUser;
    }

    public String getUsername() {
        assertLoggedIn();
        return user.getValue().getUsername();
    }

    public String getTabToken() {
        assertLoggedIn();
        return user.getValue().getTabToken();
    }

    public boolean isLoggedIn() {
        return user.getValue() != null;
    }

    private void assertLoggedIn() {
        if (!isLoggedIn()) {
            throw new NotAuthenticatedException();
        }
    }
}
