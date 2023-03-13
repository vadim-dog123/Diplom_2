package api;

import api.model.user.AuthorizationRequestModel;
import api.model.user.UserCreationModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User extends RestClient {
    private final String USER_CREATION_PATH = "/api/auth/register";
    private final String USER_INFORMATION_PATH = "/api/auth/user";
    private final String USER_AUTHORIZATION_PATH = "/api/auth/login";

    @Step("Создание пользователся с данными: email={email}, password={password}, Name={name}")
    public Response create(String email, String password, String name) {
        return given().spec(baseSpec()).body(new UserCreationModel(email, password, name)).when().post(USER_CREATION_PATH);
    }

    @Step("Получение данных о пользователе")
    public Response getUserInformation(String accessToken) {
        return given().spec(baseSpec()).auth().oauth2(accessToken).when().get(USER_INFORMATION_PATH);
    }

    @Step("Oбновление данных о пользователе: email={email}, password={password}, Name={name}")
    public Response patchUserInformation(String accessToken, String email, String name, String password) {
        return given().spec(baseSpec()).header("Content-type", "application/json").auth().oauth2(accessToken).and().body(new UserCreationModel(email, password, name)).when().patch(USER_INFORMATION_PATH);
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given().spec(baseSpec()).header("Content-type", "application/json").auth().oauth2(accessToken).when().delete(USER_INFORMATION_PATH);
    }

    @Step("Авторизация пользователя: email={email}, password={password}")
    public Response authorizationUser(String email, String password) {
        return given().spec(baseSpec()).body(new AuthorizationRequestModel(email, password)).when().post(USER_AUTHORIZATION_PATH);
    }

}
