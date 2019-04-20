package gent.zeus.tappb.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gent.zeus.tappb.api.APIException;
import gent.zeus.tappb.api.TapAPI;

public class BarcodeList {
    private static BarcodeList instance;
    private List<Barcode> barcodes;

    private BarcodeList(){}

    public static BarcodeList getInstance() {
        if (instance == null) {
            instance = new BarcodeList();
        }
        return instance;
    }

    public void reloadBarcodes() {
        try {

            barcodes = TapAPI.getBarcodes();
        } catch (APIException ex) {
            barcodes = new ArrayList<>();
            Log.e("BarcodeRequest", "failed to load barcodes");
        }
    }

    public List<Barcode> getBarcodes() {
        if (barcodes == null) {
            reloadBarcodes();
        }
        return barcodes;
    }
}
