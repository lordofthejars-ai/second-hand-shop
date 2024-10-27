package org.acme.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Optional;

@Entity
public class Category extends PanacheEntity {

    public String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Subcategory> subcategories;

    public Category() {
    }

    public Category(final String name, final List<Subcategory> subcategories) {
        this.name = name;
        this.subcategories = subcategories;
    }

    public static List<Category> findAllByOrderByNameAsc() {
        return listAll(Sort.ascending("name"));
    }

    public static Optional<Category> findByName(String name) {
        return Category.find("name", name).firstResultOptional();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", subcategories=" + subcategories + "]";
    }

}
