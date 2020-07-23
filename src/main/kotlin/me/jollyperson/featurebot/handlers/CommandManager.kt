package me.jollyperson.featurebot.handlers

import me.jollyperson.featurebot.commands.BanCommand
import me.jollyperson.featurebot.commands.PingCommand
import me.jollyperson.featurebot.commands.PrefixCommand
import me.jollyperson.featurebot.database.DatabaseManager
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import java.awt.Color
import java.util.*
import java.util.regex.Pattern

class CommandManager(private val dbManager: DatabaseManager) {
    private val commands: MutableList<Command> = ArrayList()
    private fun addCommand(cmd: Command) {
        val nameFound = commands.stream().anyMatch { it: Command -> it.name.equals(cmd.name, ignoreCase = true) }
        require(!nameFound) { "A command with this name is already present" }
        commands.add(cmd)
    }

    fun getCommands(): List<Command> {
        return commands
    }

    fun getCommand(search: String): Command? {
        val searchLower = search.toLowerCase()
        for (cmd in commands) {
            if (cmd.name == searchLower || cmd.aliases.contains(searchLower)) {
                return cmd
            }
        }
        return null
    }

    fun handle(event: GuildMessageReceivedEvent, prefix: String?) {
        val split = event.message.contentRaw
                .replaceFirst("(?i)" + Pattern.quote(prefix).toRegex(), "")
                .split("\\s+").toTypedArray()
        val invoke = split[0].toLowerCase()
        val cmd = getCommand(invoke)
        if (cmd != null) {
            if (dbManager.hasPermission(event.guild.idLong, event.author.idLong, cmd.permissions)) {
                event.channel.sendTyping().queue()
                val args: List<String?> = Arrays.asList(*split).subList(1, split.size)
                val ctx = CommandContext(event, args)
                cmd.handle(ctx)
            } else {
                val embedBuilder = EmbedBuilder()
                embedBuilder.setTitle("You do not have permission to run this command: " + cmd.name)
                embedBuilder.setColor(Color.RED)
                embedBuilder.setFooter("Command executed by " + event.author.asTag, event.author.avatarUrl)
                event.channel.sendMessage(embedBuilder.build()).queue()
            }
        }
    }

    init {
        addCommand(PingCommand())
        addCommand(BanCommand())
        addCommand(PrefixCommand(INSTANCE))
    }
}