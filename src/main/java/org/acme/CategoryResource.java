package org.acme;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.acme.model.Category;
import org.acme.model.Product;
import org.acme.model.Subcategory;
import org.jboss.resteasy.reactive.RestPath;

@ApplicationScoped
@Path("/categories")
public class CategoryResource {

    @Inject
    ObjectMapper mapper;

    @Startup
    @Transactional
    public void populateDatabase() throws IOException {
        this.cleanupDatabase();
        this.insertData();
    }

    private void insertData() throws IOException {

        List<Category> categories = new ArrayList<>();
        try (InputStream categoriesStream = CategoryResource.class.getResourceAsStream("/categories.json")) {
            categories.addAll(mapper.readValue(categoriesStream, new TypeReference<List<Category>>() {}));
        }

        categories.forEach(c -> {
            c.subcategories.forEach(sc -> {
                sc.category = c;
            });
            c.persist();
        });

        Product item = new Product();
        item.label = "Nintendo Game Boy Handheld Console";
        item.brand = "Nintendo";
        item.model = "Game Boy";
        item.description = """
            This is a used Nintendo Game Boy handheld console in classic gray. 
            The device features a dot matrix screen with stereo sound and is powered by 4 AA batteries. 
            It has a compact design for portability, complete with a directional pad and action buttons for gameplay. 
            Please note that this item does not come with original packaging or accessories. 
            The console may show minor signs of wear but functions perfectly, providing a nostalgic gaming experience.
        """;
        item.price = 75.0;
        item.condition = "used";
        item.category = "Video Games";
        item.subcategory = "Consoles";

        item.persist();

        System.out.println("Default Product: " + item.id);

    }

    private void cleanupDatabase() {
        Subcategory.deleteAll();
        Category.deleteAll();
        Product.deleteAll();
    }

    @GET
    public List<Category> allCategories() {
        return Category.listAll();
    }

    @GET
    @Path("/{categoryId}/subcategories")
    public List<Subcategory> getSubcategories(@RestPath Long categoryId) {
        return Subcategory.findByCategoryId(categoryId);
    }

}
