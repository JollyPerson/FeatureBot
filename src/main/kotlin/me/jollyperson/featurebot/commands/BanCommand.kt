package me.jollyperson.featurebot.commands

import me.jollyperson.featurebot.handlers.Command
import me.jollyperson.featurebot.handlers.CommandContext
import me.jollyperson.featurebot.objects.PermissionFlag
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.util.*

class BanCommand : Command {
    override fun handle(ctx: CommandContext) {
        val channel = ctx.channel
        val author = ctx.author
        val builder = EmbedBuilder()
        builder.setColor(Color.CYAN)
        if (ctx.args.isEmpty()) {
            builder.setDescription("PREFIXban <@user> (time) (reason)")
            channel.sendMessage(builder.build()).queue()
        }
    }

    override val name: String
        get() = "ban"

    override val help: String?
        get() = null

    override val permissions: List<PermissionFlag>
        get() {
            val flags = ArrayList<PermissionFlag>()
            flags.add(PermissionFlag.BAN)
            return flags
        }
}