package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import gent.zeus.tappb.api.TabAPI;
import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.util.NotAuthenticatedException;


@SuppressWarnings("ConstantConditions")
public class UserRepository {
    // TODO: this class has a circular dependency on StockRepository
    // leading to the wacky intialization of the fav item
    // need to research how to best handle this

    public enum UserStatus {
        LOGGED_IN,
        LOGGED_OUT,
        ERROR
    }

    private static final UserRepository ourInstance = new UserRepository();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<TapUser> tapUser = new MutableLiveData<>();
    private TapAPI tapAPI = new TapAPI();
    private TabAPI tabAPI = new TabAPI();
    private MutableLiveData<UserStatus> status = new MutableLiveData<>();
    private LiveData<StockProduct> favoriteItem;
    private MutableLiveData<Integer> accountBalanceCents = new MutableLiveData<>();

    public static UserRepository getInstance() {
        return ourInstance;
    }

    private UserRepository() {
        LiveData<TapUser> apiTapUser = tapAPI.getTapUser();
        apiTapUser.observeForever(tapusr -> {
            tapUser.setValue(tapusr);
            StockRepository.getInstance().setRequestedId(tapusr.getFavoriteItemId());
        });
        status.setValue(UserStatus.LOGGED_OUT);
        tabAPI.getBalanceInCents().observeForever((bal) -> accountBalanceCents.postValue(bal));

    }

    public void logout() {
        status.setValue(UserStatus.LOGGED_OUT);
    }

    public void load(String username, String tab_token, String tap_token) {
        user.setValue(new User(username, tab_token, tap_token));
        status.setValue(UserStatus.LOGGED_IN);
    }

    public LiveData<StockProduct> getFavoriteItem() {
        if (favoriteItem == null) {
            favoriteItem = StockRepository.getInstance().getRequestedProduct();

        }
        return favoriteItem;
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<UserStatus> getStatus() {
        return status;
    }

    public LiveData<TapUser> getTapUser() {
        assertLoggedIn();
        tapAPI.fetchTapUser(user.getValue());
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

    public LiveData<Integer> getAccountBalanceCents() {
        return accountBalanceCents;
    }

    public boolean isLoggedIn() {
        return status.getValue() == UserStatus.LOGGED_IN;
    }
    public void fetchAll() {
        tabAPI.fetchBalanceInCents();
        tapAPI.fetchTapUser(user.getValue());
    }
    public void uploadDeviceRegistrationToken(String token) {
        tabAPI.uploadDeviceRegistrationToken(user.getValue(), token);
    }
    public void setPrivate(boolean aPrivate) {
        tapAPI.setPrivate(user.getValue(), aPrivate);
    }
    public void setFavoriteItem(Product p) {
        tapAPI.setFavoriteItem(user.getValue(), p);
    }
    public void setFavoriteItemHidden(boolean favoriteItemHidden) {
        tapAPI.setFavoriteItemHidden(user.getValue(), favoriteItemHidden);
    }


    private void assertLoggedIn() {
        if (!isLoggedIn()) {
            throw new NotAuthenticatedException();
        }
    }
}
