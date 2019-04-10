package com.example.tappb.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tappb.databinding.HistoryItemBinding;
import com.example.tappb.entity.Transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    private DecimalFormat costFormatter = new DecimalFormat("#0.00");

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
            itemBinding.setCostFormatter(HistoryListAdapter.this.costFormatter);
        }

        public void bind(Transaction item) {
            itemBinding.setTransaction(item);
        }
    }
}
