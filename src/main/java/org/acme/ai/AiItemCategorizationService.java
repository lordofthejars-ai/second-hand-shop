package org.acme.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrails;
import org.acme.Item;
import org.acme.ItemCategory;

@RegisterAiService
public interface AiItemCategorizationService {

    @SystemMessage("""
        You are an expert in the field of e-commerce item categorization.
        Using tools at your disposal to get the categories and subcategories available , you return a json with item category, and the subcategory of the item.

        You received as input the label of the item, its brand, its model and its description.
        Both the brand and the model can be `unknown`.
        With all the information categorize the item with the given categories and subcategories.

        The returned json should have the following structure:
        {
            "category": "Item category",
            "subcategory": "Item subcategory"
        }

        Return only the JSON string. Do not include any other information.
    """)
    @UserMessage("""
         Item label: {item.label}
         Brand: {item.brand}
         Model: {item.model}

         Description:
         {item.description}
    """)
    @ToolBox(CategorizingTools.class)
    @OutputGuardrails(ItemCategorizationJsonOutputGuardrail.class)
    ItemCategory categorize(Item item);

}
