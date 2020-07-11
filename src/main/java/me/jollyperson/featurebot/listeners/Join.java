package me.jollyperson.featurebot.listeners;

import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.database.Cache;
import me.jollyperson.featurebot.database.DatabaseManager;
import me.jollyperson.featurebot.objects.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class Join extends ListenerAdapter {

    private final Settings botSettings;
    private final DatabaseManager databaseManager;
    private final Cache cache;

    public Join(Settings settings, DatabaseManager databaseManager, Cache cache) {
        this.databaseManager = databaseManager;
        this.botSettings = settings;
        this.cache = cache;
    }


    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {

        Guild guild = event.getGuild();
        GuildSettings settings = new GuildSettings(100, true, false, 0, botSettings.getPrefix());
        Set<BotUser> users = new HashSet<>();
        for (Member member : guild.getMembers()) {
            users.add(new BotUser(member.getIdLong(), new BotEconomyUser(0,0,1), CommandPermission.DEFAULT));
        }
        BotGuild botGuild = new BotGuild(guild.getIdLong(), guild.getName(), settings, users);
        cache.addGuildToCache(botGuild);
        databaseManager.addGuild(botGuild);
    }
}
