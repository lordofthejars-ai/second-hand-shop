package org.acme.json;

import io.vertx.core.json.JsonObject;
import io.vertx.json.schema.Draft;
import io.vertx.json.schema.JsonSchema;
import io.vertx.json.schema.JsonSchemaOptions;
import io.vertx.json.schema.Validator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class JsonSchemaProducer {

    private static final String itemCategorizationSchema = """
        {
          "$schema": "http://json-schema.org/draft-04/schema#",
          "type": "object",
          "properties": {
            "category": {
              "type": "string"
            },
            "subcategory": {
              "type": "string"
            }
          },
          "required": [
            "category",
            "subcategory"
          ]
        }
        """;

    private static final String itemDescriptionSchema = """
        {
          "$schema": "http://json-schema.org/draft-04/schema#",
          "type": "object",
          "properties": {
            "label": {
              "type": "string"
            },
            "brand": {
              "type": "string"
            },
            "model": {
              "type": "string"
            },
            "condition": {
              "type": "string"
            },
            "price": {
              "type": "number"
            },
            "description": {
              "type": "string"
            }
          },
          "required": [
            "label",
            "brand",
            "model",
            "condition",
            "price",
            "description"
          ]
        }
        """;

    @Produces
    JsonSchemaOptions createJsonSchemaConfiguration() {
        return new JsonSchemaOptions()
            .setDraft(Draft.DRAFT4)
            .setBaseUri("http://localhost");
    }

    @Produces
    @Named("item_categorization")
    Validator createJsonSchemaForItemCategorization(JsonSchemaOptions jsonSchemaOptions) {
        JsonObject schema = new JsonObject(itemCategorizationSchema);
        JsonSchema jsonSchema = JsonSchema.of(schema);

        return Validator.create(jsonSchema, jsonSchemaOptions);
    }

    @Produces
    @Named("item_description")
    Validator createJsonSchemaForItemDescription(JsonSchemaOptions jsonSchemaOptions) {

        JsonObject schema = new JsonObject(itemDescriptionSchema);
        JsonSchema jsonSchema = JsonSchema.of(schema);

        return Validator.create(jsonSchema, jsonSchemaOptions);
    }

}
