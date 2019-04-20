package gent.zeus.tappb.entity;

import java.util.List;

import gent.zeus.tappb.api.TapAPI;

public class BarcodeList {
    private static BarcodeList instance;
    private List<Barcode> barcodes;

    private BarcodeList(){}

    public static BarcodeList getInstance() {
        return instance;
    }

    public void reloadBarcodes() {
        barcodes = TapAPI.getBarcodes();
    }

    public List<Barcode> getBarcodes() {
        if (barcodes == null) {
            reloadBarcodes();
        }
        return barcodes;
    }
}
