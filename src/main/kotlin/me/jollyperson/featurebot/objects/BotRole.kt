package me.jollyperson.featurebot.objects

import com.fasterxml.jackson.databind.node.BooleanNode

data class BotRole(val id: Long, val name: String, val permissions: MutableCollection<PermissionFlag>) {

    fun hasPermission(flag: PermissionFlag): Boolean {
        return permissions.contains(flag)
    }
}
