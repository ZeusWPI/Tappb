package gent.zeus.tappb.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.ui.OrderItemListener;

public class OrderListAdapter extends ListAdapter<OrderProduct, OrderListAdapter.ViewHolder> {


    private OrderItemListener listener;
    private DecimalFormat formatter = new DecimalFormat("#0.00");
    public static final DiffUtil.ItemCallback<OrderProduct> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<OrderProduct>() {
                @Override
                public boolean areItemsTheSame(@NonNull OrderProduct oldItem, @NonNull OrderProduct newItem) {
                    boolean rslt = ((oldItem.getProduct().equals(newItem.getProduct()))
                            && (oldItem.getCount() == newItem.getCount()));
                    Log.d("areItemsTheSame", Boolean.toString(rslt));
                    return rslt;
                }

                @Override
                public boolean areContentsTheSame(@NonNull OrderProduct oldItem, @NonNull OrderProduct newItem) {
                    return oldItem.getProduct().equals(newItem.getProduct())
                            && oldItem.getCount() == newItem.getCount();
                }
            };

    public OrderListAdapter(OrderItemListener listener) {
        super(DIFF_CALLBACK);
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
        holder.bind(getItem(position));
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
            itemBinding.setProductName(item.getProduct().getName());
            itemBinding.setProductCount(item.getCount());
            itemBinding.setProductPrice(item.getPrice());
        }
    }
}
