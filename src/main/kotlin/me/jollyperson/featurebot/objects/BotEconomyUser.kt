package me.jollyperson.featurebot.objects

class BotEconomyUser(val wallet: Long, val bank: Long, val debt: Long) {
    var balance: Long = 0
        get() = wallet + bank - debt.also { field = it }
        private set

}