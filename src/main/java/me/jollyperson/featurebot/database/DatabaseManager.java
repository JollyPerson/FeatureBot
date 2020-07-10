package me.jollyperson.featurebot.database;

public interface DatabaseManager {
    DatabaseManager INSTANCE = new MongoDBDataSource();

    String getPrefix(long guildId);
    void setPrefix(long guildId, String newPrefix);
}
