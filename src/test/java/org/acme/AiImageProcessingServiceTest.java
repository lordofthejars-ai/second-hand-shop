package org.acme;

import dev.langchain4j.data.image.Image;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import org.acme.ai.AiImageProcessingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class AiImageProcessingServiceTest {

    @Inject
    AiImageProcessingService service;

    @Test
    public void shouldDetectGameBoy() throws IOException {
        String base64Image;
        try (InputStream imageStream = AiImageProcessingService.class.getResourceAsStream("/gb.jpg")) {
            base64Image = Base64.getEncoder().encodeToString(imageStream.readAllBytes());
        }

        Item productInformation = service.extractInfo(Image.builder().base64Data(base64Image).mimeType("image/jpg").build());
        assertThat(productInformation.label).containsIgnoringCase("Nintendo");
        assertThat(productInformation.brand).isEqualToIgnoringCase("Nintendo");
        assertThat(productInformation.model).containsIgnoringCase("Game Boy");

    }

}
