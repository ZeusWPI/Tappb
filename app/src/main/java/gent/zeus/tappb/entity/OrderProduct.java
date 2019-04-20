package gent.zeus.tappb.entity;

public class OrderProduct {

    private final Product product;
    private int count;

    public OrderProduct(Product p, int count) {
        this.product = p;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void addCount(int diff) {
        this.count += diff;
    }
    public void setCount(int newCount) {
        this.count = newCount;
    }

    public double getPrice() {
        return product.getPrice() * count;
    }
}
