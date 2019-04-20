package gent.zeus.tappb.ui;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import gent.zeus.tappb.MoneySubmitFragment;
import gent.zeus.tappb.MoneyTextWatcher;
import gent.zeus.tappb.databinding.FragmentTopUpBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TopUpFragment extends MoneySubmitFragment {

    private FragmentTopUpBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            binding = FragmentTopUpBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
//        binding.topup.setOnClickListener(this);
//        binding.amountInput.addTextChangedListener(new MoneyTextWatcher(binding.amountInput));

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
//        super.onClick(v);
//
//        boolean isValid = true;
//        String amount = binding.amountInput.getText().toString();
//        if (amount.isEmpty()) {
//            binding.amountInput.setError("Invalid amount");
//            isValid = false;
//        } else {
//            double parsed = Double.parseDouble(amount);
//            if (parsed <= 0) {
//                binding.amountInput.setError("Invalid amount");
//                isValid = false;
//            }
//        }
//
//        String dialogMessage = "Top up " + amount + "?";
//        if (isValid) {
//            showDialog("Top up money", dialogMessage);
//        }
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
