package gent.zeus.tappb.ui;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import gent.zeus.tappb.R;
import gent.zeus.tappb.handlers.OkCancelDialogListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OkCancelDialogFragment extends DialogFragment {

    private OkCancelDialogListener listener;

    private String message;
    private String confirmText;
    private String cancelText;

    public OkCancelDialogFragment() {
        // Keep empty
    }

    public static OkCancelDialogFragment newInstance(String message) {
        return newInstance(message, null, null);
    }

    public static OkCancelDialogFragment newInstance(String message, String confirmText, String cancelText) {
        OkCancelDialogFragment dialog = new OkCancelDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("confirm", confirmText);
        args.putString("cancel", cancelText);
        dialog.setArguments(args);

        return dialog;
    }

    public void setListener(OkCancelDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();

        message = args.getString("message");
        confirmText = args.getString("confirm") == null ? getString(R.string.confirm) : args.getString("confirm");
        cancelText = args.getString("cancel") == null ? getString(R.string.cancel) : args.getString("cancel");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(confirmText, (dialog, which) -> listener.onDialogPositiveClick(OkCancelDialogFragment.this))
                .setNegativeButton(cancelText, (dialog, which) -> listener.onDialogNegativeClick(OkCancelDialogFragment.this));
        return builder.create();
    }

}
