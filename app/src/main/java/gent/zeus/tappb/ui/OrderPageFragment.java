package gent.zeus.tappb.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import gent.zeus.tappb.databinding.FragmentOrderpageBinding;
import gent.zeus.tappb.entity.Order;
import gent.zeus.tappb.entity.Product;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderPageFragment extends Fragment {
    private FirebaseVisionBarcodeDetectorOptions firebaseOptions;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;

    public OrderPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseOptions = new FirebaseVisionBarcodeDetectorOptions.Builder()
                .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                .build();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentOrderpageBinding binding = FragmentOrderpageBinding.inflate(inflater, container, false);
        binding.setHandler(this);
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void takePicture(View ignored) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored2) {}

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "gent.zeus.tappb.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            FirebaseVisionImage image;
            try {
                Log.d("OrderPageFragment", "Getting picture from " + currentPhotoPath);
                image = FirebaseVisionImage.fromFilePath(getContext(), Uri.fromFile(new File(currentPhotoPath)));
                FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                        .getVisionBarcodeDetector();
                detector.detectInImage(image)
                        .addOnSuccessListener(barcodes -> {
                            Order newOrder = new Order();
                            Toast.makeText(getContext(), "Found " + barcodes.size() + " barcodes", Toast.LENGTH_SHORT).show();
                            Log.d("OrderPageFragment", "Found barcodes");
                            for (FirebaseVisionBarcode barcode : barcodes) {
                                Log.d("OrderPageFragment", barcode.getDisplayValue());
                                newOrder.addProduct(Product.fromBarcode(barcode.getDisplayValue()));
                            }
                            //TODO do something with `newOrder`
                        })
                        .addOnFailureListener(e -> {
                            Log.e("OrderPageFragment", "detectInImage failed", e);
                            Toast.makeText(getContext(), "An error occured finding barcodes...", Toast.LENGTH_SHORT).show();
                        });
            } catch (IOException e) {
                Log.e("OrderPageFragment", "An error occured finding barcodes", e);
            }
        }
    }

    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
