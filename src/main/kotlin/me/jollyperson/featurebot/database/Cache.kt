package me.jollyperson.featurebot.database

import me.jollyperson.featurebot.objects.BotGuild
import me.jollyperson.featurebot.objects.BotUser
import me.jollyperson.featurebot.objects.PermissionFlag
import org.slf4j.LoggerFactory
import java.util.*
import java.util.function.Consumer

class Cache internal constructor() : DatabaseManager {
    private val guilds: MutableMap<Long, BotGuild?>
    private val members: MutableMap<Long, BotUser?>
    override fun getGuild(id: Long): BotGuild? {
        return null
    }

    override fun addGuild(guild: BotGuild) {}
    override fun removeGuild(id: Long) {}
    override fun isLogging(guildID: Long): Boolean {
        return false
    }

    override fun setLogging(guildID: Long, logging: Boolean) {}
    override fun setLogID(guildID: Long, logChannelID: String?) {}
    override fun getLogID(guildID: Long): String? {
        return null
    }

    override fun getPrefix(guildID: Long): String? {
        return guilds[guildID].getGuildSettings().prefix
    }

    override fun setPrefix(guildId: Long, prefix: String?) {
        val guild = guilds[guildId]
        guild.getGuildSettings().prefix = prefix
        guilds[guildId] = guild
    }

    override fun getStarterWallet(guildID: Long): Long {
        return 0
    }

    override fun getStarterBank(guildID: Long): Long {
        return 0
    }

    override fun getStarterDebt(guildID: Long): Long {
        return 0
    }

    override fun addUser(guildID: Long, botUser: BotUser?): Boolean {
        return if (guilds[guildID] == null) {
            guilds[guildID].getUsers().add(botUser)
            guilds[guildID].getUsers().contains(botUser)
        } else false
    }

    override fun removeUser(guildID: Long, userID: Long): Boolean {
        if (guilds[guildID] == null) {
            return false
        }
        for (botUser in guilds[guildID].getUsers()) {
            if (botUser.id == userID) {
                guilds[guildID].getUsers().remove(botUser)
                return true
            }
        }
        return false
    }

    override fun hasPermission(guildID: Long, userID: Long, perms: List<PermissionFlag?>?): Boolean {
        return false
    }

    fun addGuildToCache(guild: BotGuild) {
        guilds[guild.id] = guild
        guild.users.forEach(Consumer { botUser: BotUser? -> members[botUser.getId()] = botUser })
    }

    fun removeGuildFromCache(id: Long) {
        guilds.remove(id)
    }

    fun removeGuildFromCache(guild: BotGuild) {
        guilds.remove(guild.id)
    }

    fun getGuildFromCache(id: Long): BotGuild? {
        return guilds[id]
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Cache::class.java)
    }

    init {
        guilds = HashMap()
        members = HashMap()
    }
}