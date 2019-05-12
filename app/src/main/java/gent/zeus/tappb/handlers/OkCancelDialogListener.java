package gent.zeus.tappb.handlers;

import androidx.fragment.app.DialogFragment;

public interface OkCancelDialogListener {
    void onDialogPositiveClick(DialogFragment dialog);
    void onDialogNegativeClick(DialogFragment dialog);
}
