package me.jollyperson.featurebot.listeners

import me.jollyperson.featurebot.configuration.Settings
import me.jollyperson.featurebot.database.Cache
import me.jollyperson.featurebot.database.DatabaseManager
import me.jollyperson.featurebot.objects.BotEconomyUser
import me.jollyperson.featurebot.objects.BotGuild
import me.jollyperson.featurebot.objects.BotUser
import me.jollyperson.featurebot.objects.GuildSettings
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*

class GuildJoin(private val botSettings: Settings?, private val databaseManager: DatabaseManager, private val cache: Cache) : ListenerAdapter() {
    override fun onGuildJoin(@Nonnull event: GuildJoinEvent) {
        val guild = event.guild
        val settings = GuildSettings(100, true, false, 0, botSettings.getPrefix())
        val users: MutableSet<BotUser?> = HashSet()
        for (member in guild.members) {
            users.add(BotUser(member.idLong, BotEconomyUser(0, 0, 1), CommandPermission.DEFAULT))
        }
        val botGuild = BotGuild(guild.idLong, guild.name, settings, users)
        cache.addGuildToCache(botGuild)
        databaseManager.addGuild(botGuild)
    }

}