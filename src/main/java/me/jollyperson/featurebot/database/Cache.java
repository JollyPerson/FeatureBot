package me.jollyperson.featurebot.database;

import me.jollyperson.featurebot.objects.BotGuild;
import me.jollyperson.featurebot.objects.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Cache {
    private static Cache INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(Cache.class);
    private final Map<Long, BotGuild> guilds;
    public static Cache getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Cache();
        }

        return INSTANCE;
    }

    private Cache() {
        guilds = new HashMap<>();

    }

    public String getPrefix(long guildID){
        return guilds.get(guildID).getGuildSettings().getPrefix();
    }

    public void setPrefix(long guildId, String prefix){
        BotGuild guild = guilds.get(guildId);
        guild.getGuildSettings().setPrefix(prefix);
        guilds.put(guildId, guild);
    }



    public void addGuildToCache(BotGuild guild){
        logger.info(guild.getId() + "test");
        logger.info(guild.toString());
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

