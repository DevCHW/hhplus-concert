package kr.hhplus.be.server.support.concurrent

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * 동시성 테스트 유틸 클래스
 * 모두 동시에 시작하도록 개선
 */
object ConcurrencyTestUtils2 {

    // 같은 action 여러번 동시 실행
    fun executeConcurrently(count: Int, action: Runnable) {
        val executorService = Executors.newFixedThreadPool(count)
        val startSignal = CountDownLatch(1) // 작업 시작 신호
        val doneSignal = CountDownLatch(count) // 작업 완료 신호

        repeat(count) {
            executorService.submit {
                startSignal.await() // 시작 신호가 올 때까지 대기
                try {
                    action.run()
                } finally {
                    doneSignal.countDown() // 작업 완료 시 신호
                }
            }
        }

        startSignal.countDown() // 한 번에 모두 시작
        doneSignal.await() // 모든 작업 완료될 때까지 대기
        executorService.shutdown()
    }

    fun executeConcurrently(actions: List<Runnable>) {
        val executorService = Executors.newFixedThreadPool(actions.size)
        val startSignal = CountDownLatch(1) // 작업 시작 신호
        val doneSignal = CountDownLatch(actions.size) // 작업 완료 신호

        actions.forEach { action ->
            executorService.submit {
                startSignal.await() // 시작 신호가 올 때까지 대기
                try {
                    action.run()
                } finally {
                    doneSignal.countDown() // 작업 완료 알림
                }
            }
        }

        startSignal.countDown() // 모든 스레드에게 시작하라고 신호
        doneSignal.await() // 모든 작업이 끝날 때까지 대기
        executorService.shutdown()
    }
}