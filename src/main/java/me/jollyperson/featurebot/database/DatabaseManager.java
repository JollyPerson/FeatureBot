package me.jollyperson.featurebot.database;

import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;

public interface DatabaseManager {

    DatabaseManager INSTANCE = new MongoDBDataSource(new SettingsManager().build());

    boolean isLogging(long guildID);
    void setLogging(long guildID, boolean logging);
    void setLogID(long guildID, String logChannelID);
    String getLogID(long guildID);
    String getPrefix(long guildId);
    void setPrefix(long guildId, String newPrefix);
}
