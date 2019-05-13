package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gent.zeus.tappb.adapters.HistoryListAdapter;
import gent.zeus.tappb.databinding.FragmentHistoryBinding;
import gent.zeus.tappb.viewmodel.HistoryViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private HistoryViewModel viewModel;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentHistoryBinding binding = FragmentHistoryBinding.inflate(inflater, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(HistoryViewModel.class);
        viewModel.init();

        HistoryListAdapter adapter = new HistoryListAdapter();
        viewModel.getHistory().observe(this, list -> {
            adapter.submitList(list);
            binding.refresher.setRefreshing(false);
        });
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.setLifecycleOwner(this);
        binding.refresher.setOnRefreshListener(this);

        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        viewModel.refreshHistory();
    }
}
