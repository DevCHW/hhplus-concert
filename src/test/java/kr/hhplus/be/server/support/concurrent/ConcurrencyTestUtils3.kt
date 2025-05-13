package kr.hhplus.be.server.support.concurrent

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * 동시성 테스트 유틸 클래스3
 * 비동기로 동작하는 코드에서 발생하는 예외 감지
 * try-finally를 통해 shutdown() 누락 방지
 */
object ConcurrencyTestUtils3 {

    // 같은 action 여러번 동시 실행
    fun executeConcurrently(count: Int, action: () -> Unit) {
        val startSignal = CountDownLatch(1) // 작업 시작 신호
        val doneSignal = CountDownLatch(count) // 작업 완료 신호

        val executorService = Executors.newFixedThreadPool(count)
        try {
            repeat(count) {
                executorService.submit {
                    startSignal.await() // 시작 신호가 올 때까지 대기
                    try {
                        action()
                    } finally {
                        doneSignal.countDown() // 작업 완료 시 신호
                    }
                }
            }

            startSignal.countDown() // 한 번에 모두 시작
            val completed = doneSignal.await(30, TimeUnit.SECONDS) // 30초 타임아웃
            if (!completed) {
                throw ConcurrencyTestException("작업이 타임아웃되었습니다.")
            }
        } finally {
            executorService.shutdown()
        }
    }

    fun executeConcurrently(vararg actions: () -> Unit) {
        val startSignal = CountDownLatch(1) // 작업 시작 신호
        val doneSignal = CountDownLatch(actions.size) // 작업 완료 신호

        val executorService = Executors.newFixedThreadPool(actions.size)
        try {
            actions.forEach { action ->
                executorService.submit {
                    startSignal.await() // 시작 신호가 올 때까지 대기
                    try {
                        action()
                    } finally {
                        doneSignal.countDown() // 작업 완료 알림
                    }
                }
            }

            startSignal.countDown() // 한 번에 모두 시작
            val completed = doneSignal.await(30, TimeUnit.SECONDS) // 30초 타임아웃
            if (!completed) {
                throw ConcurrencyTestException("작업이 타임아웃되었습니다.")
            }
            doneSignal.await() // 모든 작업이 끝날 때까지 대기
        } finally {
            executorService.shutdown()
        }
    }
}


class ConcurrencyTestException(message: String) : RuntimeException(message)