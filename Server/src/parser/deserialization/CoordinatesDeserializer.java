package parser.deserialization;

import elements.Coordinates;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {

    @Override
    public Coordinates deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if(jsonObject.get("y").getAsInt() != jsonObject.get("y").getAsDouble())
            throw new JsonParseException("Некорректные данные в json файле!");

        return new Coordinates(jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsInt());
    }
}
