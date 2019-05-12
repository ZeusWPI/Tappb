package gent.zeus.tappb.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentTopUpBinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TopUpFragment extends MoneySubmitFragment {

    private FragmentTopUpBinding binding;
    private ClipboardManager clipboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTopUpBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.copyButton.setOnClickListener(this);

        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), this);

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        ClipData clip = ClipData.newPlainText("account number", getString(R.string.bank_account));
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
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
