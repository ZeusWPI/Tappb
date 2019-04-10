package com.example.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.tappb.entity.Product;
import com.example.tappb.databinding.StockItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements Filterable {

    public interface StockListener {
        void onClick();
    }

    private List<Product> products;
    private List<Product> productsFull;
    private StockListener listener;
    private DecimalFormat formatter = new DecimalFormat("#0.00");

    public StockAdapter(StockListener listener) {
        this.listener = listener;
    }

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
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        productsFull = new ArrayList<>(products);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Product> filteredProducts = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredProducts.addAll(productsFull);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();

                    for (Product product: productsFull) {
                        if (product.getName().toLowerCase().contains(pattern)) {
                            filteredProducts.add(product);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredProducts;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                products.clear();
                products.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private StockItemBinding itemBinding;

        public ViewHolder(@NonNull StockItemBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
            itemBinding.setHandler(StockAdapter.this.listener);
            itemBinding.setFormatter(StockAdapter.this.formatter);
        }

        public void bind(Product item) {
            itemBinding.setProduct(item);
        }
    }
}
