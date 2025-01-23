package kr.hhplus.be.server.domain.lock

interface ReleaseLock : AutoCloseable{
    fun release()

    override fun close() {
        release()
    }
}