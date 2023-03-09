package api.user;

import api.User;
import api.model.user.AuthorizationModel;
import api.model.user.UserInformationModel;
import junit.framework.TestCase;

import java.util.Random;
import static org.apache.http.HttpStatus.*;
public class UserTest extends TestCase {

    private final User user = new User();
    int random = new Random().nextInt(10000000);
    public void testCreate() {
        var user1 = this.user.create("mail"+random+"@fvfgv.rtyy", "pas"+random,"name");
        user1.then().assertThat().statusCode(SC_OK);
        AuthorizationModel authorizationModel = user1.getBody().as(AuthorizationModel.class);
      System.out.println(authorizationModel.getAccessToken());
        System.out.println(authorizationModel.getRefreshToken());

      var c =  user.getUserInformation(authorizationModel.getAccessToken()).getBody().as(UserInformationModel.class);
        System.out.println(c.getUser().getEmail()+"   "+c.getUser().getName());
        System.out.println(c.isSuccess());
        user.patchUserInformation(authorizationModel.getAccessToken(),"190@098766.ljl","CYPERXREN","123456789");
       var  cc =  user.getUserInformation(authorizationModel.getAccessToken()).getBody().as(UserInformationModel.class);
        System.out.println(cc.getUser().getEmail()+"   "+cc.getUser().getName());
        user.deleteUser(authorizationModel.getAccessToken());
         cc =  user.getUserInformation(authorizationModel.getAccessToken()).getBody().as(UserInformationModel.class);
        System.out.println(cc.getUser().getEmail()+"   "+cc.getUser().getName());
    }
}