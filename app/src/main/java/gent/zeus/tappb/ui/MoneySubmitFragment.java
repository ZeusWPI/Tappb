package gent.zeus.tappb.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import gent.zeus.tappb.handlers.BasicDialogListener;

public abstract class MoneySubmitFragment extends Fragment implements OnBackPressedCallback,
                                                                      View.OnClickListener,
        BasicDialogListener {
    @Override
    public boolean handleOnBackPressed() {
        navigateBack();
        return true;
    }

    @Override
    public void onClick(View v) {
        closeKeyboard();
    }

    @Override
    public abstract void onDialogPositiveClick(DialogFragment dialog);

    @Override
    public abstract void onDialogNegativeClick(DialogFragment dialog);

    protected void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }

    protected void showDialog(String title, String message) {
        FragmentManager fm = getFragmentManager();
        DialogFragment dialog = new ConfirmationDialogFragment();
        dialog.setTargetFragment(this, 0);

        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        dialog.setArguments(bundle);
        dialog.show(fm, "ConfirmationDialogFragment");
    }

    protected void closeKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
