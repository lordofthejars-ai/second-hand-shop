package org.acme.ai;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.acme.model.Category;
import org.acme.model.Subcategory;
import org.jboss.logging.Logger;

@Singleton
public class CategorizingTools {

    @Inject
    Logger logger;

    @Tool("Give the list of items' categories.")
    List<String> categories() {

        logger.info("Finding Categories");

        return Category
            .findAllByOrderByNameAsc()
            .stream()
            .map(Category::getName)
            .toList();
    }

    @Tool("Give the list of subcategories of a category.")
    List<String> subcategories(final String categoryName) {

        logger.infof("Finding SubCategories for category %s", categoryName);

        final Optional<Category> optionalCategory = Category.findByName(categoryName);

        return optionalCategory.map(category -> category
            .subcategories
            .stream()
            .map(Subcategory::getName)
            .toList())
            .orElseGet(() -> List.of("Unknown category"));
    }


}
