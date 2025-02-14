package kr.hhplus.be.server.api.support.config

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@EnableAsync
@Configuration
class AsyncConfig : AsyncConfigurer{

    override fun getAsyncExecutor(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 5
        executor.maxPoolSize = 5
        executor.queueCapacity = 5
        executor.keepAliveSeconds = 30
        executor.setThreadNamePrefix("async-executor-")
        executor.initialize()
        return executor
    }

}