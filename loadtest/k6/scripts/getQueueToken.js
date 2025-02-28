import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

// 메트릭 정의
export let errorRate = new Counter('errors');

export let options = {
    stages: [
        { duration: '20s', target: 100 }, // 20초 동안 100 VU까지 증가
        { duration: '20s', target: 250 }, // 20초 동안 250 VU까지 증가
        { duration: '20s', target: 500 }, // 20초 동안 500 VU까지 증가
        { duration: '1m', target: 500 },  // 1분 동안 500 VU 유지
        { duration: '20s', target: 250 }, // 20초 동안 250 VU로 감소
        { duration: '20s', target: 100 }, // 20초 동안 100 VU로 감소
    ],
    thresholds: {
        http_req_duration: ['p(95)<500'], // 95%의 요청이 500ms 이하
        http_req_failed: ['rate<0.01'],   // 오류율 1% 미만 유지
    },
};

let host = 'http://host.docker.internal:8080';

export default function () {
    const url = `${host}/api/v1/queue/token`;
    const userId = `user-${__VU}-${__ITER}`;
    const token = '92060722-72d5-41ea-b9c4-56f49bd20d3b';
    const params = {
        headers: {
            'Authorization': userId,
            'Queue-Token': token,
            'Content-Type': 'application/json',
        },
    };

    const res = http.get(url, params);

    const success = check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
    });

    if (!success) {
        errorRate.add(1);
    }

    // 요청 간격
    sleep(0.5);
}