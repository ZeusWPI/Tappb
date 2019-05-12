package gent.zeus.tappb.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.api.APIException;
import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Barcode;

public class BarcodeRepository {
    private static BarcodeRepository instance;
    private List<Barcode> barcodes;
    private TapAPI api = new TapAPI();

    private BarcodeRepository(){
        LiveData<List<Barcode>> apiCodes = api.getBarcodes();
        apiCodes.observeForever(codes -> {
            barcodes = codes;
            Log.e("REE", "REE" + codes.size());
        });
    }

    public static BarcodeRepository getInstance() {
        if (instance == null) {
            instance = new BarcodeRepository();
        }
        return instance;
    }

    public void reloadBarcodes() {
        try {
            api.fetchBarcodes();
        } catch (APIException ex) {
            barcodes = new ArrayList<>();
            Log.e("BarcodeRequest", "failed to load barcodes", ex);
        }
    }

    public List<Barcode> getBarcodes() {
        if (barcodes == null) {
            reloadBarcodes();
        }
        return barcodes;
    }
}
