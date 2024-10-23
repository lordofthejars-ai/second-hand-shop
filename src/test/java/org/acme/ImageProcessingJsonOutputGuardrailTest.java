package org.acme;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.GuardrailResult;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.ai.ImageProcessingJsonOutputGuardrail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ImageProcessingJsonOutputGuardrailTest {

    @Inject
    ImageProcessingJsonOutputGuardrail imageProcessingJsonOutputGuardrail;


    @Test
    public void shouldValidateAValidSchema() {
        AiMessage message = new AiMessage("""
            {
                    "label": "Item label",
                    "brand": "Item brand",
                    "model": "Item model",
                    "condition": "Item condition",
                    "price": 0.00,
                    "description": "Item description"
                }
            """);
        final OutputGuardrailResult outputGuardrailResult = imageProcessingJsonOutputGuardrail.validate(message);
        assertThat(outputGuardrailResult.result()).isEqualTo(GuardrailResult.Result.SUCCESS);
    }

    @Test
    public void shouldValidateAnInValidSchema() {
        AiMessage message = new AiMessage("""
            {
                    "labels": "Item label",
                    "brand": "Item brand",
                    "model": "Item model",
                    "condition": "Item condition",
                    "price": 0.00,
                    "description": "Item description"
                }
            """);
        final OutputGuardrailResult outputGuardrailResult = imageProcessingJsonOutputGuardrail.validate(message);
        assertThat(outputGuardrailResult.result()).isEqualTo(GuardrailResult.Result.FATAL);
    }

}
