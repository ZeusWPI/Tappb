package gent.zeus.tappb.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import gent.zeus.tappb.R;
import gent.zeus.tappb.adapters.StockAdapter;
import gent.zeus.tappb.databinding.FragmentStockBinding;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.StockProduct;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.viewmodel.StockViewModel;

public class FavoriteItemFragment extends Fragment implements StockAdapter.StockListener {

    StockViewModel viewModel;
    StockAdapter adapter;

    public FavoriteItemFragment() {
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
    public void onClick(StockProduct p) {
        User.getInstance().setFavoriteItem(p.getProduct());
        Toast.makeText(getContext(), p.getName(), Toast.LENGTH_SHORT).show();
        NavHostFragment.findNavController(this).navigateUp();
    }
}
