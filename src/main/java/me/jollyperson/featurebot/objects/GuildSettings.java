package me.jollyperson.featurebot.objects;

import me.jollyperson.featurebot.configuration.Settings;
import me.jollyperson.featurebot.configuration.SettingsManager;
import me.jollyperson.featurebot.database.DatabaseManager;

public class GuildSettings {

    private long dailyEconomy = 100;
    private boolean dailyEnabled = true;
    private boolean logEnabled = false;
    private long logChannelID;
    private String prefix = new SettingsManager().build().getPrefix();

    public void setDailyEconomy(long dailyEconomy) {
        this.dailyEconomy = dailyEconomy;
    }

    public void setDailyEnabled(boolean dailyEnabled) {
        this.dailyEnabled = dailyEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public void setLogChannelID(long logChannelID) {
        this.logChannelID = logChannelID;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public GuildSettings(long dailyEconomy, boolean dailyEnabled, boolean logEnabled, long logChannelID, String prefix) {
        this.dailyEconomy = dailyEconomy;
        this.dailyEnabled = dailyEnabled;
        this.logEnabled = logEnabled;
        this.logChannelID = logChannelID;
        this.prefix = prefix;
    }

    public long getDailyEconomy() {
        return dailyEconomy;
    }

    public boolean isDailyEnabled() {
        return dailyEnabled;
    }

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public long getLogChannelID() {
        return logChannelID;
    }
}
