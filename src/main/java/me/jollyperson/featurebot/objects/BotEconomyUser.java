package me.jollyperson.featurebot.objects;

public class BotEconomyUser {

    private long wallet;
    private long bank;
    private long balance;
    private long debt;

    public long getWallet() {
        return wallet;
    }

    public long getBank() {
        return bank;
    }

    public BotEconomyUser(long wallet, long bank, long debt) {
        this.wallet = wallet;
        this.bank = bank;
        this.debt = debt;
    }

    public long getBalance() {
        return balance = (wallet + bank) - debt;
    }

    public long getDebt() {
        return debt;
    }
}
