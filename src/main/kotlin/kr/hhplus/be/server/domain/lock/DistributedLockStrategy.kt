package kr.hhplus.be.server.domain.lock

enum class DistributedLockStrategy(
    val clientName: String,
) {
    REDISSON("redissonLockClient"),
    LETTUCE("lettuceLockClient"),
    NAMED("namedLockClient"),
}