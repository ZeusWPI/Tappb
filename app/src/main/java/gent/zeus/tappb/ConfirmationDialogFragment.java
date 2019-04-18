package gent.zeus.tappb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class ConfirmTransferDialogFragment extends DialogFragment {

    private BasicDialogListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        builder.setMessage(R.string.confirm_transfer)
                .setPositiveButton("Confirm", (dialog, which) -> listener.onDialogPositiveClick(ConfirmTransferDialogFragment.this))
                .setNegativeButton("Cancel", (dialog, which) -> listener.onDialogNegativeClick(ConfirmTransferDialogFragment.this));
        return builder.create();
    }
}
