package me.jollyperson.featurebot.handlers;

import java.util.List;

public interface Command {
    void handle(CommandContext ctx);

    String getName();

    String getHelp();

    default List<String> getAliases() {
        return List.of(); // use Arrays.asList if you are on java 8
    }
}