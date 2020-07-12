package me.jollyperson.featurebot;

import com.google.gson.Gson;
import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;
import me.jollyperson.featurebot.database.Cache;
import me.jollyperson.featurebot.database.DatabaseManager;
import me.jollyperson.featurebot.handlers.CommandListener;
import me.jollyperson.featurebot.listeners.Join;
import me.jollyperson.featurebot.listeners.ReadyListener;
import me.jollyperson.featurebot.objects.BotGuild;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.Set;

public class FeatureBot {

    private final Settings settings;
    private static final Logger logger = LoggerFactory.getLogger(FeatureBot.class);
    private final JDA jda;
    private final Cache cache = Cache.getInstance();
    private final DatabaseManager databaseManager = DatabaseManager.INSTANCE;

    private FeatureBot() throws LoginException {
        SettingsManager settingsBuilder = new SettingsManager();

        settings = settingsBuilder.build();
        JDABuilder builder = new JDABuilder();
        logger.info(new Gson().toJson(settings));
        builder.setActivity(Activity.of(settings.getActivityType(), settings.getActivity()));
        builder.setToken(settings.getToken());
        builder.addEventListeners(new CommandListener(settings, cache));
        builder.addEventListeners(new Join(settings, databaseManager, cache));
        builder.addEventListeners(new ReadyListener());
        jda = builder.build();
        if(settings.isStreaming()) {
           jda.getPresence().setPresence(Activity.streaming(settings.getActivity(), settings.getStreamingLink()), false );
        }
        logger.info("Bot enabled in " + (jda.getGuildCache().size() + 1) + " guilds.");

    }

    public static void main(String[] args){
        try {
            FeatureBot featureBot = new FeatureBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
