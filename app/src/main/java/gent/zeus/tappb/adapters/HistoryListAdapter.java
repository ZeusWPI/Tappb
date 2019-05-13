package gent.zeus.tappb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import gent.zeus.tappb.R;
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
                        false), parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private HistoryItemBinding itemBinding;
        private Context context;

        public ViewHolder(@NonNull HistoryItemBinding binding, Context context) {
            super(binding.getRoot());
            this.context = context;
            itemBinding = binding;
            itemBinding.setMonthFormatter(HistoryListAdapter.this.monthFormatter);
        }

        public void bind(Transaction item) {
            itemBinding.setTransaction(item);
            itemBinding.personDescription.setText(getPersonDescription(item));
            itemBinding.cost.setText(getFormattedAmount(item));
            itemBinding.day.setText(getDayString(item));
        }

        private String getPersonDescription(Transaction item) {
            return item.isUserIsDebtor() ?
                    context.getResources().getString(R.string.to) + ": " + item.getCreditor() :
                    context.getResources().getString(R.string.from) + ": " + item.getDebtor();
        }

        private String getFormattedAmount(Transaction item) {
            DecimalFormat costFormatter = new DecimalFormat("#0.00");
            String formatted = "€" + costFormatter.format(item.getAmount());
            if (item.isUserIsDebtor()) {
                formatted = "-" + formatted;
            }
            return formatted;
        }

        private String getDayString(Transaction item) {
            int day = item.getDate().getDayOfMonth();
            return day > 9 ? Integer.toString(day) : "0" + day;
        }
    }
}
