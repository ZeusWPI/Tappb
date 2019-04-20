package gent.zeus.tappb.entity;

public class Product {

    private int id;

    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product other = (Product) o;
        return other.getName().equals(this.getName()) && other.getId() == this.getId();
    }

    // To make sure items of the same type get combined in a HashMap
    @Override
    public final int hashCode() {
        return (name + id).hashCode();
    }
}
