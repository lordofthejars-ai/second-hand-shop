package org.acme.ai;

import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;

import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.OutputUnit;
import io.vertx.json.schema.Validator;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ImageProcessingJsonOutputGuardrail implements OutputGuardrail {

    @Named("item_description")
    Validator schemaValidator;

    @Inject
    Logger logger;

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {

        logger.infof("Executing Output Guard Level "
            + "of image processing for validating %s", responseFromLLM.text());

        final OutputUnit validated = schemaValidator.validate(
            new JsonObject(responseFromLLM.text())
        );

        if (!validated.getValid()) {
            return reprompt("Invalid JSON",
                "Make sure you return a valid JSON object following "
                    + "the specified format");
        }

        return success();
    }
}
