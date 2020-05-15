package parser.serialization;

import elements.Product;
import collection.ProductList;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ProductListSerializer implements JsonSerializer<ProductList> {

    @Override
    public JsonElement serialize(ProductList productList, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();

        for (Product product : productList.getProducts()) {
            jsonArray.add(jsonSerializationContext.serialize(product));
        }

        jsonObject.add("products", jsonArray);
        return jsonObject;
    }
}
