import http from 'k6/http';
import {check, sleep} from 'k6';

export const options = {
    stages: [
        { duration: '20s', target: 10}, // 20초 동안 20명까지 증가
    ]
};

export default function() {
    const url = "http://host.docker.internal:8080/health"
    const res = http.get(url)

    // 검증
    check(res, {
        'status is 200': (r) => r.status === 200,
        'response time < 500ms': (r) => r.timings.duration < 500,
    })

    sleep(1)
}
