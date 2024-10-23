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

    }

    private void cleanupDatabase() {
        Subcategory.deleteAll();
        Category.deleteAll();
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
