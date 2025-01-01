package io.hhplus.cleanarchitecture.support.concurrent

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 동시성 테스트 유틸 클래스
 */
class ConcurrencyTestUtils {

    companion object {
        // 같은 action 여러번 동시 실행
        fun executeConcurrently(count: Int, action: Runnable) {
            val executorService: ExecutorService = Executors.newFixedThreadPool(count)
            val futures = (1..count).map {
                CompletableFuture.runAsync(action, executorService)
            }

            CompletableFuture.allOf(*futures.toTypedArray()).join()
            executorService.shutdown()
        }

        // 다른 action 여러번 동시 실행
        fun executeConcurrently(actions: List<Runnable>) {
            val executorService: ExecutorService = Executors.newFixedThreadPool(actions.size)
            val futures = actions.map { action ->
                CompletableFuture.runAsync(action, executorService)
            }
            CompletableFuture.allOf(*futures.toTypedArray()).join()
            executorService.shutdown()
        }
    }
}