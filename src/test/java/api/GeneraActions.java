package api;

import api.User;
import api.model.user.AuthorizationModel;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import static org.apache.http.HttpStatus.*;
import java.util.Random;

public abstract class GeneraActions {
    protected static  String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa78"};
    protected static String mailUser = String.format("mail%d@fvfgv.rtyy", new Random().nextInt(10000000));
    protected static String mailSecondUser= String.format("mail%d@fvfgv.rtyy", new Random().nextInt(10000000));
    protected static String passwordUser = String.format("pas%d", new Random().nextInt(10000000));
    protected static String nameUser = String.format("name%d", new Random().nextInt(10000000));
    protected Response user;
    protected  Response secondUser;

    @Before
    public void beforeTest() {
        user = new User().create(mailUser, passwordUser, nameUser);
        secondUser = new User().create(mailSecondUser, passwordUser, nameUser);
    }

    @After
    public void afterEachTest() {
        new User().deleteUser(user.getBody().as(AuthorizationModel.class).getAccessToken());
        new User().deleteUser(secondUser.getBody().as(AuthorizationModel.class).getAccessToken());
    }

}
