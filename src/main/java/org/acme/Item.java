package org.acme;

import java.util.StringJoiner;

public class Item {

    public String label;
    public String brand;
    public String model;
    public String condition;
    public double price;
    public String description;

    @Override
    public String toString() {
        return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
            .add("label='" + label + "'")
            .add("brand='" + brand + "'")
            .add("model='" + model + "'")
            .add("condition='" + condition + "'")
            .add("price=" + price)
            .add("description='" + description + "'")
            .toString();
    }
}
