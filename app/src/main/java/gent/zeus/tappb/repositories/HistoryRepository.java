package gent.zeus.tappb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import gent.zeus.tappb.api.TabAPI;
import gent.zeus.tappb.entity.Transaction;

public class HistoryRepository {
    private static final HistoryRepository ourInstance = new HistoryRepository();
    private TabAPI tabAPI = new TabAPI();

    public static HistoryRepository getInstance() {
        return ourInstance;
    }

    private MutableLiveData<List<Transaction>> transactionList = new MutableLiveData<>();

    private HistoryRepository() {
        tabAPI.getTransactions().observeForever(transactions -> transactionList.postValue(transactions));
        tabAPI.fetchTransactions();
    }

    public LiveData<List<Transaction>> getTransactionList() {
        return transactionList;
    }
    public void fetchAll() {
        tabAPI.fetchTransactions();
    }
}
