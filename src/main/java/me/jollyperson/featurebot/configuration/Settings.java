package me.jollyperson.featurebot.configuration;

import net.dv8tion.jda.api.entities.Activity;

public class Settings {

    String token = "tokenNotSet";
    String activity = "activityNotSet";
    Activity.ActivityType activityType = Activity.ActivityType.DEFAULT;
    boolean streaming = false;
    String streamingLink = "no link provided";
    private Settings settings;
    private String ownerID = "noOwnerID";

    public Activity.ActivityType getActivityType() {
        return activityType;
    }

    public String getOwnerID(){return ownerID;}

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

    public Settings(SettingsManager builder){
        this.token = builder.token;
        this.activity = builder.activity;
        this.activityType = builder.activityType;
        this.streaming = builder.streaming;
        this.streamingLink = builder.streamingLink;
        this.ownerID = builder.ownerID;
    }
}
