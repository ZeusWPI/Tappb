package gent.zeus.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import gent.zeus.tappb.databinding.OrderItemBinding;
import gent.zeus.tappb.entity.OrderProduct;

public class OrderListAdapter extends ListAdapter<OrderProduct, OrderListAdapter.ViewHolder> {

    public interface OrderListener {
        void onClick(OrderProduct orderProduct);
        void onIncreaseClicked();
        void onDecreaseClicked();
    }

    private List<OrderProduct> orderProducts;
    private OrderListener listener;
    private DecimalFormat formatter = new DecimalFormat("#0.00");

    public static final DiffUtil.ItemCallback<OrderProduct> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<OrderProduct>() {
                @Override
                public boolean areItemsTheSame(@NonNull OrderProduct oldItem,  @NonNull OrderProduct newItem) {
                    return oldItem.getProduct().getId() == newItem.getProduct().getId();
                }
                @Override
                public boolean areContentsTheSame(@NonNull OrderProduct oldItem,  @NonNull OrderProduct newItem) {
                    return oldItem.getProduct().equals(newItem.getProduct())
                            && oldItem.getCount() == newItem.getCount();
                }
            };

    public OrderListAdapter(OrderListener listener) {
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
            itemBinding.setOrderProduct(item);
        }
    }
}
