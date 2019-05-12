package gent.zeus.tappb.entity;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import gent.zeus.tappb.repositories.BarcodeRepository;

public class Stock {

    private Map<Integer, StockProduct> products;

    @SuppressLint("UseSparseArrays")
    public Stock() {
        this.products = new HashMap<>();
    }

    public Stock(Map<Integer, StockProduct> products) {
        this.products = products;
    }

    public StockProduct getProductById(Integer id) {
        return products.get(id);
    }

    public Collection<StockProduct> getProducts() {
        return products.values();
    }

    public void addProduct(StockProduct product) {
        products.put(product.getId(), product);
    }

    public Product getProductByBarcode(String barcode) {
        List<Barcode> barcodes = BarcodeRepository.getInstance().getBarcodes();
        if (barcodes == null) {
            Log.e("PING", "PING");
            return null;
        }

        Optional<Barcode> maybeBarcode = barcodes.stream().filter(b -> b.getBarcode().equals(barcode)).findFirst();

        return maybeBarcode.map(Barcode::getProduct).orElse(null);
    }
}
