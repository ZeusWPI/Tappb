package gent.zeus.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentCameraBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {


    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCameraBinding binding = FragmentCameraBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
