package me.jollyperson.featurebot.listeners

import me.jollyperson.featurebot.configuration.Settings
import me.jollyperson.featurebot.database.DatabaseManager
import me.jollyperson.featurebot.objects.BotEconomyUser
import me.jollyperson.featurebot.objects.BotUser
import me.jollyperson.featurebot.objects.CommandPermission
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class UserJoin(private val settings: Settings, private val manager: DatabaseManager) : ListenerAdapter() {
    override fun onGuildMemberJoin(@Nonnull event: GuildMemberJoinEvent) {
        val id = event.user.idLong
        val guildID = event.guild.idLong
        val economyUser = BotEconomyUser(manager.getStarterWallet(guildID), manager.getStarterBank(guildID), manager.getStarterDebt(guildID))
        val user = BotUser(id, economyUser, CommandPermission.DEFAULT)
        getInstance().addUser(guildID, user)
        INSTANCE.addUser(guildID, id)
    }

}