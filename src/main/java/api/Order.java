package api;

import api.model.oder.CreateOrderRequestModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order extends RestClient {
    private final String ORDER_PATH = "/api/orders";

    @Step("Создание заказа")
    public Response create(String[] ingredients) {
        return given()
                .spec(baseSpec())
                .body(new CreateOrderRequestModel(ingredients))
                .when()
                .post(ORDER_PATH);
    }
}
