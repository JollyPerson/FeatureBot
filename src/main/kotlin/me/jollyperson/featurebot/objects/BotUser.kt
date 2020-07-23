package me.jollyperson.featurebot.objects

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.Permission

data class BotUser(val id: Long, val eco: BotEconomyUser, val groups: MutableCollection<BotRole>,val userPerms: MutableList<PermissionFlag>) {
    /**
     * Returns the group(s) of this player.
     *
     * @return      the groups of this user
     * @see Group
     */

    fun addPermission(permissionFlag: PermissionFlag) {
        userPerms.add(permissionFlag)
    }

    fun removePermission(permissionFlag: PermissionFlag?) {
        userPerms.remove(permissionFlag)
    }

    fun hasPermission(flag: PermissionFlag?, jda: JDA, guildID: Long, allowRoleOverrides: Boolean): Boolean {
        return if (userPerms.contains(flag)) {
            if (allowRoleOverrides) {
                jda.getGuildById(guildID)!!.getMemberById(id)!!.hasPermission(Permission.ADMINISTRATOR)
            } else true
        } else false
    }

    fun hasPermission(flag: PermissionFlag?): Boolean {
        val isPerm = userPerms.contains(flag)
        if(!isPerm){
            groups.forEach { _ -> return hasPermission(flag) }
        }
        return isPerm
    }

    init {
        groups
    }
}