package me.jollyperson.featurebot.database;

import com.mongodb.*;
import com.mongodb.client.*;
import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.objects.*;
import net.dv8tion.jda.api.entities.Guild;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class MongoDBDataSource implements DatabaseManager{

    private Settings settings;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private static final Logger logger = LoggerFactory.getLogger(MongoDBDataSource.class);

    @Override
    public void addGuild(BotGuild guild) {
        collection.insertOne(guildToMongo(guild));
    }

    @Override @Nullable
    public BotGuild getGuild(long id) {
        FindIterable<Document> guildResult = collection.find(eq("id", id));
        logger.info(String.valueOf(id));
        GuildSettings guildSettings;
        BotGuild botGuild;
        Set<BotUser> userSet = new HashSet<>();
        List<Document> users;
        for (Document guildMongo : guildResult) {
            Document settingsDoc = guildMongo.get("settings", Document.class);
            guildSettings = new GuildSettings(settingsDoc.getLong("dailyIncrease"),
                    settingsDoc.getBoolean("dailyEnabled"),
                    settingsDoc.getBoolean("logEnabled"),
                    settingsDoc.getLong("logChannelID"),
                    settingsDoc.getString("prefix")
                            );
            users = guildMongo.get("users", List.class);
            users.forEach((document -> {
                //logger.info(document.getLong("id").toString());
                Document eco = document.get("eco", Document.class);
                //logger.info(eco.getLong("balance").toString());
                userSet.add(new BotUser(document.getLong("id"),
                        new BotEconomyUser(
                                eco.getLong("wallet"),
                                eco.getLong("bank"),
                                eco.getLong("debt")),
                        CommandPermission.valueOf(document.getString("permissionLevel"))));
            }));
           // botGuild = new BotGuild(guildMongo.getLong("id"), guildMongo.getString("name"), guildSettings, userSet);
            //logger.info(guildMongo.get("settings", Document.class).getString("prefix"));
            botGuild = new BotGuild(guildMongo.getLong("id"), guildMongo.getString("name"), guildSettings, userSet);
            return botGuild;
        }
        return null;
    }

    private Document guildToMongo(BotGuild botGuild){
        System.out.println(mongoClient);
        GuildSettings settings = botGuild.getGuildSettings();
        Document botGuildMongo = new Document()
                .append("_id", botGuild.getId())
                .append("name", botGuild.getGuildName());
        DBObject guildSettingsMongo = new BasicDBObject();
        guildSettingsMongo.put("logEnabled", settings.isLogEnabled());
        guildSettingsMongo.put("logChannelID", settings.getLogChannelID());
        guildSettingsMongo.put("dailyEnabled", settings.isDailyEnabled());
        guildSettingsMongo.put("dailyIncrease", settings.getDailyEconomy());
        guildSettingsMongo.put("prefix", settings.getPrefix());
        botGuildMongo.put("settings", guildSettingsMongo);

        List<DBObject> usersMongo = new ArrayList<>();
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
        mongoClient = MongoClients.create(
                "mongodb://localhost"
        );
        database = mongoClient.getDatabase(settings.getMongoDB().getDatabase());
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

        collection.updateOne(
                eq("id",guildId),
                set("settings.prefix", newPrefix));
    }
}
