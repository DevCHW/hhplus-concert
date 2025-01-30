package kr.hhplus.be.server.domain.support.lock

interface LockHandler : AutoCloseable {
    fun unlock()

    override fun close() {
        unlock()
    }
}