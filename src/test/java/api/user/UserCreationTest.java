package api.user;

import api.User;
import api.model.user.AuthorizationModel;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@DisplayName("Создание пользователя")
public class UserCreationTest extends GeneraActions{
 /*   int random = new Random().nextInt(10000000);
    private Response user;
    private String mailUser;
    private String passwordUser;
    private String nameUser;

    @Before
    public void beforeTest() {
        mailUser = String.format("mail%d@fvfgv.rtyy", random);
        passwordUser = String.format("pas%d", random);
        nameUser = String.format("name%d", random);
        user = new User().create(mailUser, passwordUser, nameUser);
    }

    @After
    public void afterEachTest() {
        new User().deleteUser(user.getBody().as(AuthorizationModel.class).getAccessToken());
    }*/

    @Test
    @DisplayName("Проверка создания уникального пользователя")
    public void createUniqueUserTest() {
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(SC_OK);
        Allure.step("Проверка тела ответа");
        var uiqueUser = user.getBody().as(AuthorizationModel.class);
        assertEquals(uiqueUser.getUser().getEmail(), mailUser);
        assertEquals(uiqueUser.getUser().getName(), nameUser);
        assertFalse(uiqueUser.getRefreshToken().isBlank());
        assertFalse(uiqueUser.getAccessToken().isBlank());
        assertTrue(uiqueUser.getSuccess());
    }
    @Test
    @DisplayName("Проверка создания пользователя, который уже зарегистрирован")
    public void ReCreatingUserTest() {
        var user = new User().create(mailUser, passwordUser, nameUser);
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(SC_FORBIDDEN);
        Allure.step("Проверка тела ответа");
        user.then().assertThat().body("success",equalTo(false));
        user.then().assertThat().body("message",equalTo("User already exists"));
    }
    @Test
    @DisplayName("Проверка проверка отсутствия поля mail")
    public void missingFieldMailTest() {
        var user = new User().create(null, passwordUser, nameUser);
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(SC_FORBIDDEN);
        Allure.step("Проверка тела ответа");
        user.then().assertThat().body("success",equalTo(false));
        user.then().assertThat().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Проверка проверка отсутствия поля passwor")
    public void missingFieldPasswordTest() {
        var user = new User().create(mailUser, null, nameUser);
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(SC_FORBIDDEN);
        Allure.step("Проверка тела ответа");
        user.then().assertThat().body("success",equalTo(false));
        user.then().assertThat().body("message",equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("Проверка проверка отсутствия поля name")
    public void missingFieldNameTest() {
        var user = new User().create(mailUser, passwordUser, null);
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(SC_FORBIDDEN);
        Allure.step("Проверка тела ответа");
        user.then().assertThat().body("success",equalTo(false));
        user.then().assertThat().body("message",equalTo("Email, password and name are required fields"));
    }
}
