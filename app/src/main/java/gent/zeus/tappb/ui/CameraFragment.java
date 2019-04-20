package gent.zeus.tappb.ui;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import gent.zeus.tappb.R;
import gent.zeus.tappb.databinding.FragmentCameraBinding;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.entity.ProductList;
import gent.zeus.tappb.viewmodel.OrderViewModel;

public class CameraFragment extends Fragment {


    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private OrderViewModel viewModel;
    private NavController navController;

    private FirebaseVisionBarcodeDetectorOptions firebaseOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseOptions = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCameraBinding binding = FragmentCameraBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
        navController=NavHostFragment.findNavController(this);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        launchCamera();
    }

    private void launchCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "gent.zeus.tappb.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // Make sure it's a temp file
        image.deleteOnExit();

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            FirebaseVisionImage image;
            try {
                viewModel.setScanningState(OrderViewModel.ScanningState.SCANNING);
                Log.d("OrderPageFragment", "Getting picture from " + currentPhotoPath);
                image = FirebaseVisionImage.fromFilePath(getContext(), Uri.fromFile(new File(currentPhotoPath)));
                FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                        .getVisionBarcodeDetector(firebaseOptions);
                detector.detectInImage(image)
                        .addOnSuccessListener(barcodes -> {
                            Log.i("OrderPageFragment", "Found " + barcodes.size() + " barcodes");
                            if (barcodes.isEmpty()) {
                                viewModel.setScanningState(OrderViewModel.ScanningState.EMPTY);
                            } else {
                                for (FirebaseVisionBarcode barcode : barcodes) {
                                    Log.i("OrderPageFragment", barcode.getDisplayValue());
                                    Product product = ProductList.getInstance().getProductByBarcode(barcode.getDisplayValue());
                                    if(product != null) {
                                        viewModel.addProduct(product);
                                    }
                                }
                                viewModel.invalidateOrderList();
                                viewModel.setScanningState(OrderViewModel.ScanningState.NOT_SCANNING);
                                navController.navigate(R.id.nav_order);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("OrderPageFragment", "detectInImage failed", e);
                            viewModel.setScanningState(OrderViewModel.ScanningState.ERROR);
                            navController.popBackStack();
                        });
            } catch (IOException e) {
                Log.e("OrderPageFragment", "An error occured finding barcodes", e);
                viewModel.setScanningState(OrderViewModel.ScanningState.ERROR);
            }
        }
        navController.popBackStack();

    }
}
