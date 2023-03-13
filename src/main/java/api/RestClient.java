package api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestClient {
    private final String BASE_URL = "https://stellarburgers.nomoreparties.site";

    protected RequestSpecification baseSpec() {
        return new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(BASE_URL).build();
    }
}
