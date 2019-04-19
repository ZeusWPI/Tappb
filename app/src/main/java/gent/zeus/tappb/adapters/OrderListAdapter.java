package gent.zeus.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.ui.OrderItemListener;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private OrderItemListener listener;
    private DecimalFormat formatter = new DecimalFormat("#0.00");
    private List<OrderProduct> orderList;

    public OrderListAdapter(List<OrderProduct> orderList, OrderItemListener listener) {
        this.listener = listener;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(OrderItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private OrderItemBinding itemBinding;

        public ViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
            itemBinding.setHandler(OrderListAdapter.this.listener);
            itemBinding.setFormatter(OrderListAdapter.this.formatter);
        }

        public void bind(OrderProduct item) {
            itemBinding.setOrderProduct(item);
            itemBinding.setProductName(item.getProduct().getName());
            itemBinding.setProductCount(item.getCount());
            itemBinding.setProductPrice(item.getPrice());
        }
    }
}
