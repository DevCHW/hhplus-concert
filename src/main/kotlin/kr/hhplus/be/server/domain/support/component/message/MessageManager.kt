package kr.hhplus.be.server.domain.support.component.message

import org.springframework.stereotype.Service

@Service
class MessageManager(
    private val senders: Set<MessageSender>,
) {
    fun send(
        platform: MessagePlatform,
        topic: String,
        key: String? = null,
        message: Map<String, Any>,
    ) {
        val sender = find(platform)
        sender.send(topic, key, message)
    }

    private fun find(platform: MessagePlatform): MessageSender {
        return senders
            .firstOrNull { it.isSupport(platform) } ?: throw IllegalArgumentException("${platform} 메세지 발행 구현체가 없습니다.")
    }
}