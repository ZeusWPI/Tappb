package gent.zeus.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.ui.viewholders.OrderItemViewHolder;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class OrderListAdapter extends RecyclerView.Adapter<OrderItemViewHolder> {


    private OrderViewModel viewModel;
    @Nullable
    private List<OrderProduct> orderList;

    public OrderListAdapter(@Nullable List<OrderProduct> orderList, OrderViewModel viewModel) {
        this.orderList = orderList;
        this.viewModel = viewModel;
    }

    public void setOrderList(List<OrderProduct> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemViewHolder(OrderItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false), viewModel, this);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemViewHolder holder, int position) {
        if (orderList != null) {
            holder.bind(orderList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }
}
