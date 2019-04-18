package gent.zeus.tappb.ui;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.ConfirmTransferDialogFragment;
import gent.zeus.tappb.MoneyTextWatcher;
import gent.zeus.tappb.databinding.FragmentTransferBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment implements OnBackPressedCallback,
                                                          View.OnClickListener,
                                                          ConfirmTransferDialogFragment.TransferDialogListener {

    private FragmentTransferBinding binding;

    public TransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransferBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.transfer.setOnClickListener(this);
        binding.amountInput.addTextChangedListener(new MoneyTextWatcher(binding.amountInput));

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), this);

        return binding.getRoot();
    }

    @Override
    public boolean handleOnBackPressed() {
        navigateBack();
        return true;
    }

    @Override
    public void onClick(View v) {
        //Close the keyboard
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

        boolean isValid = true;

        String name = binding.nameInput.getText().toString();
        if (name.isEmpty()) {
            binding.nameInput.setError("No name given");
            isValid = false;
        }

        String amount = binding.amountInput.getText().toString();
        if (amount.isEmpty()) {
            binding.amountInput.setError("Invalid amount");
            isValid = false;
        } else {
            double parsed = Double.parseDouble(amount);
            isValid &= parsed > 0;
        }

        if (isValid) {
            DialogFragment dialog = new ConfirmTransferDialogFragment(this);
            dialog.show(getFragmentManager(), "ConfirmTransferDialogFragment");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(getContext(), "CONFIRMED", Toast.LENGTH_SHORT).show();
        navigateBack();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(getContext(), "DECLINED", Toast.LENGTH_SHORT).show();
    }

    private void navigateBack() {
        NavHostFragment.findNavController(this).navigateUp();
    }
}
