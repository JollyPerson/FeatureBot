package me.jollyperson.featurebot.handlers

import me.duncte123.botcommons.BotCommons
import me.jollyperson.featurebot.configuration.Settings
import me.jollyperson.featurebot.database.Cache
import me.jollyperson.featurebot.database.DatabaseManager
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.slf4j.LoggerFactory

class CommandListener(private val settings: Settings?, private val cache: Cache) : ListenerAdapter() {
    private val manager = CommandManager()
    private val dbManager: DatabaseManager? = null
    override fun onReady(@Nonnull event: ReadyEvent) {
        LOGGER.info("{} is ready", event.jda.selfUser.asTag)
    }

    override fun onGuildMemberJoin(@Nonnull event: GuildMemberJoinEvent) {
        val dontDoThis = event.guild.getTextChannelsByName("bot-spam", true)
        if (dontDoThis.isEmpty()) {
            return
        }
        val pleaseDontDoThisAtAll = dontDoThis[0]
        val useGuildSpecificSettingsInstead = String.format("Welcome %s to %s",
                event.member.user.asTag, event.guild.name)
        pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue()
    }

    override fun onGuildMemberLeave(@Nonnull event: GuildMemberLeaveEvent) {
        val dontDoThis = event.guild.getTextChannelsByName("bot-spam", true)
        if (dontDoThis.isEmpty()) {
            return
        }
        val pleaseDontDoThisAtAll = dontDoThis[0]
        val useGuildSpecificSettingsInstead = String.format("Goodbye %s",
                event.member.user.asTag)
        pleaseDontDoThisAtAll.sendMessage(useGuildSpecificSettingsInstead).queue()
    }

    override fun onGuildMessageReceived(@Nonnull event: GuildMessageReceivedEvent) {
        val user = event.author
        if (user.isBot || event.isWebhookMessage) {
            return
        }
        val guildId = event.guild.idLong
        var prefix = settings.getPrefix()
        if (cache.getGuildFromCache(guildId) != null) {
            prefix = cache.getPrefix(guildId)
        }
        val raw = event.message.contentRaw
        if (raw.equals(prefix + "shutdown", ignoreCase = true)
                && user.id == settings.getOwnerID()) {
            LOGGER.info("Shutting down")
            event.jda.shutdown()
            BotCommons.shutdown(event.jda)
            return
        }
        if (raw.toLowerCase().startsWith(prefix!!.toLowerCase())) {
            manager.handle(event, prefix)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CommandListener::class.java)
    }

}