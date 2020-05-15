package parser.deserialization;

import elements.Organization;
import elements.OrganizationType;
import com.google.gson.*;

import java.lang.reflect.Type;

public class OrganizationDeserializer implements JsonDeserializer<Organization> {

    @Override
    public Organization deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.get("annualTurnover").getAsLong() != jsonObject.get("annualTurnover").getAsDouble())
            throw new JsonParseException("Некорректные данные в json файле!");
        return new Organization(jsonObject.get("name").getAsString(),
                jsonObject.get("annualTurnover").getAsLong(),
                OrganizationType.valueOf(jsonObject.get("organizationType").getAsString()));
    }
}
