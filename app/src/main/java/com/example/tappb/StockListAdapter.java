package com.example.tappb;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tappb.databinding.StockItemBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StockListAdapter extends ListAdapter<Product, StockListAdapter.ViewHolder> {

    public StockListAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Product>() {
                @Override
                public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Product oldItem,  @NonNull Product newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(StockItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private StockItemBinding itemBinding;

        public ViewHolder(@NonNull StockItemBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
        }

        public void bind(Product item) {
            itemBinding.setProduct(item);
        }
    }
}
