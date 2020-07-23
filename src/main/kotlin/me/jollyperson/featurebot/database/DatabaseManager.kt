package me.jollyperson.featurebot.database

import me.jollyperson.featurebot.configuration.SettingsManager
import me.jollyperson.featurebot.objects.BotGuild
import me.jollyperson.featurebot.objects.BotUser
import me.jollyperson.featurebot.objects.PermissionFlag

interface DatabaseManager {
    fun getGuild(id: Long): BotGuild?
    fun addGuild(guild: BotGuild)
    fun removeGuild(id: Long)
    fun isLogging(guildID: Long): Boolean
    fun setLogging(guildID: Long, logging: Boolean)
    fun setLogID(guildID: Long, logChannelID: String?)
    fun getLogID(guildID: Long): String?
    fun getPrefix(guildId: Long): String?
    fun setPrefix(guildId: Long, newPrefix: String?)
    fun getStarterWallet(guildID: Long): Long
    fun getStarterBank(guildID: Long): Long
    fun getStarterDebt(guildID: Long): Long
    fun addUser(guildID: Long, user: BotUser?): Boolean
    fun removeUser(guildID: Long, userID: Long): Boolean
    fun hasPermission(guildID: Long, userID: Long, perms: List<PermissionFlag?>?): Boolean

    companion object {
        val DATABASE: DatabaseManager = MongoDBDataSource(SettingsManager().build())
        val CACHE: DatabaseManager = Cache()
    }
}