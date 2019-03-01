package cn.beautybase.authorization.core.security;

public class SimpleUser {

    private Long id;
    private String username;
    private boolean signUp;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getSignUp() {
        return this.getSignUp();
    }

    public void setSignUp(boolean signUp) {
        this.signUp = signUp;
    }

}
