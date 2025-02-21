package kr.hhplus.be.server.domain.support.component.message

interface MessageSender {
    fun isSupport(messagePlatform: MessagePlatform): Boolean

    fun send(
        topic: String,
        key: String?,
        message: Map<String, Any>,
    ): Boolean
}