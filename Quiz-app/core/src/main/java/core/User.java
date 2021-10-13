package core;

public abstract class User {
    private String userName;

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
