package me.jollyperson.featurebot.configuration;

public class MongoDB {

    private String address;
    private String port;
    private String password;
    private String username;

    public String getCollection() {
        return collection;
    }

    private String database;
    private String collection;

    public String getDatabase() {
        return database;
    }

    public MongoDB(String address, String port, String password, String username, String database, String collection) {
        this.address = address;
        this.port = port;
        this.password = password;
        this.username = username;
        this.database = database;
        this.collection = collection;
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
