package gent.zeus.tappb.viewmodel;

import gent.zeus.tappb.entity.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {
    //TODO: implement API

    LiveData<List<Transaction>> history;

    public void init() {
        initializeHistory();
    }

    public void initializeHistory() {
        history = new MutableLiveData<>();
        List<Transaction> transactionList = new ArrayList<>();

        transactionList.add(new Transaction(0, LocalDate.of(1999, 2, 15), "User", "Zeus","Cola", -2.50));
        transactionList.add(new Transaction(1, LocalDate.of(2007, 8, 16), "Zeus", "User","Schuldafbetaling", 10));
        transactionList.add(new Transaction(2, LocalDate.of(2016, 12, 31), "User", "Zeus","Ice Tea", -2000));
        transactionList.add(new Transaction(3, LocalDate.of(2081, 3, 1),"User", "Zeus","Bier", -4));
        transactionList.add(new Transaction(4, LocalDate.of(2018, 4, 10), "User", "Zeus","Snoep", -1.3));

        ((MutableLiveData<List<Transaction>>) history).setValue(transactionList);
    }

    public LiveData<List<Transaction>> getHistory() {
        return history;
    }
}
