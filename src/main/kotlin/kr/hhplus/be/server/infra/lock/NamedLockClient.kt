package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.lock.DistributedLockClient
import kr.hhplus.be.server.domain.lock.ReleaseLock
import java.sql.Connection
import java.sql.PreparedStatement
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

class NamedLockClient(
    private val dataSource: DataSource,
) : DistributedLockClient {

    override fun getLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): ReleaseLock? {
        val connection = dataSource.connection
        val isLockAcquired = connection.prepareStatement(NamedLockQueryType.GET_LOCK.query).use {
            it.setString(1, key)
            it.setInt(2, waitTime.toInt())
            executeLock(key, it, NamedLockQueryType.GET_LOCK)
        }

        if (!isLockAcquired) {
            connection.close()
            return null
        }

        return object : ReleaseLock {
            override fun release() {
                connection.use { connection ->
                    connection.prepareStatement(NamedLockQueryType.RELEASE_LOCK.query).use {
                        it.setString(1, key)
                        executeLock(key, it, NamedLockQueryType.RELEASE_LOCK)
                    }
                }
            }
        }
    }

    override fun releaseLock(lock: ReleaseLock) {
        lock.release()
    }

    private fun executeLock(
        lockName: String,
        preparedStatement: PreparedStatement,
        queryType: NamedLockQueryType,
        connection: Connection? = null,
    ): Boolean {
        preparedStatement.executeQuery().use { resultSet ->
            if (!resultSet.next()) {
                connection?.close()
                throw IllegalStateException("NamedLock 수행 실패. type = [${queryType.name}], lockName [$lockName], connection=[${preparedStatement.connection}]")
            }

            val result = resultSet.getInt(1)
            return result == 1
        }
    }

    private enum class NamedLockQueryType(
        val query: String,
    ) {
        GET_LOCK("SELECT GET_LOCK(?, ?)"),
        RELEASE_LOCK("SELECT RELEASE_LOCK(?)"),
    }
}