package kr.hhplus.be.server.infra.lock

import kr.hhplus.be.server.domain.support.component.lock.LockClient
import kr.hhplus.be.server.domain.support.component.lock.LockHandler
import kr.hhplus.be.server.domain.support.component.lock.LockStrategy
import java.sql.Connection
import java.sql.PreparedStatement
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

class NamedLockClient(
    private val dataSource: DataSource,
) : LockClient {
    override fun isSupport(strategy: LockStrategy): Boolean {
        return strategy == LockStrategy.MYSQL_NAMED_LOCK
    }

    override fun tryLock(key: String, waitTime: Long, leaseTime: Long, timeUnit: TimeUnit): LockHandler? {
        val connection = dataSource.connection
        val isLockAcquired = connection.prepareStatement(NamedLockQueryType.GET_LOCK.query).use {
            it.setString(1, key)
            it.setInt(2, waitTime.toInt())
            executeNamedLock(key, it, NamedLockQueryType.GET_LOCK)
        }

        if (!isLockAcquired) {
            connection.close()
            return null
        }

        return object : LockHandler {
            override fun unlock() {
                releaseLock(connection, key)
            }
        }
    }

    private fun releaseLock(connection: Connection, lockName: String) {
        connection.use { conn ->
            conn.prepareStatement(NamedLockQueryType.RELEASE_LOCK.query).use { prepareStatement ->
                prepareStatement.setString(1, lockName)
                executeNamedLock(lockName, prepareStatement, NamedLockQueryType.RELEASE_LOCK)
            }
        }
    }

    private fun executeNamedLock(
        lockName: String,
        preparedStatement: PreparedStatement,
        queryType: NamedLockQueryType,
        connection: Connection? = null,
    ): Boolean {
        preparedStatement.executeQuery().use { resultSet ->
            if (!resultSet.next()) {
                connection?.close()
                throw IllegalStateException("NamedLock 수행 실패. queryType=[${queryType.name}], lockName=[$lockName], connection=[${preparedStatement.connection}]")
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