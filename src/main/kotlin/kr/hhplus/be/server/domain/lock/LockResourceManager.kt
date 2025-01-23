package kr.hhplus.be.server.domain.lock

interface LockResourceManager : AutoCloseable {
    fun unlock()

    override fun close() {
        unlock()
    }
}