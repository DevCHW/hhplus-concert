package kr.hhplus.be.server.domain.support.lock

interface LockResourceManager : AutoCloseable {
    fun unlock()

    override fun close() {
        unlock()
    }
}