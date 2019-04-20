package gent.zeus.tappb.viewmodel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import gent.zeus.tappb.entity.Transaction;
import gent.zeus.tappb.entity.User;

public class AccountViewModel extends ViewModel {
    //TODO: implement API

    MutableLiveData<User> user = new MutableLiveData<>();

    public void init() {
        user.setValue(User.getInstance());
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setProfilePicture(Bitmap icon) {
        User currUser = user.getValue();
        user.setValue(currUser);
    }
}
