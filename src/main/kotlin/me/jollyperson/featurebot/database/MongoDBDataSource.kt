package me.jollyperson.featurebot.database

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import me.jollyperson.featurebot.configuration.Settings
import me.jollyperson.featurebot.objects.*
import okhttp3.internal.addHeaderLenient
import org.bson.Document
import org.slf4j.LoggerFactory
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap

class MongoDBDataSource(private val settings: Settings?) : DatabaseManager {
    private val mongoClient: MongoClient?
    private val database: MongoDatabase
    private val collection: MongoCollection<Document>
    override fun addGuild(guild: BotGuild) {
        collection.insertOne(guildToMongo(guild))
    }

    override fun getGuild(id: Long): BotGuild? {
       /* val guildResult = collection.find(Filters.eq("id", id))
        val guildSettings: GuildSettings
        */val botGuild: BotGuild /*
        val userSet: MutableSet<BotUser?> = HashSet()
        val users: List<Document>
        for (guildMongo in guildResult) {
            val settingsDoc = guildMongo.get("settings", Document::class.java)
            guildSettings = GuildSettings(settingsDoc.getLong("dailyIncrease"),
                    settingsDoc.getBoolean("dailyEnabled"),
                    settingsDoc.getBoolean("logEnabled"),
                    settingsDoc.getLong("logChannelID"),
                    settingsDoc.getString("prefix")
            )
            users = guildMongo.get<List<*>>("users", MutableList::class.java)
            users.forEach(Consumer { document: Document ->
                //logger.info(document.getLong("id").toString());
                val eco = document.get("eco", Document::class.java)
                //logger.info(eco.getLong("balance").toString());
                userSet.add(BotUser(document.getLong("id"),
                        BotEconomyUser(
                                eco.getLong("wallet"),
                                eco.getLong("bank"),
                                eco.getLong("debt")),
                        CommandPermission.valueOf(document.getString("permissionLevel"))))
            })
            // botGuild = new BotGuild(guildMongo.getLong("id"), guildMongo.getString("name"), guildSettings, userSet);
            //logger.info(guildMongo.get("settings", Document.class).getString("prefix"));
            botGuild = BotGuild(guildMongo.getLong("id"), guildMongo.getString("name"), guildSettings, userSet)
            //logger.info(new Gson().toJson(botGuild));

        */
            TODO("fix making it a botguild")
            return botGuild


    }

    private fun guildToMongo(botGuild: BotGuild): Document {
        println(mongoClient)
        val settings = botGuild.guildSettings
        val botGuildMongo = Document()
                .append("id", botGuild.id)
                .append("name", botGuild.guildName)
        val guildSettingsMongo: DBObject = BasicDBObject()
        guildSettingsMongo.put("logEnabled", settings!!.isLogEnabled)
        guildSettingsMongo.put("logChannelID", settings.logChannelID)
        guildSettingsMongo.put("dailyEnabled", settings.isDailyEnabled)
        guildSettingsMongo.put("dailyIncrease", settings.dailyEconomy)
        guildSettingsMongo.put("prefix", settings.prefix)
        botGuildMongo["settings"] = guildSettingsMongo
        val usersMongo: MutableList<DBObject> = ArrayList()
        Document()
                .append("id", botGuild)
                .append("name", botGuild.guildName)
            guildSettingsMongo.put("logEnabled", settings.isLogEnabled)
            guildSettingsMongo.put("logChannelID", settings.logChannelID)
            guildSettingsMongo.put("dailyEnabled", settings.isDailyEnabled)
            guildSettingsMongo.put("dailyIncrease", settings.dailyEconomy)
            guildSettingsMongo.put("prefix", settings.prefix)
            botGuildMongo["settings"] = guildSettingsMongo

        val roles = BasicDBObject()
        botGuild.roles.forEach{
            val group = BasicDBObject()
            group["name"] = it.name
            group["permissions"] = it.permissions
            roles[it.id.toString()] = group
        }
        botGuildMongo["groups"] = roles
        for (user in botGuild.users) {
            val dbject: DBObject = BasicDBObject()
            dbject.put("id", user.id)
            dbject.put("groups", user.groups)
            val eco: DBObject = BasicDBObject()
            eco.put("wallet", user.eco.wallet)
            eco.put("bank", user.eco.wallet)
            eco.put("debt", user.eco.wallet)
            eco.put("balance", user.eco.wallet)
            dbject.put("eco", eco)
            usersMongo.add(dbject)
        }


        botGuildMongo["users"] = usersMongo
        return botGuildMongo
    }

    override fun isLogging(guildID: Long): Boolean {
        return false
    }

    override fun setLogging(guildID: Long, logging: Boolean) {}
    override fun setLogID(guildID: Long, logChannelID: String?) {}
    override fun removeGuild(id: Long) {}
    override fun getStarterWallet(guildID: Long): Long {
        return 0
    }

    override fun getStarterBank(guildID: Long): Long {
        return 0
    }

    override fun getStarterDebt(guildID: Long): Long {
        return 0
    }

    override fun addUser(guildID: Long, user: BotUser?): Boolean {
        return false
    }

    override fun removeUser(guildID: Long, userID: Long): Boolean {
        return false
    }

    override fun hasPermission(guildID: Long, userID: Long, perms: List<PermissionFlag?>?): Boolean {
        return false
    }

    override fun getLogID(guildID: Long): String? {
        return null
    }

    override fun getPrefix(guildId: Long): String? {
        return "!"
    }

    override fun setPrefix(guildId: Long, newPrefix: String?) {
        collection.updateOne(
                Filters.eq("id", guildId),
                Updates.set("settings.prefix", newPrefix))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MongoDBDataSource::class.java)
    }

    init {
        //mongoClient = new MongoClient(new MongoClientURI("mongodb://" + settings.getMongoDB().getAddress() + ":" + settings.getMongoDB().getPort()));
        mongoClient = MongoClients.create(MongoClientSettings.builder().build())
        if (mongoClient == null) {
            logger.info("NULLLLLELRLTR")
        }
        database = mongoClient.getDatabase(settings.getMongoDB().database)
        collection = database.getCollection(settings.getMongoDB().collection)
    }
}