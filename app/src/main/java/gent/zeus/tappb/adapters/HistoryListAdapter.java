package gent.zeus.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import gent.zeus.tappb.databinding.HistoryItemBinding;
import gent.zeus.tappb.entity.Transaction;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryListAdapter extends ListAdapter<Transaction, HistoryListAdapter.ViewHolder> {

    public static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Transaction>() {
                @Override
                public boolean areItemsTheSame(@NonNull Transaction oldItem,  @NonNull Transaction newItem) {
                    return oldItem.getId() == newItem.getId();
                }
                @Override
                public boolean areContentsTheSame(@NonNull Transaction oldItem,  @NonNull Transaction newItem) {
                    return oldItem.equals(newItem);
                }
            };

    private DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMM");

    public HistoryListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        HistoryItemBinding itemBinding;

        public ViewHolder(@NonNull HistoryItemBinding binding) {
            super(binding.getRoot());
            itemBinding = binding;
            itemBinding.setMonthFormatter(HistoryListAdapter.this.monthFormatter);
        }

        public void bind(Transaction item) {
            itemBinding.setTransaction(item);
        }
    }
}
