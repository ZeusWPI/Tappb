package gent.zeus.tappb.entity;

public class Barcode {
    private String barcode;
    private Integer productId;

    public Barcode(String barcode, Integer productId) {
        this.barcode = barcode;
        this.productId = productId;
    }

    public String getBarcode() {
        return barcode;
    }

    public Integer getProductId() {
        return productId;
    }
}
