package api.user;

import api.User;
import api.model.user.AuthorizationModel;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.util.Random;

public abstract class GeneraActions {
    static String mailUser = String.format("mail%d@fvfgv.rtyy", new Random().nextInt(10000000));
    static String mailSecondUser= String.format("mail%d@fvfgv.rtyy", new Random().nextInt(10000000));
    static String passwordUser = String.format("pas%d", new Random().nextInt(10000000));
    static String nameUser = String.format("name%d", new Random().nextInt(10000000));
    Response user;
    Response secondUser;

    @Before
    public void beforeTest() {
        //    mailUser = String.format("mail%d@fvfgv.rtyy", random);
        //  passwordUser = String.format("pas%d", random);
        //   nameUser = String.format("name%d", random);
        user = new User().create(mailUser, passwordUser, nameUser);
        secondUser = new User().create(mailSecondUser, passwordUser, nameUser);
    }

    @After
    public void afterEachTest() {
        new User().deleteUser(user.getBody().as(AuthorizationModel.class).getAccessToken());
        new User().deleteUser(secondUser.getBody().as(AuthorizationModel.class).getAccessToken());
    }

}
