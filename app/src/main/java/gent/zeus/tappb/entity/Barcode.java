package gent.zeus.tappb.entity;

public class Barcode {
    private String barcode;
    private Product product;

    public Barcode(String barcode, Product product) {
        this.barcode = barcode;
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public String getBarcode() {
        return barcode;
    }
}
