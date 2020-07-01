package me.jollyperson.featurebot.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;

public class SettingsManager {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private String token;
    private String activity;
    private boolean streaming;
    private String streamingLink;


    public SettingsManager setToken(String token) {
        this.token = token;
        return this;
    }

    public SettingsManager setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public SettingsManager setStreaming(boolean streaming) {
        this.streaming = streaming;
        return this;
    }

    public SettingsManager setStreamingLink(String streamingLink) {
        this.streamingLink = streamingLink;
        return this;
    }

    public SettingsManager() {
        loadJson();
    }

    private void loadJson() {
        try{
            getSettingsFromResources();
        }catch (IOException e){
            e.printStackTrace();
        }
       /* try {
            Settings settings = gson.fromJson(getSettingsFromResources().toString(), Settings.class);
        } catch (IOException e){
            e.printStackTrace();
    } */
    }


    public Settings build() {
        return new Settings(this);
    }


    private File getSettingsFromResources() throws IOException {
        File settingsFile = new File("settings.json");
        if(!settingsFile.exists()){
            settingsFile.createNewFile();

            System.out.println(settingsFile.getAbsoluteFile());
            FileOutputStream outputStream = new FileOutputStream(settingsFile);
            Settings settings = new Settings(new SettingsManager().setActivity("CSGO").setStreaming(true).setToken("Hello").setStreamingLink("test"));
            outputStream.write(gson.toJson(settings).getBytes());
            outputStream.close();
        }
        return settingsFile;
    }
}