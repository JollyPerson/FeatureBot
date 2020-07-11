package me.jollyperson.featurebot.database;

import com.mongodb.*;
import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.objects.BotGuild;
import me.jollyperson.featurebot.objects.BotUser;
import me.jollyperson.featurebot.objects.GuildSettings;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class MongoDBDataSource implements DatabaseManager{

    private Settings settings;
    private MongoClient mongoClient;
    private DB database;
    private DBCollection collection;
    private static final Logger logger = LoggerFactory.getLogger(MongoDBDataSource.class);

    @Override
    public void addGuild(BotGuild guild) {
        collection.insert(guildToMongo(guild));
    }

    @Override
    public BotGuild getGuild(long id) {
        logger.info("works");
        DBObject query = new BasicDBObject("id", id);
        DBCursor cursor = collection.find(query);
        DBObject dbGuild = cursor.one();
        BotGuild guild;
        GuildSettings settings = new GuildSettings((long) dbGuild.get("settings.dailyIncrease"),
                (boolean) dbGuild.get("settings.dailyEnabled"),
                (boolean) dbGuild.get("settings.logEnabled"),
                (long) dbGuild.get("settings.logChannelID"),
                (String) dbGuild.get("settings.prefix"));
        DBObject dbObjects = (DBObject) dbGuild.get("users");
        logger.info(dbObjects.toString());
        //guild = new BotGuild();
        return null;
    }

    private DBObject guildToMongo(BotGuild botGuild){
        System.out.println(mongoClient);
        GuildSettings settings = botGuild.getGuildSettings();
        DBObject botGuildMongo = new BasicDBObject()
                .append("_id", botGuild.getId())
                .append("name", botGuild.getGuildName());
        DBObject guildSettingsMongo = new BasicDBObject();
        guildSettingsMongo.put("logEnabled", settings.isLogEnabled());
        guildSettingsMongo.put("logChannelID", settings.getLogChannelID());
        guildSettingsMongo.put("dailyEnabled", settings.isDailyEnabled());
        guildSettingsMongo.put("dailyIncrease", settings.getDailyEconomy());
        guildSettingsMongo.put("prefix", settings.getPrefix());
        botGuildMongo.put("settings", guildSettingsMongo);

        List<DBObject> usersMongo = new ArrayList<DBObject>();
        for (BotUser user : botGuild.getUsers()) {
            DBObject object = new BasicDBObject();
            object.put("id", user.getId());
            object.put("permissionLevel", user.getPerm().toString());
            DBObject eco = new BasicDBObject();
            eco.put("wallet", user.getEco().getWallet());
            eco.put("bank", user.getEco().getWallet());
            eco.put("debt", user.getEco().getWallet());
            eco.put("balance", user.getEco().getWallet());
            object.put("eco", eco);
            usersMongo.add(object);
        }
        botGuildMongo.put("users", usersMongo);
        return botGuildMongo;
    }

    public MongoDBDataSource(Settings settings)  {
        this.settings = settings;
        //mongoClient = new MongoClient(new MongoClientURI("mongodb://" + settings.getMongoDB().getAddress() + ":" + settings.getMongoDB().getPort()));
        mongoClient = new MongoClient();
        database = mongoClient.getDB(settings.getMongoDB().getDatabase());
        collection = database.getCollection(settings.getMongoDB().getCollection());
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
