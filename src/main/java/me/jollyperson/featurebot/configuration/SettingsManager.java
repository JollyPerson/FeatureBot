package me.jollyperson.featurebot.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.dv8tion.jda.api.entities.Activity;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsManager {

    final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    String token = "tokenNotSet";
    String activity = "activityNotSet";
    Activity.ActivityType activityType = Activity.ActivityType.DEFAULT;
    boolean streaming = false;
    String streamingLink = "no link provided";
    private Settings settings;
    String ownerID;


    public SettingsManager setOwnerID(String id){
        this.ownerID = id;
        return this;
    }

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
            File file = getSettingsFromResources();
            Stream<String> lines = Files.lines(Path.of(file.getPath()));
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            settings = gson.fromJson(data, Settings.class);
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
        return settings;
    }


    private File getSettingsFromResources() throws IOException {
        File settingsFile = new File("settings.json");
        if(!settingsFile.exists()){
            settingsFile.createNewFile();
            System.out.println(settingsFile.getAbsoluteFile());
            FileOutputStream outputStream = new FileOutputStream(settingsFile);
            String jsonS = "{\"token\":\"tokenNotSet\",\"ownerID\":\"idNotSet\",\"activityType\":\"DEFAULT\",\"activity\":\"activityNotSet\",\"streaming\":false,\"streamingLink\":\"notSet\"}";
            outputStream.write(jsonS.getBytes());
            outputStream.close();
        }
        return settingsFile;
    }
}