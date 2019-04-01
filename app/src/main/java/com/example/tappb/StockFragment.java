package com.example.tappb;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.tappb.databinding.FragmentStockBinding;

public class StockFragment extends Fragment implements StockAdapter.StockListener {

    StockViewModel viewModel;
    StockAdapter adapter;

    public StockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStockBinding binding = FragmentStockBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(StockViewModel.class);
        viewModel.init();

        adapter = new StockAdapter(viewModel.getStock().getValue(), this);
        binding.recyclerView.setAdapter(adapter);
        viewModel.getStock().observe(this, adapter::setProducts);
        binding.setLifecycleOwner(this);
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stock_menu, menu);

        MenuItem searched = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searched.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onClick(String name) {
        StockDialogFragment dialog = new StockDialogFragment();
        dialog.show(getFragmentManager(), "Confirm");
    }
}
