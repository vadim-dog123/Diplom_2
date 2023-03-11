package api.order;

import api.BasicTest;
import api.Order;
import api.model.user.AuthorizationModel;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Получение заказов конкретного пользователя")
public class OrdersFromSpecificUserTest extends BasicTest {
    @Before
    public void before() {
        new Order().create(user.getBody().as(AuthorizationModel.class).getAccessToken(), ingredients);
    }
    @Test
    @DisplayName("Авторизованный пользователь")
    public void authorizedUser() {
        var order = new Order().getOrdersFromSpecificUser(user.getBody().as(AuthorizationModel.class).getAccessToken());
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(200);
        Allure.step("Проверка тела ответа");
        order.then().assertThat().body("orders.ingredients", notNullValue());
        order.then().assertThat().body("orders._id", notNullValue());
        order.then().assertThat().body("orders.status", notNullValue());
        order.then().assertThat().body("orders.number", notNullValue());
        order.then().assertThat().body("orders.createdAt", notNullValue());
        order.then().assertThat().body("orders.updatedAt", notNullValue());
        order.then().assertThat().body("total", notNullValue());
        order.then().assertThat().body("totalToday", notNullValue());
    }
    @Test
    @DisplayName("Авторизованный пользователь")
    public void unauthorizedUser() {
        var order = new Order().getOrdersFromSpecificUser("");
        Allure.step("Проверка кода ответа");
        order.then().assertThat().statusCode(401);
        Allure.step("Проверка тела ответа");
        order.then().assertThat().body("success", equalTo(false));
        order.then().assertThat().body("message", equalTo("You should be authorised"));
    }
}