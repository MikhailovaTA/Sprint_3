package tests.serialization;

import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class DataCourier {

    @Nullable
    private String login;
    private String password;
    private String firstName;

    public DataCourier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public DataCourier() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}

