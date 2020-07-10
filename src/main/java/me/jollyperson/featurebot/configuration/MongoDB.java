package me.jollyperson.featurebot.configuration;

public class MongoDB {

    private String address;
    private String port;
    private String password;
    private String username;

    public MongoDB(String address, String port, String password, String username) {
        this.address = address;
        this.port = port;
        this.password = password;
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public String getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
