package me.jollyperson.featurebot.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoURI;
import me.jollyperson.featurebot.configuration.Settings;

import java.net.UnknownHostException;

public class MongoDBDataSource implements DatabaseManager{

    private Settings settings;
    private MongoClient mongoClient;
    public MongoDBDataSource(Settings settings)  {
        this.settings = settings;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://" + settings.getMongoDB().getAddress() + ":" + settings.getMongoDB().getPort()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isLogging(long guildID) {
        return false;
    }

    @Override
    public void setLogging(long guildID, boolean logging) {

    }

    @Override
    public void setLogID(long guildID, String logChannelID) {

    }

    @Override
    public String getLogID(long guildID) {
        return null;
    }

    @Override
    public String getPrefix(long guildId) {
        return "!";
    }

    @Override
    public void setPrefix(long guildId, String newPrefix) {

    }
}
