package api.user;

import api.User;
import api.model.user.AuthorizationModel;
import api.model.user.UserInformationModel;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Изменение данных пользователя")
public class ChangingUserDataTest extends GeneraActions {

    private final String text;
    private final boolean isAuthorization;
    private final String mail;
    private final String password;
    private final String name;
    private final int statusCode;
    private final boolean success;
    private final String message;
    private String accessToken = "";

    public ChangingUserDataTest(String text, boolean isAuthorization, String mail, String password, String name, int statusCode, boolean success, String message) {
        this.text = text;
        this.isAuthorization = isAuthorization;
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.statusCode = statusCode;
        this.success = success;
        this.message = message;
    }

    @Parameterized.Parameters(name = "Изменение {0}, авторизация: {1}")
    public static Object[][] getTestData() {
        final Object[][] objects = {{"почты", true, mailUser + "t", passwordUser, nameUser, 200, true, ""},
                {"пароля", true, mailUser, passwordUser + "t", nameUser, 200, true, ""},
                {"имени", true, mailUser, passwordUser, nameUser + "t", 200, true, ""},
                {"имени", false, mailUser, passwordUser, nameUser + "t", 401, false, "You should be authorised"},
                {"почты", false, mailUser + "t", passwordUser, nameUser, 401, false, "You should be authorised"},
                {"пароля", false, mailUser, passwordUser + "t", nameUser, 401, false, "You should be authorised"},
                {"почты на уже существующую", true, mailSecondUser, passwordUser, nameUser, 403, false, "User with such email already exists"},
        };
        return objects;
    }

    @Test
    public void userLoginTest() {
        if (isAuthorization) {
            accessToken = user.getBody().as(AuthorizationModel.class).getAccessToken();
        }
        var userInformation = new User().patchUserInformation(accessToken, mail, name, password);
        Allure.step("Проверка кода ответа");
        userInformation.then().assertThat().statusCode(statusCode);
        Allure.step("Проверка тела ответа");
        if (statusCode == 200) {
            var responseBody = userInformation.getBody().as(UserInformationModel.class);
            assertEquals(responseBody.getUser().getEmail(), mail);
            assertEquals(responseBody.getUser().getName(), name);
            assertTrue(responseBody.isSuccess());
        } else {
            userInformation.then().assertThat().body("success", equalTo(success));
            userInformation.then().assertThat().body("message", equalTo(message));
        }
    }
}
