package me.jollyperson.featurebot;

import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class FeatureBot {

    private final Settings settings;


    private FeatureBot(){
        SettingsManager settingsBuilder = new SettingsManager();
        settings = settingsBuilder.build();
        /*JDABuilder builder = new JDABuilder();
        builder.setActivity(Activity.of(Activity.ActivityType.valueOf(settings.getActivityType()), settings.getActivity()));
        builder.setToken(settings.getToken()); */

    }

    public static void main(String[] args){
        FeatureBot featureBot = new FeatureBot();
    }
}
