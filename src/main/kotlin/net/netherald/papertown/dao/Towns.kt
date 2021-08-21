package net.netherald.papertown.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Towns : IntIdTable() {
    val name : Column<String> = varchar("name",20)
    val owner : Column<String> = varchar("owner",20)
    val owneruuid : Column<String> = varchar("owneruuid",20)
    override val primaryKey = PrimaryKey(id)
}

class Town(id:EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Town>(Towns)
    var name by Towns.name
    var owner by Towns.owner
    var owneruuid by Towns.owneruuid
}