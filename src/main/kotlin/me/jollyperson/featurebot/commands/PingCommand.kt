package me.jollyperson.featurebot.commands

import me.jollyperson.featurebot.handlers.Command
import me.jollyperson.featurebot.handlers.CommandContext
import me.jollyperson.featurebot.objects.PermissionFlag
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.util.*

class PingCommand : Command {
    override fun handle(ctx: CommandContext) {
        val builder = EmbedBuilder()
        builder.setTitle("Ping: " + ctx.jda.gatewayPing)
        builder.setFooter("Command executed by " + ctx.author.asTag)
        builder.setColor(Color.CYAN)
        ctx.channel.sendMessage(builder.build()).queue()
    }

    override val name: String
        get() = "ping"

    override val help: String?
        get() = "Returns bot pig"

    override val permissions: List<PermissionFlag>
        get() {
            val flags = ArrayList<PermissionFlag>()
            flags.add(PermissionFlag.PING)
            return flags
        }
}