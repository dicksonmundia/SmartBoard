package DataObjects;

public final class UserInstance {
    private User user;

    private final static UserInstance USER_INSTANCE = new UserInstance();

    private UserInstance () {}

    public static UserInstance getInstance() {return USER_INSTANCE;}

    public void setUser (User newUser) {this.user = newUser;}

    public User getUser () {return user;}
}
