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
    String token;
    String activity;
    Activity.ActivityType activityType;
    boolean streaming;
    String streamingLink;

    String ownerID;
    String prefix;
    private static String outJson = "{\r\n  \"token\": \"tokenNotSet\",\r\n  \"ownerID\": \"idNotSet\",\r\n  \"prefix\": \"?\",\r\n  \"activityType\": \"DEFAULT\",\r\n  \"activity\": \"activityNotSet\",\r\n  \"streaming\": false,\r\n  \"streamingLink\": \"notSet\",\r\n  \"mongoDB\": {\r\n    \"address\": \"localhost\",\r\n    \"port\": \"27017\",\r\n    \"username\": \"default\",\r\n    \"password\": \"password\"\r\n  }\r\n}";

    private static Settings settings;


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
        if(settings == null){
            loadJson();
        }
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
            outputStream.write(outJson.getBytes());
            outputStream.close();
        }
        return settingsFile;
    }
}