package gent.zeus.tappb.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.repositories.UserRepository;

public class AccountViewModel extends ViewModel {
    //TODO: implement API

    private LiveData<User> user;
    private LiveData<TapUser> tapUser;
    private LiveData<String> profileURL;
    private LiveData<String> userName;
    private LiveData<String> favoriteItemName;
    private StockViewModel stockViewModel;


    public void init() {
        user = UserRepository.getInstance().getUser();
        profileURL = Transformations.map(tapUser, TapUser::getProfilePictureURL);
        userName = Transformations.map(user, User::getUsername);
        favoriteItemName = Transformations.map(tapUser, (usr) -> usr.getFavoriteItem().getName());
    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<TapUser> getTapUser() {
        return UserRepository.getInstance().getTapUser();
    }
    public LiveData<String> getProfileURL() {
        return profileURL;
    }
    public LiveData<String> getUserName() {
        return userName;
    }
    public LiveData<String> getFavoriteItemName() {
        return favoriteItemName;
    }

    public void setProfilePicture(Bitmap icon) {
        User currUser = user.getValue();
    }
    public boolean isLoggedIn() {
        return user.getValue() != null;
    }
}
