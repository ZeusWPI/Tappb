package gent.zeus.tappb.adapters;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private OrderViewModel.ScanningState state;

    public interface OrderListener {
        void onClick(OrderProduct orderProduct);
        void afterTextChanged(Editable s, OrderProduct orderProduct);
    }

    private List<OrderProduct> products;
    private OrderListener listener;
    private DecimalFormat formatter = new DecimalFormat("#0.00");

    public OrderAdapter(OrderListener listener) {
        this.listener = listener;
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
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private OrderItemBinding itemBinding;

        public ViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
            itemBinding.setHandler(OrderAdapter.this.listener);
            itemBinding.setFormatter(OrderAdapter.this.formatter);
        }

        public void bind(OrderProduct item) {
            itemBinding.setOrderProduct(item);
        }
    }
}
