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
        if (user.getValue() == null) {
            user.setValue(new User("Test", "tab", "tap"));
        }
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User u) {
        user.setValue(u);
    }

    public void setProfilePicture(Bitmap icon) {
        User currUser = user.getValue();
        currUser.setProfilePicture(icon);
        user.setValue(currUser);
    }
}
