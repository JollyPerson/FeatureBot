package me.jollyperson.featurebot.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

public class SettingsBuilder {

    private final Gson gson;
    private String token;
    private String activity;
    private boolean streaming;
    private String streamingLink;


    public SettingsBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public SettingsBuilder setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public SettingsBuilder setStreaming(boolean streaming) {
        this.streaming = streaming;
        return this;
    }

    public SettingsBuilder setStreamingLink(String streamingLink) {
        this.streamingLink = streamingLink;
        return this;
    }

    public SettingsBuilder(){
        gson = new GsonBuilder().setPrettyPrinting().create();

    }


    public Settings build(){
        return new Settings(this);
    }
}
