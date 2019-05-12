package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import gent.zeus.tappb.R;
import gent.zeus.tappb.handlers.MoneyTextWatcher;
import gent.zeus.tappb.api.TabAPI;
import gent.zeus.tappb.databinding.FragmentTransferBinding;
import gent.zeus.tappb.entity.User;
import gent.zeus.tappb.handlers.OkCancelDialogListener;
import gent.zeus.tappb.repositories.UserRepository;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends MoneySubmitFragment implements
        View.OnClickListener,
        OkCancelDialogListener {

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
            binding.nameInput.setError(getString(R.string.no_name));
            isValid = false;
        }

        String amount = binding.amountInput.getText().toString();
        if (amount.isEmpty()) {
            binding.amountInput.setError(getString(R.string.invalid_amount));
            isValid = false;
        } else {
            double parsed = Double.parseDouble(amount);
            if (parsed <= 0) {
                binding.amountInput.setError(getString(R.string.invalid_amount));
                isValid = false;
            }
        }

        String message = binding.messageInput.getText().toString();

        String dialogMessage = getString(R.string.transfer_confirmation, amount, name);
        if (isValid) {
            DialogFragment dialog = OkCancelDialogFragment.newInstance(dialogMessage);
            dialog.show(getFragmentManager(), "ConfirmTransferDialogFragment");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Editable nameEditable = binding.nameInput.getText();
        Editable messageEditable = binding.messageInput.getText();
        Editable amountEditable = binding.amountInput.getText();
        if (nameEditable == null) {
            Toast.makeText(getContext(), "Please fill in a recipient", Toast.LENGTH_LONG).show();
        } else if (messageEditable == null) {
            Toast.makeText(getContext(), "Please supply a message", Toast.LENGTH_LONG).show();
        } else if (amountEditable == null) {
            Toast.makeText(getContext(), "Please specify an amount", Toast.LENGTH_LONG).show();
        } else {
            try {
                int amount = ((int) (Double.parseDouble(amountEditable.toString()) * 100));
                // TODO: reimplement
//                TabAPI.createTransaction(UserRepository.getInstance().getUser().getValue().getUsername(), nameEditable.toString(), amount, messageEditable.toString());
            } catch (NumberFormatException e) {
                Log.d("TransferFragment", amountEditable.toString(), e);
                Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_LONG).show();
            } catch (RuntimeException e) {
                Toast.makeText(getContext(), "Not logged in!", Toast.LENGTH_LONG).show();
            }
        }
        navigateBack();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
