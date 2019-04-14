package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import gent.zeus.tappb.R;
import gent.zeus.tappb.adapters.StockAdapter;
import gent.zeus.tappb.viewmodel.StockViewModel;
import gent.zeus.tappb.databinding.FragmentStockBinding;

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

        adapter = new StockAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
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
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

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
    public void onClick() {
        Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
    }
}