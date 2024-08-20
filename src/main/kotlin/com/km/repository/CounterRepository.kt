package com.km.repository

import com.km.dao.CounterDao
import com.km.dao.CounterTable
import io.ktor.server.plugins.NotFoundException
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class CounterRepository {

    suspend fun getAll() = runInTransaction {
        CounterDao
            .all()
            .associate { it.name to it.value }
    }

    suspend fun getByName(name: String): Map<String, Long> = runInTransaction {
        CounterDao
            .find { CounterTable.name eq name }
            .limit(1)
            .associate { it.name to it.value }
    }

    suspend fun deleteByName(name: String) = runInTransaction {
        CounterTable.deleteWhere { this.name.eq(name) }
    }

    suspend fun incrementAndGet(name: String): Long = runInTransaction {
        CounterDao.findSingleByAndUpdate(CounterTable.name eq name) {
            it.value = it.value.inc()
        }?.value ?: throw NotFoundException("Counter not found")
    }

    suspend fun create(name: String, value: Long): CounterDao = runInTransaction {
        CounterDao.new {
            this.name = name
            this.value = value
        }
    }

    private suspend fun <T> runInTransaction(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}