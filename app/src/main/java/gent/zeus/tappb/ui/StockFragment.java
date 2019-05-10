package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.viewmodel.OrderViewModel;
import gent.zeus.tappb.viewmodel.StockViewModel;
import gent.zeus.tappb.databinding.FragmentStockBinding;

public class StockFragment extends Fragment implements StockAdapter.StockListener, SearchView.OnQueryTextListener {

    private OrderViewModel orderViewModel;
    private StockViewModel stockViewModel;
    private StockAdapter adapter;

    private SearchView searchView;
    private String searchString;
    private static final String SEARCH_VIEW_KEY = "search";

    public StockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            searchString = savedInstanceState.getString(SEARCH_VIEW_KEY);
        }

        FragmentStockBinding binding = FragmentStockBinding.inflate(inflater, container, false);
        stockViewModel = ViewModelProviders.of(getActivity()).get(StockViewModel.class);
        stockViewModel.init();

        orderViewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);

        adapter = new StockAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        stockViewModel.getStock().observe(this, adapter::setProducts);
        stockViewModel.getFetchError().observe(this, (error) -> {
            if (error) {
                Toast.makeText(getContext(), "API fetch failed, showing sample data", Toast.LENGTH_LONG).show();
            }
        });
        binding.setLifecycleOwner(this);
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stock_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(this);

        if (searchString != null && !searchString.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchString, true);
            searchView.clearFocus();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        searchString = searchView.getQuery().toString();
        outState.putString(SEARCH_VIEW_KEY, searchString);
    }

    @Override
    public void onClick(StockProduct stockProduct) {
        Toast.makeText(getContext(), stockProduct.getName(), Toast.LENGTH_SHORT).show();
        orderViewModel.addProduct(stockProduct);
    }
}
