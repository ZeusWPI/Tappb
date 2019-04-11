package com.example.tappb.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tappb.R;
import com.example.tappb.databinding.FragmentHomeBinding;
import com.example.tappb.databinding.FragmentMoneyBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyFragment extends Fragment {


    public MoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentMoneyBinding binding = FragmentMoneyBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
