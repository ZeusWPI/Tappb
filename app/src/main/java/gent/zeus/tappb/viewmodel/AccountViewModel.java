package gent.zeus.tappb.viewmodel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import gent.zeus.tappb.entity.Transaction;
import gent.zeus.tappb.entity.User;

public class AccountViewModel extends ViewModel {
    //TODO: implement API

    MutableLiveData<User> user = new MutableLiveData<>();

    public void init() {
        user.setValue(new User("Test", "tab","tap"));
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User u) {
        user.setValue(u);
    }
}
