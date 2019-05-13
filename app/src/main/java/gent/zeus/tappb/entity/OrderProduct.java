package gent.zeus.tappb.entity;

public class OrderProduct extends Product{

    private int count;

    public OrderProduct(Product p, int count) {
        super(p);
        this.count = count;
    }

    public OrderProduct(int id, String name, double price, String imageURL, int count) {
        super(id, name, price, imageURL);
        this.count = count;
    }

    public OrderProduct(int id, String name, double price, int count) {
        super(id, name, price);
        this.count = count;
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
}
