package gent.zeus.tappb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import gent.zeus.tappb.databinding.FragmentTopUpBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TopUpFragment extends Fragment {


    public TopUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTopUpBinding binding = FragmentTopUpBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
