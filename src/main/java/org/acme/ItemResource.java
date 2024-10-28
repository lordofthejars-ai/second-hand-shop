package org.acme;


import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.ai.AiImageProcessingService;
import org.acme.ai.AiItemCategorizationService;
import org.acme.model.Category;
import org.acme.model.Product;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class ItemResource {

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance form(Product product, List<Category> categories);
        public static native TemplateInstance list(List<Product> products);
    }

    @GET
    @Path("/list.html")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public TemplateInstance list() {
        List<Product> products = Product.listAll();
        return Templates.list(products);
    }

    @GET
    @Path("/form.html")
    @Produces(MediaType.TEXT_HTML)
    @Blocking
    @Transactional
    public TemplateInstance form(@RestQuery long productId) {
        Product product = Product.findById(productId);
        return Templates.form(product, Category.findAllByOrderByNameAsc());
    }

    @PUT
    @Path("/update")
    @Transactional
    public Response updateItem(ItemCategoryDto itemCategoryDto) {
        Product p = Product.findProductByLabel(itemCategoryDto.label);

        p.label = itemCategoryDto.label;
        p.subcategory = itemCategoryDto.subcategory;
        p.category = itemCategoryDto.category;
        p.price = itemCategoryDto.price;
        p.description = itemCategoryDto.description;
        p.brand = itemCategoryDto.brand;
        p.model = itemCategoryDto.model;
        p.condition = itemCategoryDto.condition;

        return Response.ok().build();

    }

    @Inject
    ItemRecognition itemRecognition;

    @POST
    @Path("uploadItem")
    public Response uploadItem(@RestForm("image") FileUpload file) throws IOException {

        Product product = itemRecognition.detectAndSaveProduct(
                Files.readAllBytes(file.uploadedFile()),
                file.contentType()
        );

        final Map<String, Object> returnObject = new HashMap<>();
        returnObject.put("id", product.id);
        JsonObject jsonObject = new JsonObject(returnObject);

        return Response.ok().entity(jsonObject).build();
    }
}
