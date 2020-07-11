package me.jollyperson.featurebot.configuration;

import com.google.gson.Gson;
import net.dv8tion.jda.api.entities.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {

    private static final Logger logger = LoggerFactory.getLogger(Settings.class);
    String token;
    String activity;
    Activity.ActivityType activityType;
    boolean streaming;
    String streamingLink;
    private Settings settings;
    private String ownerID;
    String prefix;
    MongoDB mongoDB;

    private Settings(){

    }

    public MongoDB getMongoDB() {
        return mongoDB;
    }

    public Activity.ActivityType getActivityType() {
        return activityType;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public String getToken() {
        return token;
    }

    public String getActivity() {
        return activity;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public String getStreamingLink() {
        return streamingLink;
    }

    public String getPrefix() {
        return prefix;
    }



}

