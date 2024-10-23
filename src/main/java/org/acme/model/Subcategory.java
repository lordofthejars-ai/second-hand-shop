package org.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class Subcategory extends PanacheEntity  {

    public String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    public Category category;

    public Subcategory() {
    }

    public Subcategory(final String name) {
        this.name = name;
    }

    public static List<Subcategory> findByCategoryId(Long id) {
        Category category = new Category();
        category.id = id;
        return Subcategory.list("category", category);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subcategory [id=" + id + ", name=" + name + "]";
    }

}
