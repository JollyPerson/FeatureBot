package me.jollyperson.featurebot.commands

import me.jollyperson.featurebot.database.DatabaseManager
import me.jollyperson.featurebot.handlers.Command
import me.jollyperson.featurebot.handlers.CommandContext
import me.jollyperson.featurebot.objects.PermissionFlag
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.util.*

class PrefixCommand(private val databaseManager: DatabaseManager) : Command {
    override fun handle(ctx: CommandContext) {
        if (ctx.args.isEmpty()) {
            ctx.channel.sendMessage(EmbedBuilder().setFooter("Command sent by " + ctx.author.asTag, ctx.author.avatarUrl).setDescription(getInstance().getGuildFromCache(ctx.guild.idLong).getGuildSettings().getPrefix().toString() + "prefix <prefix>").setColor(Color.RED).build()).queue()
            return
        }
        val prefix = ctx.args[0]
        getInstance().setPrefix(ctx.guild.idLong, prefix)
        INSTANCE.setPrefix(ctx.guild.idLong, prefix)
        ctx.channel.sendMessage(EmbedBuilder().setFooter("Command sent by " + ctx.author.asTag, ctx.author.avatarUrl).setDescription("Prefix set to `$prefix").build()).queue()
    }

    override val name: String
        get() = "prefix"

    override val permissions: List<PermissionFlag>
        get() {
            val flags = ArrayList<PermissionFlag>()
            flags.add(PermissionFlag.CHANGE_PREFIX)
            return flags
        }

    override val help: String?
        get() = "Changes the prefix of the this bot for this guild."

}