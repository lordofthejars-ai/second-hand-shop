package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.ai.AiItemCategorizationService;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class AiCategorizationServiceTest {

    @Inject
    AiItemCategorizationService categorizationService;

    @Test
    public void shouldCategorizeItem() {
        Item item = new Item();
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

        final ItemCategory itemCategory = categorizationService.categorize(item);
        assertThat(itemCategory.category).isEqualTo("Video Games");
        assertThat(itemCategory.subcategory).isEqualTo("Consoles");
    }

}
