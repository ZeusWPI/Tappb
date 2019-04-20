package gent.zeus.tappb.viewmodel;

import gent.zeus.tappb.entity.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

        transactionList.add(new Transaction(0, OffsetDateTime.parse("2019-03-13T21:11:50.000+01:00"), "User", "Zeus","Cola", -2.50));
        transactionList.add(new Transaction(1, OffsetDateTime.parse("2018-04-13T21:11:50.000+01:00"), "Zeus", "User","Schuldafbetaling", 10));
        transactionList.add(new Transaction(2, OffsetDateTime.parse("2081-05-13T21:11:50.000+01:00"), "User", "Zeus","Ice Tea", -2000));
        transactionList.add(new Transaction(3, OffsetDateTime.parse("2010-06-13T21:11:50.000+01:00"),"User", "Zeus","Bier", -4));
        transactionList.add(new Transaction(4, OffsetDateTime.parse("2006-07-13T21:11:50.000+01:00"), "User", "Zeus","Snoep", -1.3));

        ((MutableLiveData<List<Transaction>>) history).setValue(transactionList);
    }

    public LiveData<List<Transaction>> getHistory() {
        return history;
    }
}
