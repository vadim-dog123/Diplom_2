package api.model.user;

public class UserInformationModel {
    private boolean success;
    private UserModel user;

    public UserInformationModel(boolean success, UserModel user) {
        this.success = success;
        this.user = user;
    }

    public UserInformationModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
