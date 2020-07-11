package me.jollyperson.featurebot.commands;

import me.jollyperson.featurebot.database.Cache;
import me.jollyperson.featurebot.database.DatabaseManager;
import me.jollyperson.featurebot.handlers.Command;
import me.jollyperson.featurebot.handlers.CommandContext;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class PrefixCommand implements Command {

    private final DatabaseManager databaseManager;

    public PrefixCommand(DatabaseManager manager){
        this.databaseManager = manager;
    }
    @Override
    public void handle(CommandContext ctx) {
        if(ctx.getArgs().isEmpty()){


            //ctx.getChannel().sendMessage(new EmbedBuilder().setFooter("Command sent by " + ctx.getAuthor().getAsTag(), ctx.getAuthor().getAvatarUrl()).setDescription(Cache.getInstance().getGuildFromCache(ctx.getGuild().getIdLong()).getGuildSettings().getPrefix() + "prefix <prefix>").setColor(Color.RED).build());
        }
    }

    @Override
    public String getName() {
        return "prefix";
    }

    @Override
    public String getHelp() {
        return "Changes the prefix of the this bot for this guild.";
    }
}
