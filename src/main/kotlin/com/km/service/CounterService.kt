package com.km.service

import com.km.repository.CounterRepository

class CounterService(val countersRepository: CounterRepository) {

    suspend fun create(name: String, value: Long) = countersRepository.create(name, value)

    suspend fun read(name: String): Map<String, Long> = countersRepository.getByName(name)

    suspend fun increment(name: String): Long = countersRepository.incrementAndGet(name)

    suspend fun delete(name: String) = countersRepository.deleteByName(name)

    suspend fun getAll() = countersRepository.getAll()
}