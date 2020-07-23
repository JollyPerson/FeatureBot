package me.jollyperson.featurebot.configuration

import com.google.gson.GsonBuilder
import net.dv8tion.jda.api.entities.Activity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class SettingsManager {
    val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    var token: String? = null
    var activity: String? = null
    var activityType: Activity.ActivityType? = null
    var streaming = false
    var streamingLink: String? = null
    var ownerID: String? = null
    var prefix: String? = null
    fun setOwnerID(id: String?): SettingsManager {
        ownerID = id
        return this
    }

    fun setToken(token: String?): SettingsManager {
        this.token = token
        return this
    }

    fun setActivity(activity: String?): SettingsManager {
        this.activity = activity
        return this
    }

    fun setStreaming(streaming: Boolean): SettingsManager {
        this.streaming = streaming
        return this
    }

    fun setStreamingLink(streamingLink: String?): SettingsManager {
        this.streamingLink = streamingLink
        return this
    }

    private fun loadJson() {
        try {
            val file = settingsFromResources
            val lines = Files.lines(Path.of(file.path))
            val data = lines.collect(Collectors.joining("\n"))
            lines.close()
            settings = gson.fromJson(data, Settings::class.java)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        /* try {
            Settings settings = gson.fromJson(getSettingsFromResources().toString(), Settings.class);
        } catch (IOException e){
            e.printStackTrace();
    } */
    }

    fun build(): Settings? {
        return settings
    }

    @get:Throws(IOException::class)
    private val settingsFromResources: File
        private get() {
            val settingsFile = File("settings.json")
            if (!settingsFile.exists()) {
                settingsFile.createNewFile()
                println(settingsFile.absoluteFile)
                val outputStream = FileOutputStream(settingsFile)
                outputStream.write(outJson.toByteArray())
                outputStream.close()
            }
            return settingsFile
        }

    companion object {
        private const val outJson = "{\r\n  \"token\": \"tokenNotSet\",\r\n  \"ownerID\": \"idNotSet\",\r\n  \"prefix\": \"?\",\r\n  \"activityType\": \"DEFAULT\",\r\n  \"activity\": \"activityNotSet\",\r\n  \"streaming\": false,\r\n  \"streamingLink\": \"notSet\",\r\n  \"mongoDB\": {\r\n    \"address\": \"localhost\",\r\n    \"port\": \"27017\",\r\n    \"username\": \"default\",\r\n    \"password\": \"password\", \r\n \"database\": \"featurebot\",\r\n \"collection\": \"default\" }\r\n}"
        private var settings: Settings? = null
    }

    init {
        if (settings == null) {
            loadJson()
        }
    }
}