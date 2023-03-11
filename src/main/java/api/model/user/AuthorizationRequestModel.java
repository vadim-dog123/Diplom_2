package api.model.user;

public class AuthorizationRequestModel {
    private String email;
    private String password;

    public AuthorizationRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthorizationRequestModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
