package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.databinding.FragmentTopUpBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TopUpFragment extends Fragment implements OnBackPressedCallback {

    public TopUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTopUpBinding binding = FragmentTopUpBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), this);

        return binding.getRoot();
    }

    @Override
    public boolean handleOnBackPressed() {
        NavHostFragment.findNavController(this).navigateUp();
        return true;
    }
}
