package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class Product extends PanacheEntity {

    public String label;
    public String brand;
    public String model;
    public String condition;
    public double price;

    @Lob
    public String description;

    // To simplify

    public String category;
    public String subcategory;

    public static Product findProductByLabel(String label) {
        return find("label", label).firstResult();
    }

}
