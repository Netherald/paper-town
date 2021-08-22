package net.netherald.papertown

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class InitSQLDriver(private val plugin: Plugin) {
    companion object {
        lateinit var sqlConnection: Connection

        var database: String?        = null
        var table: String?           = null
    }

    private val inlineDatabase       = plugin.config.getString("DATABASE")

    init {
        database       = inlineDatabase
    }

    fun loadSQLModule() {
        val logger   = plugin.logger
        val url      = plugin.config.getString("SQL_IP")
        val username = plugin.config.getString("SQL_USERNAME")
        val password = plugin.config.getString("SQL_PASSWORD")
        val port     = plugin.config.getInt("SQL_PORT")

        logger.info("Loading driver...")

        Class.forName("com.mysql.cj.jdbc.Driver")
        plugin.logger.info("Connecting to SQL...")

        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://${url}:${port}", username, password)
            logger.info("Connected to ${url}:${port}")
        } catch (exception: SQLException) {
            exception.printStackTrace()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        val statement: Statement = sqlConnection.createStatement()
        statement.executeUpdate("create database if not exists $database default character set utf8")
        statement.executeUpdate("create table if not exists towns("+
                "ID int primary key not null auto_increment unique,"+
                "NAME varchar(20) not null,"+
                "OWNER varchar(20) not null,"+
                "MEMBERS varchar(1000) not null"
        )
        statement.close()
    }

    fun closeConnection() {
        try {
            if (!sqlConnection.isClosed) {
                sqlConnection.close()
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun createTown(name : String, owner : Player) {
        val statement = sqlConnection.createStatement()
        statement.executeUpdate("insert into paper_towny.towns (NAME, OWNER, MEMBERS) values ($name,${owner.uniqueId},${owner.uniqueId})")
    }
}