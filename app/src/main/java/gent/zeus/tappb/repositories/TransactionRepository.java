package gent.zeus.tappb.repositories;

import gent.zeus.tappb.api.TabAPI;

public class TransactionRepository {
    private static final TransactionRepository ourInstance = new TransactionRepository();
    private TabAPI tabAPI = new TabAPI();

    public static TransactionRepository getInstance() {
        return ourInstance;
    }

    private TransactionRepository() {
    }

    public void createTransaction(String from, String to, int amount, String message) {
        tabAPI.createTransaction(from, to, amount, message);
    }
}
