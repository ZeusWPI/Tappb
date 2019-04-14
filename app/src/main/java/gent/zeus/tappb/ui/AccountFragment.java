package gent.zeus.tappb.ui;

import android.icu.text.DecimalFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import gent.zeus.tappb.databinding.FragmentAccountBinding;
import gent.zeus.tappb.viewmodel.AccountViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    private AccountViewModel viewModel;
    private DecimalFormat formatter = new DecimalFormat("€ #0.00;€ -#0.00");


    public AccountFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAccountBinding binding = FragmentAccountBinding.inflate(inflater, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        viewModel.init();
        viewModel.getUser().observe(this, binding::setUser);

        binding.setFormatter(formatter);
        binding.setHandler(this);

        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public boolean setProfilePicture() {
        Toast.makeText(getContext(), "TODO: set profile picture", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean setFavoriteDrink() {
        Toast.makeText(getContext(), "TODO: set favorite drink", Toast.LENGTH_SHORT).show();
        return true;
    }
}
