package parser.deserialization;

import elements.Coordinates;
import elements.Organization;
import elements.Product;
import elements.UnitOfMeasure;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;

public class ProductDeserializer implements JsonDeserializer<Product> {

    @Override
    public Product deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Product product = new Product(jsonObject.get("name").getAsString(),
                (Coordinates) jsonDeserializationContext.deserialize(jsonObject.get("coordinates"), Coordinates.class),
                jsonObject.get("price").getAsDouble(),
                jsonObject.get("partNumber").getAsString(),
                jsonObject.get("manufactureCost").getAsLong(),
                UnitOfMeasure.valueOf(jsonObject.get("unitOfMeasure").getAsString()),
                (Organization) jsonDeserializationContext.deserialize(jsonObject.get("manufacturer"), Organization.class));
                product.setCreationDate(ZonedDateTime.parse(jsonObject.get("creationDate").getAsString()));

        if((jsonObject.get("manufactureCost").getAsLong() != jsonObject.get("manufactureCost").getAsDouble()) ||
                (product.getCoordinates() == null || product.getManufacturer() == null)||
                (product.getPrice() <= 0) ||
                (product.getCoordinates().getY() >= 999) ||
                (product.getManufacturer().getAnnualTurnover() < 0))
            throw new JsonParseException("Некорректные данные в json файле!");

        return product;
    }
}
