package api.user;

import api.BasicTest;
import api.User;
import api.model.user.AuthorizationModel;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

@DisplayName("Логин пользователя")
@RunWith(Parameterized.class)
public class UserLoginTest extends BasicTest {

    private final String text;
    private final String mail;
    private final String password;
    private final int statusCode;
    private final boolean success;
    private final String message;

    public UserLoginTest(String text, String mail, String password, int statusCode, boolean success, String message) {
        this.text = text;
        this.mail = mail;
        this.password = password;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters(name = "Автоизация {0}, логин: {1}, пароль:{2}")
    public static Object[][] getTestData() {
        final Object[][] objects = {
                {"под cуществующим пользователем", mailUser, passwordUser, SC_OK, true, ""},
                {"с неверным логином", mailUser + "e", passwordUser, SC_UNAUTHORIZED, false, "email or password are incorrect"},
                {"с неверным паролем", mailUser, passwordUser + "e", SC_UNAUTHORIZED, false, "email or password are incorrect"}
        };
        return objects;
    }

    @Test
    public void userLoginTest() {
        var user = new User().authorizationUser(mail, password);
        Allure.step("Проверка кода ответа");
        user.then().assertThat().statusCode(statusCode);
        Allure.step("Проверка тела ответа");
        if (SC_OK == statusCode) {
            var uiqueUser = user.getBody().as(AuthorizationModel.class);
            assertEquals(uiqueUser.getUser().getEmail(), mail);
            assertEquals(uiqueUser.getUser().getName(), nameUser);
            assertFalse(uiqueUser.getRefreshToken().isBlank());
            assertFalse(uiqueUser.getAccessToken().isBlank());
            assertTrue(uiqueUser.getSuccess());
        } else {
            user.then().assertThat().body("success", equalTo(success));
            user.then().assertThat().body("message", equalTo(message));
        }
    }
}
