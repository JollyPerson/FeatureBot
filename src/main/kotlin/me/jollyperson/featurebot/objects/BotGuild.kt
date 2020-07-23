package me.jollyperson.featurebot.objects

import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent

data class BotGuild(val id: Long, val guildName: String, val guildSettings: GuildSettings, val users: MutableSet<BotUser>, val roles: MutableCollection<BotRole>)