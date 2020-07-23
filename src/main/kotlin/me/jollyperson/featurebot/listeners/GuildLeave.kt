package me.jollyperson.featurebot.listeners

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory

class GuildLeave : ListenerAdapter() {
    override fun onGuildLeave(@Nonnull event: GuildLeaveEvent) {
        getInstance().removeGuildFromCache(event.guild.idLong)
        INSTANCE.removeGuild(event.guild.idLong)
        LOGGER.info(event.guild.name + " removed from cache and database.")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(GuildLeave::class.java)
    }
}