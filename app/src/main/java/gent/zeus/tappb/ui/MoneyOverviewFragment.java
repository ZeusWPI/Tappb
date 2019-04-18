package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.MoneyListener;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentMoneyOverviewBinding;


public class MoneyOverviewFragment extends Fragment implements MoneyListener {

    public MoneyOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMoneyOverviewBinding binding = FragmentMoneyOverviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
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
}
