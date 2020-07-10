package me.jollyperson.featurebot.database;

public class MongoDBDataSource implements DatabaseManager{
    @Override
    public String getPrefix(long guildId) {
        return null;
    }

    @Override
    public void setPrefix(long guildId, String newPrefix) {

    }
}
