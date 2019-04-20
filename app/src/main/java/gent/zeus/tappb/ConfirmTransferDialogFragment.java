package gent.zeus.tappb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import gent.zeus.tappb.ui.OkCancelDialogListener;

public class ConfirmTransferDialogFragment extends DialogFragment {


    private OkCancelDialogListener listener;

    public ConfirmTransferDialogFragment(OkCancelDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_transfer)
                .setPositiveButton("Confirm", (dialog, which) -> listener.onDialogPositiveClick(ConfirmTransferDialogFragment.this))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onDialogNegativeClick(ConfirmTransferDialogFragment.this));
        return builder.create();
    }
}
