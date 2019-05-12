package gent.zeus.tappb.viewmodel;

import gent.zeus.tappb.entity.Transaction;
import gent.zeus.tappb.repositories.HistoryRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    private LiveData<List<Transaction>> history;

    public void init() {
        history = HistoryRepository.getInstance().getTransactionList();
    }

    public LiveData<List<Transaction>> getHistory() {
        return history;
    }
}
