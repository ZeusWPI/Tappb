package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentMoneyOverviewBinding;


public class MoneyOverviewFragment extends Fragment {

    private FragmentMoneyOverviewBinding binding;

    public MoneyOverviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoneyOverviewBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.topUp.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toTopUp));
        binding.transfer.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.toTransfer));
    }
}
