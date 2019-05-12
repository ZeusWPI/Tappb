package gent.zeus.tappb.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import gent.zeus.tappb.repositories.UserRepository;

public class MoneyViewModel extends ViewModel {
    private LiveData<Integer> balanceInCents;

    public void init() {
        balanceInCents = UserRepository.getInstance().getAccountBalanceCents();

    }

    public LiveData<Integer> getBalanceInCents() {
        UserRepository.getInstance().fetchAll();
        return balanceInCents;
    }
}
