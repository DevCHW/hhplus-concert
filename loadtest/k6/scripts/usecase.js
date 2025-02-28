import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
    vus: 50, // 동시 사용자 수
    duration: '1m', // 테스트 실행 시간
};

const host = 'http://host.docker.internal:8080'; // API HOST 지정
const userId = `user_${__VU}_${Date.now()}`; // 사용자마다 고유한 ID 사용
const concertId = ''; // 콘서트 ID 지정
const concertScheduleId = ''; // 콘서트 스케줄 ID 지정

export default function () {
    let vars = {};

    // 잔고 충전
    let chargeRes = http.put(`${host}/api/v1/balance/charge`, JSON.stringify({
        userId: userId,
        amount: 100
    }), { headers: { 'Content-Type': 'application/json' } });

    check(chargeRes, {
        '잔고 충전 성공': (res) => res.status === 200,
    });

    // 잔고 조회
    let balanceRes = http.get(`${host}/api/v1/balance?userId=${userId}`, {
        headers: { 'Content-Type': 'application/json' }
    });

    check(balanceRes, {
        '잔고 조회 성공': (res) => res.status === 200,
    });

    // 대기열 토큰 생성
    let queueTokenRes = http.post(`${host}/api/v1/queue/token`, JSON.stringify({ userId: userId }), {
        headers: { 'Content-Type': 'application/json' }
    });

    check(queueTokenRes, {
        '대기열 토큰 생성 성공': (res) => res.status === 200,
    });

    vars.queueToken = queueTokenRes.json()?.data?.token;

    // 대기열 토큰 조회
    while (true) {
        let queueTokenCheckRes = http.get(`${host}/api/v1/queue/token`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': userId,
                'Queue-Token': vars.queueToken,
            }
        });

        const queueTokenStatus = queueTokenCheckRes.json()?.data?.token;

        // 상태가 "ACTIVE"면 종료
        if (queueTokenStatus === 'ACTIVE') {
            console.log(`대기열 토큰이 활성 상태가 아닙니다.`);
            break;
        }

        sleep(1); // 1초 대기 후 다시 조회
    }

    check(queueTokenCheckRes, {
        '대기열 토큰 조회 성공': (res) => res.status === 200,
    });


    // 예약 가능 콘서트 일정 목록 조회
    let schedulesRes = http.get(`${host}/api/v1/concerts/${vars.concertId}/schedules/available`, {
        headers: {
            'Content-Type': 'application/json',
            'Queue-Token': vars.queueToken,
        }
    });

    check(schedulesRes, {
        '예약 가능 일정 조회 성공': (res) => res.status === 200,
    });

    vars.concertScheduleId = schedulesRes.json()?.data?.[0]?.id;

    // 예약 가능 좌석 목록 조회
    let seatsRes = http.get(`${host}/api/v1/concerts/${vars.concertId}/schedules/${vars.concertScheduleId}/seats/available`, {
        headers: {
            'Content-Type': 'application/json',
            'Queue-Token': vars.queueToken,
        }
    });

    check(seatsRes, {
        '예약 가능 좌석 조회 성공': (res) => res.status === 200,
    });

    vars.seatId = seatsRes.json()?.data?.[0]?.id;

    // 예약
    let reservationRes = http.post(`${host}/api/v1/reservations`, JSON.stringify({
        concertId: vars.concertId,
        userId: userId,
        seatId: vars.seatId,
    }), {
        headers: {
            'Content-Type': 'application/json',
            'Queue-Token': vars.queueToken,
        }
    });

    check(reservationRes, {
        '예약 성공': (res) => res.status === 200,
    });

    vars.reservationId = reservationRes.json()?.data?.id;

    // 예약 결제
    let paymentRes = http.post(`${host}/api/v1/reservations/pay`, JSON.stringify({
        reservationId: vars.reservationId,
        token: vars.queueToken,
    }), {
        headers: {
            'Content-Type': 'application/json',
            'Queue-Token': vars.queueToken,
        }
    });

    check(paymentRes, {
        '결제 성공': (res) => res.status === 200,
    });

    sleep(1); // 각 요청 간 간격 추가
}
