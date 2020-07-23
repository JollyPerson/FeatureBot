package me.jollyperson.featurebot.handlers

import me.jollyperson.featurebot.objects.PermissionFlag
import java.util.*

interface Command {
    fun handle(ctx: CommandContext)
    val name: String
    val help: String?
    val aliases: List<String?>
        get() = Arrays.asList()

    val permissions: List<PermissionFlag>
}