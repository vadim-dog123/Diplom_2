package api.order;

import api.BasicTest;
import api.Order;
import api.model.user.AuthorizationModel;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Создание заказа")
public class CreateOrderTest extends BasicTest {
    @Test
    @DisplayName("C авторизацией и ингредиентами")
    public void createOrderTest() {
        var accessToken = user.getBody().as(AuthorizationModel.class).getAccessToken();
        var order = new Order().create(accessToken, ingredients);
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(SC_OK);
        Allure.step("Проверка тела ответа");
        order.then().assertThat().body("name", equalTo("Альфа-сахаридный флюоресцентный бургер"));
        order.then().assertThat().body("order.number", notNullValue());
        order.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Без авторизации")
    public void createOrderWithoutAuthorizationTest() {
        var order = new Order().create("", ingredients);
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(SC_OK);
        Allure.step("Проверка тела ответа");
        order.then().assertThat().body("name", equalTo("Альфа-сахаридный флюоресцентный бургер"));
        order.then().assertThat().body("order.number", notNullValue());
        order.then().assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Без ингредиентов")
    public void creatingOrderWithoutIngredients() {
        var accessToken = user.getBody().as(AuthorizationModel.class).getAccessToken();
        var order = new Order().create(accessToken, null);
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(SC_BAD_REQUEST);
        Allure.step("Проверка тела ответа");
        order.then().assertThat().body("success", equalTo(false));
        order.then().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("С неверным хешем ингредиентов")
    public void creatingOrderWithInvalidIngredientHash() {
        var accessToken = user.getBody().as(AuthorizationModel.class).getAccessToken();
        var order = new Order().create(accessToken, new String[]{"91c0c5a7"});
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}
