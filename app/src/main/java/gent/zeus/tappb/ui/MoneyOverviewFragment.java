package gent.zeus.tappb.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.DecimalFormat;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentMoneyOverviewBinding;
import gent.zeus.tappb.handlers.MoneyListener;
import gent.zeus.tappb.repositories.UserRepository;
import gent.zeus.tappb.viewmodel.MoneyViewModel;


public class MoneyOverviewFragment extends Fragment implements MoneyListener, SwipeRefreshLayout.OnRefreshListener {

    private DecimalFormat formatter = new DecimalFormat("#0.00");
    private MoneyViewModel viewModel;

    public MoneyOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMoneyOverviewBinding binding = FragmentMoneyOverviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(getActivity()).get(MoneyViewModel.class);
        viewModel.init();

        viewModel.getBalanceInCents().observe(this, (newBalance) -> {
            binding.refresher.setRefreshing(false);
            if (UserRepository.getInstance().isLoggedIn()) {
                binding.balance.setText("€" + formatter.format(((double) newBalance) / 100));
            } else {
                binding.balance.setText(getString(R.string.not_logged_in));
            }
        });
        binding.refresher.setOnRefreshListener(this);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onTopUpClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.nav_top_up);
    }

    @Override
    public void onTransferClicked() {
        NavHostFragment.findNavController(this).navigate(R.id.nav_transfer);
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }
}
