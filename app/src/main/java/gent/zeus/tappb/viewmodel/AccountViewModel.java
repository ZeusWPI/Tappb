package gent.zeus.tappb.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.TapUser;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.repositories.StockRepository;
import gent.zeus.tappb.repositories.UserRepository;

import static gent.zeus.tappb.repositories.StockRepository.getInstance;

public class AccountViewModel extends ViewModel {
    //TODO: implement API

    private LiveData<User> user;
    private LiveData<TapUser> tapUser;
    private LiveData<String> profileURL;
    private LiveData<String> userName;
    private LiveData<String> favoriteItemName;


    public void init() {
        user = UserRepository.getInstance().getUser();
        tapUser = UserRepository.getInstance().getTapUser();
        profileURL = Transformations.map(tapUser, TapUser::getProfilePictureURL);
        userName = Transformations.map(user, User::getUsername);
        favoriteItemName = Transformations.map(UserRepository.getInstance().getFavoriteItem(), prod -> prod != null ? prod.getName() : null);

    }

    public LiveData<User> getUser() {
        return user;
    }

    public LiveData<TapUser> getTapUser() {
        return tapUser;
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
