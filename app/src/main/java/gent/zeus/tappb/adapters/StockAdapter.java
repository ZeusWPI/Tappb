package gent.zeus.tappb.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.databinding.StockItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import gent.zeus.tappb.entity.StockProduct;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements Filterable {

    public interface StockListener {
        void onClick(StockProduct stockProduct);
    }

    private List<StockProduct> products;
    private List<StockProduct> productsFull;
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

    public void setProducts(List<StockProduct> products) {
        this.products = products;
        productsFull = new ArrayList<>(products);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<StockProduct> filteredProducts = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredProducts.addAll(productsFull);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();

                    for (StockProduct product: productsFull) {
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

        public void bind(StockProduct item) {
            itemBinding.setProduct(item);
            Picasso.get().load(item.getProduct().getImageURL()).into(itemBinding.image);
        }
    }
}
