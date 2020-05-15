package parser;

import elements.Coordinates;
import elements.Organization;
import elements.Product;
import collection.ProductList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import parser.deserialization.CoordinatesDeserializer;
import parser.deserialization.OrganizationDeserializer;
import parser.deserialization.ProductDeserializer;
import parser.deserialization.ProductListDeserializer;
import parser.serialization.CoordinatesSerializer;
import parser.serialization.OrganizationSerializer;
import parser.serialization.ProductListSerializer;
import parser.serialization.ProductSerializer;

public class Parser {

    public static String serialize(ProductList productList) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(ProductList.class, new ProductListSerializer())
                .registerTypeAdapter(Product.class, new ProductSerializer())
                .registerTypeAdapter(Organization.class, new OrganizationSerializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesSerializer())
                .create();
        return gson.toJson(productList);
    }

    public static ProductList deserialize(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ProductList.class, new ProductListDeserializer())
                .registerTypeAdapter(Product.class, new ProductDeserializer())
                .registerTypeAdapter(Organization.class, new OrganizationDeserializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesDeserializer())
                .create();
        return gson.fromJson(json, ProductList.class);
    }
}
