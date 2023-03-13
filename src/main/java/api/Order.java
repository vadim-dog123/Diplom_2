package api;

import api.model.oder.CreateOrderRequestModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order extends RestClient {
    private final String ORDER_PATH = "/api/orders";
    private final String GET_INGREDIENTS = "/api/ingredients";

    @Step("Создание заказа")
    public Response create(String accessToken, String[] ingredients) {
        return given().spec(baseSpec()).auth().oauth2(accessToken).body(new CreateOrderRequestModel(ingredients)).when().post(ORDER_PATH);
    }

    @Step("Получение данных об ингредиентах")
    public Response getIngredients() {
        return given().spec(baseSpec()).when().get(GET_INGREDIENTS);
    }

    @Step("Получить заказы конкретного пользователя")
    public Response getOrdersFromSpecificUser(String accessToken) {
        return given().spec(baseSpec()).auth().oauth2(accessToken).when().get(ORDER_PATH);
    }
}
