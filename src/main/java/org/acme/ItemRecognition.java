package org.acme;

import dev.langchain4j.data.image.Image;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.ai.AiImageProcessingService;
import org.acme.ai.AiItemCategorizationService;
import org.acme.model.Product;

import java.util.Base64;

@ApplicationScoped
public class ItemRecognition {

    @Inject
    AiImageProcessingService imageProcessingService;

    @Inject
    AiItemCategorizationService categorizationService;

    public Product detectAndSaveProduct(byte[] image, String mime) {
        String base64Image = Base64.getEncoder().encodeToString(image);
        Image encodedImage = Image.builder().base64Data(base64Image).mimeType(mime).build();

        Item item = imageProcessingService.extractInfo(encodedImage);
        ItemCategory itemCategory = categorizationService.categorize(item);

        return insertProduct(item, itemCategory);

    }

    @Transactional
    public Product insertProduct(Item item, ItemCategory itemCategory) {
        Product product = new Product();
        product.description = item.description;
        product.brand = item.brand;
        product.label = item.label;
        product.condition = item.condition;
        product.model = item.model;
        product.price = item.price;

        product.category = itemCategory.category;
        product.subcategory = itemCategory.subcategory;

        product.persist();

        return product;
    }

}
