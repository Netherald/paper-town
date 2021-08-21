package net.netherald.papertown

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.netherald.papertown.dao.Town
import net.netherald.papertown.dao.Towns
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Database.Companion.connect
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

class SQLManager(val plugin:Plugin) {

    private var database = plugin.config.getString("SQL_DATABASE")
    val logger = plugin.logger
    private val url = plugin.config.getString("SQL_IP")
    private val username = plugin.config.getString("SQL_USERNAME")
    private val password = plugin.config.getString("SQL_PASSWORD")
    private val port = plugin.config.getInt("SQL_PORT")

    fun init() {
        if (!isNull()) {
            transaction(Database.connect(connect(url!!, username!!, password!!, port))) {
                SchemaUtils.createDatabase(database!!)
            }
        }
    }

    fun addTown(town:String,player:Player) {
        if (!isNull()) transaction(Database.connect(connect(url!!,username!!,password!!,port))) {
            val newtown = Town.new {
                name = town
                owner = player.name
                owneruuid = player.uniqueId.toString()
            }
        }
    }

    private fun isNull() : Boolean {
        return (url.isNullOrEmpty() || username.isNullOrEmpty() || password.isNullOrEmpty())
    }

    private fun connect(url:String,user:String,pw:String,port:Int) : DataSource {
        val config = HikariConfig().apply {
            jdbcUrl ="jdbc:mysql://$url:$port"
            username = user
            password = pw
            driverClassName = "com.mysql.jdbc.Driver"
        }
        return HikariDataSource(config)
    }
}