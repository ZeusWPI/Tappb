package gent.zeus.tappb.viewmodel;

import gent.zeus.tappb.entity.Transaction;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private LiveData<List<Transaction>> history;

    public void init() {
        initializeHistory();
    }

    private void initializeHistory() {
        history = new MutableLiveData<>();
        // TODO
//        ((MutableLiveData<List<Transaction>>) history).setValue(TabAPI.getTransactions());

    }

    public LiveData<List<Transaction>> getHistory() {
        return history;
    }
}
