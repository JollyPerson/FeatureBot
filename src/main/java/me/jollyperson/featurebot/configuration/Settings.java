package me.jollyperson.featurebot.configuration;

public class Settings {

    private String token;
    private String activity;
    private String activityType;

    public String getActivityType() {
        return activityType;
    }

    private boolean streaming;
    private String streamingLink;

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

    }
}
