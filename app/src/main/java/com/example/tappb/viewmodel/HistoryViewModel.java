package com.example.tappb.viewmodel;

import com.example.tappb.entity.Transaction;

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

        transactionList.add(new Transaction(0, "Cola", -2.50, LocalDate.of(1999, 2, 15)));
        transactionList.add(new Transaction(1, "Schuldafbetaling", 10, LocalDate.of(2007, 8, 16)));
        transactionList.add(new Transaction(2, "Ice Tea", -2000, LocalDate.of(2016, 12, 31)));
        transactionList.add(new Transaction(3, "Bier", -4, LocalDate.of(2081, 3, 1)));
        transactionList.add(new Transaction(4, "Snoep", -1.3, LocalDate.of(2018, 4, 10)));

        ((MutableLiveData<List<Transaction>>) history).setValue(transactionList);
    }

    public LiveData<List<Transaction>> getHistory() {
        return history;
    }
}
