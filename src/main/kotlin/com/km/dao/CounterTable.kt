package com.km.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object CounterTable : LongIdTable("counters") {
    val name = varchar("name", length = 50)
    val value = long("value")
}

object DatabaseFactory {

    fun init() {
        val connect = Database.connect(hikari())
        transaction(connect) {
            SchemaUtils.create(CounterTable)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.h2.Driver"
        config.jdbcUrl = "jdbc:h2:mem:test"
        config.maximumPoolSize = 3
        config.minimumIdle = 1
        config.idleTimeout = 60000
        config.isAutoCommit = false
        config.validate()
        return HikariDataSource(config)
    }
}

class CounterDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CounterDao>(CounterTable)

    var name by CounterTable.name
    var value by CounterTable.value
}