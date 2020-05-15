package parser.deserialization;

import collection.ProductList;
import com.google.gson.*;
import elements.Product;

import java.lang.reflect.Type;

public class ProductListDeserializer implements JsonDeserializer<ProductList> {

    @Override
    public ProductList deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        ProductList result = new ProductList();
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray products = jsonObject.getAsJsonArray("products");

        for(JsonElement product : products) {
            result.add((Product) jsonDeserializationContext.deserialize(product, Product.class));
        }
        return result;
    }
}
