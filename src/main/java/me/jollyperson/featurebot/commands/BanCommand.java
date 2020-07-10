package me.jollyperson.featurebot.commands;

import me.jollyperson.featurebot.handlers.Command;
import me.jollyperson.featurebot.handlers.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;

public class BanCommand implements Command {
    @Override
    public void handle(CommandContext ctx) {
        TextChannel channel = ctx.getChannel();
        User author = ctx.getAuthor();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.CYAN);
        if(ctx.getArgs().isEmpty()){
            builder.setDescription("PREFIXban <@user> (time) (reason)");
            channel.sendMessage(builder.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getHelp() {
        return null;
    }
}
