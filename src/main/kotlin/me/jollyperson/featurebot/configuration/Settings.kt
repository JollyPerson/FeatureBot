package me.jollyperson.featurebot.configuration

import net.dv8tion.jda.api.entities.Activity
import org.slf4j.LoggerFactory

class Settings private constructor() {
    var token: String? = null
    var activity: String? = null
    var activityType: Activity.ActivityType? = null
    var isStreaming = false
    var streamingLink: String? = null
    private val settings: Settings? = null
    val ownerID: String? = null
    var prefix: String? = null
    var mongoDB: MongoDB? = null

    companion object {
        private val logger = LoggerFactory.getLogger(Settings::class.java)
    }
}