package parser.serialization;

import elements.Product;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ProductSerializer implements JsonSerializer<Product> {

    @Override
    public JsonElement serialize(Product product, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", product.getName());
        jsonObject.add("coordinates", jsonSerializationContext.serialize(product.getCoordinates()));
        jsonObject.addProperty("creationDate", product.getCreationDate().toString());
        jsonObject.addProperty("price", product.getPrice());
        jsonObject.addProperty("partNumber", product.getPartNumber());
        jsonObject.addProperty("manufactureCost", product.getManufactureCost());
        jsonObject.addProperty("unitOfMeasure", product.getUnitOfMeasure().toString());
        jsonObject.add("manufacturer", jsonSerializationContext.serialize(product.getManufacturer()));

        return jsonObject;
    }
}
