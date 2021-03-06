package gent.zeus.tappb.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.text.DecimalFormat;

import gent.zeus.tappb.R;
import gent.zeus.tappb.adapters.OrderListAdapter;
import gent.zeus.tappb.databinding.FragmentOrderpageBinding;
import gent.zeus.tappb.handlers.OkCancelDialogListener;
import gent.zeus.tappb.handlers.OrderPageListener;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class OrderPageFragment extends Fragment implements OrderPageListener {
    private OrderViewModel viewModel;
    private OrderListAdapter adapter;
    private FragmentOrderpageBinding binding;
    private DecimalFormat formatter = new DecimalFormat("#0.00");

    public OrderPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderpageBinding.inflate(inflater, container, false);
        binding.setHandler(this);

        viewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);

        adapter = new OrderListAdapter(viewModel.getOrders().getValue(), viewModel);
        binding.productList.setAdapter(adapter);
        binding.productList.setItemAnimator(null);
        binding.productList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        viewModel.getOrders().observe(this, adapter::setOrderList);
        viewModel.getScanningState().observe(this, this::setButtonText);
        viewModel.getTotalPrice().observe(this, price -> binding.totalPrice.setText("€" + formatter.format(price)));


        return binding.getRoot();
    }

    @Override
    public void takePicture() {
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_order_to_nav_camera);
    }

    @Override
    public void executeOrder() {
        OkCancelDialogFragment dialog = OkCancelDialogFragment.newInstance(getString(R.string.order_confirmation));
        dialog.setListener(new OkCancelDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                viewModel.makeOrder();
            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {

            }
        });
        dialog.show(getFragmentManager(), "ConfirmOrderDialogFragment");

    }

    @Override
    public void clearOrder() {
        OkCancelDialogFragment dialog = OkCancelDialogFragment.newInstance(getString(R.string.delete_all_items));
        dialog.setListener(new OkCancelDialogListener() {
            @Override
            public void onDialogPositiveClick(DialogFragment dialog) {
                viewModel.clearOrder();
            }

            @Override
            public void onDialogNegativeClick(DialogFragment dialog) {

            }
        });
        dialog.show(getFragmentManager(), "ConfirmDeleteOrderDialogFragment");
    }

    private void setButtonText(OrderViewModel.ScanningState scanningState) {
        ImageButton button = binding.cameraButton;
        switch (scanningState) {
            case SCANNING:
                button.setEnabled(false);
                break;
            case ERROR:
                button.setEnabled(true);
                break;
            case NOT_SCANNING:
                button.setEnabled(true);
                break;
            case EMPTY:
                button.setEnabled(true);
                break;
        }
    }
}
