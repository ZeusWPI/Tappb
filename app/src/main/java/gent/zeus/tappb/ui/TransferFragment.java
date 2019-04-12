package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.databinding.FragmentTransferBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment implements OnBackPressedCallback {


    public TransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTransferBinding binding = FragmentTransferBinding.inflate(inflater, container, false);
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
