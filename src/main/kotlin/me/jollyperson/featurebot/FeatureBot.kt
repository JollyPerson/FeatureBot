package me.jollyperson.featurebot

import com.google.gson.Gson
import me.jollyperson.featurebot.configuration.Settings
import me.jollyperson.featurebot.configuration.SettingsManager
import me.jollyperson.featurebot.database.Cache
import me.jollyperson.featurebot.database.DatabaseManager
import me.jollyperson.featurebot.handlers.CommandListener
import me.jollyperson.featurebot.listeners.GuildJoin
import me.jollyperson.featurebot.listeners.GuildLeave
import me.jollyperson.featurebot.listeners.ReadyListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import org.slf4j.LoggerFactory
import javax.security.auth.login.LoginException

class FeatureBot private constructor() {
    private val settings: Settings?
    private val jda: JDA
    private val cache: DatabaseManager = DatabaseManager.CACHE
    private val databaseManager: DatabaseManager = DatabaseManager.DATABASE

    companion object {
        private val logger = LoggerFactory.getLogger(FeatureBot::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                val featureBot = FeatureBot()
            } catch (e: LoginException) {
                e.printStackTrace()
            }
        }
    }

    init {
        val settingsBuilder = SettingsManager()
        settings = settingsBuilder.build()
        val builder = JDABuilder()
        logger.info(Gson().toJson(settings))
        builder.setActivity(Activity.of(settings.activityType, settings.activity))
        builder.setToken(settings.token)
        builder.addEventListeners(CommandListener(settings, cache))
        builder.addEventListeners(GuildJoin(settings, databaseManager, cache))
        builder.addEventListeners(ReadyListener())
        builder.addEventListeners(GuildLeave())
        jda = builder.build()
        if (settings!!.isStreaming) {
            jda.presence.setPresence(Activity.streaming(settings.activity, settings.streamingLink), false)
        }
        logger.info("Bot enabled in " + (jda.guildCache.size() + 1) + " guilds.")
    }
}