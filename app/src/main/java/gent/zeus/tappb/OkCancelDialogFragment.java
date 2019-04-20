package gent.zeus.tappb;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import gent.zeus.tappb.ui.OkCancelDialogListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class OkCancelDialogFragment extends DialogFragment {
    private OkCancelDialogListener listener;
    private String message;
    private String okText;
    private String cancelText;


    public OkCancelDialogFragment(OkCancelDialogListener listener, String message, String okText, String cancelText) {
        this.listener = listener;
        this.message = message;
        this.okText = okText;
        this.cancelText = cancelText;
    }

    public OkCancelDialogFragment(OkCancelDialogListener listener, String message) {
        this(listener, message, "Confirm", "Cancel");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message)
                .setPositiveButton(okText, (dialog, which) -> listener.onDialogPositiveClick(OkCancelDialogFragment.this))
                .setNegativeButton(cancelText, (dialog, which) -> listener.onDialogNegativeClick(OkCancelDialogFragment.this));
        return builder.create();
    }

}
