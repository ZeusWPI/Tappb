package gent.zeus.tappb;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gent.zeus.tappb.ui.OkCancelDialogListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderFragment extends DialogFragment {
    private OkCancelDialogListener listener;


    public ConfirmOrderFragment(OkCancelDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm order?")
                .setPositiveButton("Confirm", (dialog, which) -> listener.onDialogPositiveClick(ConfirmOrderFragment.this))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onDialogNegativeClick(ConfirmOrderFragment.this));
        return builder.create();
    }

}
