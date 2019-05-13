package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.ViewModel;

import gent.zeus.tappb.repositories.TransactionRepository;
import gent.zeus.tappb.repositories.UserRepository;

public class TransferViewModel extends ViewModel {

    public void init() {

    }

    public void createTransaction(String to, int amount, String message) {
        TransactionRepository.getInstance().createTransaction(UserRepository.getInstance().getUsername(), to, amount, message);
    }
}
