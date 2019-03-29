package com.example.tappb;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tappb.databinding.FragmentStockBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockFragment extends Fragment {

    LiveData<List<Product>> products;

    public StockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Product> p = new ArrayList<>();
        p.add(new Product(0, "Cola", 1.20, 5));
        p.add(new Product(1, "Ice-Tea", 1.20, 5));
        p.add(new Product(2, "Bier", 1.20, 5));
        p.add(new Product(3, "Mate", 1.20, 5));
        p.add(new Product(4, "Cola", 1.20, 5));
        p.add(new Product(5, "Ice-Tea", 1.20, 5));
        p.add(new Product(6, "Bier", 1.20, 5));
        p.add(new Product(7, "Mate", 1.20, 5));
        p.add(new Product(8, "Cola", 1.20, 5));
        p.add(new Product(9, "Ice-Tea", 1.20, 5));
        p.add(new Product(10, "Bier", 1.20, 5));
        p.add(new Product(11, "Mate", 1.20, 5));

        products = new MutableLiveData<>();

        FragmentStockBinding binding = FragmentStockBinding.inflate(inflater, container, false);
        StockListAdapter adapter = new StockListAdapter();
        products.observe(this, adapter::submitList);
        binding.recyclerView.setAdapter(adapter);
        ((MutableLiveData<List<Product>>) products).setValue(p);
        binding.setLifecycleOwner(this);

        return binding.getRoot();
    }
}
