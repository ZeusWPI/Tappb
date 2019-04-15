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
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import gent.zeus.tappb.R;
import gent.zeus.tappb.adapters.OrderAdapter;
import gent.zeus.tappb.databinding.FragmentOrderpageBinding;
import gent.zeus.tappb.entity.Order;
import gent.zeus.tappb.entity.OrderProduct;
import gent.zeus.tappb.entity.Product;
import gent.zeus.tappb.viewmodel.OrderViewModel;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class OrderPageFragment extends Fragment implements OrderAdapter.OrderListener {
    private FirebaseVisionBarcodeDetectorOptions firebaseOptions;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private OrderViewModel viewModel;
    private OrderAdapter adapter;


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

        viewModel = ViewModelProviders.of(getActivity()).get(OrderViewModel.class);
        viewModel.init();

        adapter = new OrderAdapter(this);
        binding.productList.setAdapter(adapter);
        binding.productList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        viewModel.getOrders().observe(this, adapter::setProducts);

        viewModel.getScanningState().observe(this, scanningState -> {
            Button button = this.getActivity().findViewById(R.id.button);
            if (button == null) {
                return;
            }
            switch (scanningState) {
                case SCANNING:
                    button.setText(R.string.scanning);
                    button.setEnabled(false);
                    break;
                case ERROR:
                    button.setText(R.string.scanning_error);
                    button.setEnabled(true);
                    break;
                case NOT_SCANNING:
                    button.setText(R.string.scan_barcode);
                    button.setEnabled(true);
                    break;
                case EMPTY:
                    button.setText(R.string.scanning_empty);
                    button.setEnabled(true);
                    break;
            }
        });

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
                                Order newOrder = new Order();
                                for (FirebaseVisionBarcode barcode : barcodes) {
                                    Log.i("OrderPageFragment", barcode.getDisplayValue());
                                    newOrder.addProduct(Product.fromBarcode(barcode.getDisplayValue()));
                                }
                                viewModel.addOrder(newOrder);
                                viewModel.setScanningState(OrderViewModel.ScanningState.NOT_SCANNING);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("OrderPageFragment", "detectInImage failed", e);
                            viewModel.setScanningState(OrderViewModel.ScanningState.ERROR);
                        });
            } catch (IOException e) {
                Log.e("OrderPageFragment", "An error occured finding barcodes", e);
                viewModel.setScanningState(OrderViewModel.ScanningState.ERROR);
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

        // Make sure it's a temp file
        image.deleteOnExit();

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onClick(OrderProduct orderProduct) {
        Toast.makeText(getContext(),orderProduct.getCount() + " " + orderProduct.getProduct().getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable newValue, OrderProduct orderProduct) {
        if (newValue.length() == 0) {
            return;
        }
        viewModel.updateCount(orderProduct.getProduct(), Integer.parseInt(newValue.toString()));
    }
}
