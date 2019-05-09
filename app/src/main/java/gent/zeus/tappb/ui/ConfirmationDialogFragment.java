package gent.zeus.tappb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import gent.zeus.tappb.handlers.BasicDialogListener;

public class ConfirmationDialogFragment extends DialogFragment {

    private BasicDialogListener listener;

    private String title;
    private String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            message = bundle.getString("message");
        }

        try {
            listener = (BasicDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFriendListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Confirm", (dialog, which) -> listener.onDialogPositiveClick(ConfirmationDialogFragment.this))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onDialogNegativeClick(ConfirmationDialogFragment.this));
        return builder.create();
    }
}
