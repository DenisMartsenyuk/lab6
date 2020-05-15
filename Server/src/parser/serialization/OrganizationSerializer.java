package parser.serialization;

import elements.Organization;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class OrganizationSerializer implements JsonSerializer<Organization> {

    @Override
    public JsonElement serialize(Organization organization, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", organization.getName());
        jsonObject.addProperty("annualTurnover", organization.getAnnualTurnover());
        jsonObject.addProperty("organizationType", organization.getOrganizationType().toString());

        return jsonObject;
    }
}
