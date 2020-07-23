package me.jollyperson.featurebot.listeners

import me.jollyperson.featurebot.database.DatabaseManager
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReadyListener : ListenerAdapter() {
    private val databaseManager: DatabaseManager = INSTANCE
    override fun onReady(@Nonnull event: ReadyEvent) {
        for (guild in event.jda.guilds) {
            getInstance().addGuildToCache(databaseManager.getGuild(guild.idLong))
        }
    }
}