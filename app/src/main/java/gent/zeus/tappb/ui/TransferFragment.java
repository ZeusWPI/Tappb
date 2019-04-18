package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import gent.zeus.tappb.MoneySubmitFragment;
import gent.zeus.tappb.MoneyTextWatcher;
import gent.zeus.tappb.databinding.FragmentTransferBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends MoneySubmitFragment {

    private FragmentTransferBinding binding;

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
        super.onClick(v);

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
            if (parsed <= 0) {
                binding.amountInput.setError("Invalid amount");
                isValid = false;
            }
        }

        String message = binding.messageInput.getText().toString();

        String dialogMessage = "Send " + amount + " to " + name + "?";
        if (isValid) {
            showDialog("Transfer money", dialogMessage);
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
}
