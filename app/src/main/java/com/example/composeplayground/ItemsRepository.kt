package com.example.composeplayground

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

class ItemsRepository {

    suspend fun getItems(): Flow<List<Int>> {
        return flow {
            emit((1.. 100).toList())
        }
    }

    suspend fun getItemsWithThrottle(): Flow<Int> {
        return flow {
            for (num in 1..100) {
                emit(num)
                delay(1000)
            }
        }
    }
}

fun <T> Flow<T>.throttle(periodMillis: Long): Flow<T> {
    if (periodMillis < 0) return this
    return flow {
        conflate().collect { value ->
            emit(value)
            delay(periodMillis)
        }
    }
}
