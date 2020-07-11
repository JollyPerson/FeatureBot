package me.jollyperson.featurebot.database;

import me.jollyperson.featurebot.objects.BotGuild;
import me.jollyperson.featurebot.objects.BotUser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cache {
    private static final Cache INSTANCE = new Cache();

    public static Cache getInstance() {
        return INSTANCE;
    }

    public Cache() {
    }

    private Map<Long, BotGuild> guilds = new HashMap<Long, BotGuild>();

    public void addGuildToCache(BotGuild guild){
        guilds.put(guild.getId(), guild);
    }

    public void removeGuildFromCache(long id){
        guilds.remove(id);
    }

    public void removeGuildFromCache(BotGuild guild) {
        guilds.remove(guild.getId());
    }

    public BotGuild getGuildFromCache(long id){
        return guilds.get(id);
    }
}

