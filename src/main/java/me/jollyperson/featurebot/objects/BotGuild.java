package me.jollyperson.featurebot.objects;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.jollyperson.featurebot.objects.GuildSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BotGuild {

    private long id;
    private String guildName;
    private GuildSettings guildSettings;
    private Set<BotUser> users;

    public BotGuild(long id, String guildName, GuildSettings guildSettings, Set<BotUser> users) {
        this.id = id;
        this.guildName = guildName;
        this.guildSettings = guildSettings;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public String getGuildName() {
        return guildName;
    }

    public GuildSettings getGuildSettings() {
        return guildSettings;
    }

    public Set<BotUser> getUsers() {
        return users;
    }

    public static DBObject toMongoDBElement(BotGuild botGuild){
        GuildSettings settings = botGuild.getGuildSettings();
        DBObject botGuildMongo = new BasicDBObject()
                .append("id", botGuild.getId())
                .append("name", botGuild.getGuildName());
        DBObject guildSettingsMongo = new BasicDBObject();
        guildSettingsMongo.put("logEnabled", settings.isLogEnabled());
        guildSettingsMongo.put("logChannelID", settings.getLogChannelID());
        guildSettingsMongo.put("dailyEnabled", settings.isDailyEnabled());
        guildSettingsMongo.put("dailyIncrease", settings.getDailyEconomy());
        botGuildMongo.put("settings", guildSettingsMongo);

        List<DBObject> usersMongo = new ArrayList<DBObject>();
        for (BotUser user : botGuild.getUsers()) {
            DBObject object = new BasicDBObject();
            object.put("id", user.getId());
            object.put("permissionLevel", user.getPerm());
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

}
