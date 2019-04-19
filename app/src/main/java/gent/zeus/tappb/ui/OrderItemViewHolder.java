package gent.zeus.tappb.ui;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

import gent.zeus.tappb.adapters.OrderListAdapter;
import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class OrderItemViewHolder extends RecyclerView.ViewHolder implements OrderItemListener{

    private OrderItemBinding itemBinding;
    private int position;
    private DecimalFormat formatter = new DecimalFormat("#0.00");
    private OrderProduct orderProduct;
    private OrderViewModel viewModel;
    private OrderListAdapter adapter;

    public OrderItemViewHolder(@NonNull OrderItemBinding binding, OrderViewModel viewModel, OrderListAdapter adapter) {
        super(binding.getRoot());
        itemBinding = binding;
        itemBinding.setHandler(this);
        this.viewModel = viewModel;
        this.adapter = adapter;
    }

    public void bind(OrderProduct orderProduct, int position){
        this.position = position;
        this.orderProduct = orderProduct;
        itemBinding.setOrderProduct(orderProduct);
        itemBinding.setProductName(orderProduct.getProduct().getName());
        itemBinding.setProductCount(Integer.toString(orderProduct.getCount()));
        itemBinding.setProductPrice(formatter.format(orderProduct.getPrice()));
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onIncreaseClicked() {
        viewModel.increaseCount(orderProduct);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onDecreaseClicked() {
        viewModel.decreaseCount(orderProduct);
        adapter.notifyItemChanged(position);
    }
}
