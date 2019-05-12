package gent.zeus.tappb.ui;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentAccountBinding;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.viewmodel.AccountViewModel;

public class AccountFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;

    private AccountViewModel viewModel;
    private DecimalFormat formatter = new DecimalFormat("€ #0.00;€ -#0.00");


    public AccountFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAccountBinding binding = FragmentAccountBinding.inflate(inflater, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        viewModel.init();
        viewModel.getProfileURL().observe(this, (url) -> {
            Picasso.get().load(url).into(binding.profilePicture);
        });
        viewModel.getUserName().observe(this, binding.username::setText);
        viewModel.getFavoriteItemName().observe(this, binding.favoriteItem::setText);

        binding.setFormatter(formatter);
        binding.setHandler(this);
        binding.setViewModel(viewModel);

        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    public boolean setProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.profile_picture_camera_gallery)
                .setItems(R.array.camera_gallery, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            openCamera();
                            break;
                        case 1:
                            openGallery();
                            break;
                    }
                });
        builder.setCancelable(true);
        builder.create().show();
        return true;
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        galleryIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (galleryIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(galleryIntent, REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap icon;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            icon = (Bitmap) data.getExtras().get("data");
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            ContentProviderClient contentProviderClient = getContext().getContentResolver().acquireContentProviderClient(data.getData());
            try {
                icon = BitmapFactory.decodeFileDescriptor(contentProviderClient.openAssetFile(data.getData(), "r").getFileDescriptor());
            } catch (Exception ex) {
                Log.e("AccountFragment.onActivityResult", "Could not read file", ex);
                return;
            }
        } else {
            return;
        }
        int cutoutSize = Math.min(icon.getWidth(), icon.getHeight());
        Bitmap cutout = Bitmap.createBitmap(icon, (icon.getWidth() - cutoutSize) / 2, (icon.getHeight() - cutoutSize) / 2, cutoutSize, cutoutSize);

        int finalSize = Math.min(cutoutSize, getResources().getInteger(R.integer.profile_picture_size));
        Bitmap finalImage = Bitmap.createScaledBitmap(cutout, finalSize, finalSize, false);
        viewModel.setProfilePicture(finalImage);
        Log.i("REQUEST", finalImage.getWidth() + ", " + finalImage.getHeight());
    }

    public boolean setFavoriteItem() {
        NavController navController = Navigation.findNavController(getActivity().findViewById(R.id.nav_host_fragment));
        navController.navigate(R.id.action_nav_account_to_nav_favorite_item);
        return true;
    }
}
