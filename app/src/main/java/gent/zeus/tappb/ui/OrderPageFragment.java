package gent.zeus.tappb.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import gent.zeus.tappb.R;
import gent.zeus.tappb.adapters.OrderListAdapter;
import gent.zeus.tappb.databinding.FragmentOrderpageBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class OrderPageFragment extends Fragment implements OrderItemListener {
    private OrderViewModel viewModel;
    private OrderListAdapter adapter;
    private FragmentOrderpageBinding binding;

    public OrderPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderpageBinding.inflate(inflater, container, false);

        binding.setHandler(this);


        viewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
        adapter = new OrderListAdapter(viewModel.getOrders().getValue(), this);
        binding.productList.setAdapter(adapter);
        binding.productList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        viewModel.getOrders().observe(this, list -> adapter.notifyDataSetChanged());
        viewModel.getScanningState().observe(this, this::setButtonText);

        return binding.getRoot();
    }

    @Override
    public void onClick(OrderProduct orderProduct) {
        Toast.makeText(getContext(), orderProduct.getCount() + " " + orderProduct.getProduct().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIncreaseClicked(OrderProduct orderProduct) {
        viewModel.updateCount(orderProduct.getProduct(), orderProduct.getCount() + 1);
    }

    @Override
    public void onDecreaseClicked(OrderProduct orderProduct) {
        viewModel.updateCount(orderProduct.getProduct(), orderProduct.getCount() - 1);
    }

    public void takePicture(View ignored) {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_order_to_nav_camera);
    }

    private void setButtonText(OrderViewModel.ScanningState scanningState) {
        Button button = binding.button;
        switch (scanningState) {
            case SCANNING:
                button.setText(R.string.scanning);
                button.setEnabled(false);
                break;
            case ERROR:
                button.setText(R.string.scanning_error);
                button.setEnabled(true);
                break;
            case NOT_SCANNING:
                button.setText(R.string.scan_barcode);
                button.setEnabled(true);
                break;
            case EMPTY:
                button.setText(R.string.scanning_empty);
                button.setEnabled(true);
                break;
        }
    }
}
