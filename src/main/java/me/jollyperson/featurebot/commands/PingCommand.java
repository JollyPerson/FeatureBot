package me.jollyperson.featurebot.commands;

import me.jollyperson.featurebot.handlers.Command;
import me.jollyperson.featurebot.handlers.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class PingCommand implements Command {
    @Override
    public void handle(CommandContext ctx) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Ping: " + ctx.getJDA().getGatewayPing());
        builder.setFooter("Command executed by " + ctx.getAuthor().getAsTag());
        builder.setColor(Color.CYAN);
        ctx.getChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Returns bot pig";
    }
}
