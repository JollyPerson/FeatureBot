package me.jollyperson.featurebot;

import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class FeatureBot {

    private final Settings settings;


    private FeatureBot() throws LoginException {
        SettingsManager settingsBuilder = new SettingsManager();
        settings = settingsBuilder.build();
        JDABuilder builder = new JDABuilder();
        builder.setActivity(Activity.of(settings.getActivityType(), settings.getActivity()));
        builder.setToken(settings.getToken());
        JDA jda = builder.build();

    }

    public static void main(String[] args){
        try {
            FeatureBot featureBot = new FeatureBot();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
