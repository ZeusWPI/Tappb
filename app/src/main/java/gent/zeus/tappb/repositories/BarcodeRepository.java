package gent.zeus.tappb.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gent.zeus.tappb.api.APIException;
import gent.zeus.tappb.api.TapAPI;
import gent.zeus.tappb.entity.Barcode;

public class BarcodeRepository {
    private static BarcodeRepository instance;
    private List<Barcode> barcodes;
    private Map<String, Barcode> codeMap = new HashMap<>();
    private MutableLiveData<String> codeRequest = new MutableLiveData<>();
    private LiveData<Barcode> requestedCode;
    private MutableLiveData<Barcode> backingCode = new MutableLiveData<>();
    private TapAPI api;

    private BarcodeRepository() {
        this.api = new TapAPI();
        LiveData<Map<String, Barcode>> apiCodes = api.getBarcodes();
        apiCodes.observeForever(codes -> {
            codeMap = codes;
        });
        requestedCode = Transformations.switchMap(codeRequest, (code) -> {
            backingCode.postValue(codeMap.get(code));
            return backingCode;
        });
    }

    public void requestBarcodeByCode(String code) {
        codeRequest.postValue(code);
    }

    public LiveData<Barcode> getRequestedBarcode() {
        return requestedCode;
    }

    public static BarcodeRepository getInstance() {
        if (instance == null) {
            instance = new BarcodeRepository();
        }
        return instance;
    }

    public void fetchAll() {
        api.fetchBarcodes();
    }

    public void reloadBarcodes() {
        try {
            api.fetchBarcodes();
        } catch (APIException ex) {
            barcodes = new ArrayList<>();
            Log.e("BarcodeRequest", "failed to load barcodes", ex);
        }
    }

}
