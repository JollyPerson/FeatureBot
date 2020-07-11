package me.jollyperson.featurebot.listeners;

import me.jollyperson.featurebot.database.DatabaseManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    private DatabaseManager databaseManager = DatabaseManager.INSTANCE;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            databaseManager.getGuild(guild.getIdLong());
            System.out.println("firing ");
        }
    }
}
