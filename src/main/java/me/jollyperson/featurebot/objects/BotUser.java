package me.jollyperson.featurebot.objects;

public class BotUser {

    private long id;
    private BotEconomyUser eco;
    private String permission;

    public long getId() {
        return id;
    }

    public BotEconomyUser getEco() {
        return eco;
    }

    public BotUser(long id, BotEconomyUser eco, CommandPermission perm) {
        this.id = id;
        this.eco = eco;
        this.permission = perm.toString();
    }

    public CommandPermission getPerm() {
        return CommandPermission.valueOf(permission);
    }
}
