package me.jollyperson.featurebot;

import com.google.gson.Gson;
import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;
import me.jollyperson.featurebot.database.Cache;
import me.jollyperson.featurebot.handlers.Listener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class FeatureBot {

    private final Settings settings;
    private static final Logger logger = LoggerFactory.getLogger(FeatureBot.class);
    private JDA jda;
    private Cache cache = new Cache();

    private FeatureBot() throws LoginException {
        SettingsManager settingsBuilder = new SettingsManager();
        settings = settingsBuilder.build();
        JDABuilder builder = new JDABuilder();
        logger.info(new Gson().toJson(settings));
        builder.setActivity(Activity.of(settings.getActivityType(), settings.getActivity()));
        builder.setToken(settings.getToken());
        builder.addEventListeners(new Listener(settings, cache));
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
