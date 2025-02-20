-- 높은 재귀(반복) 횟수를 허용하도록 설정
SET SESSION cte_max_recursion_depth = 10000000;

-- payment 테이블
INSERT INTO payment (id, user_id, reservation_id, amount, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS user_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS reservation_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CAST(1000 + (RAND() * (100000 - 1000)) AS UNSIGNED) AS amount, -- 1000부터 100000 사이의 랜덤 정수를 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

-- reservation 테이블
INSERT INTO reservation (id, seat_id, user_id, pay_amount, status, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS seat_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS user_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CAST(1000 + (RAND() * (100000 - 1000)) AS UNSIGNED) AS pay_amount, -- 1000부터 100000 사이의 랜덤 정수를 생성
    ELT(FLOOR(RAND() * 3) + 1, 'PENDING', 'COMPLETED', 'CANCEL') AS status, -- 'PENDING', 'COMPLETED', 'CANCEL' 중 랜덤 값 선택
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

-- balance 테이블
INSERT INTO balance (id, user_id, balance, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS user_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CAST(1000 + (RAND() * (100000 - 1000)) AS UNSIGNED) AS balance, -- 1000부터 100000 사이의 랜덤 정수를 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

-- seat 테이블
INSERT INTO seat (id, concert_schedule_id, number, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS concert_schedule_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    MOD(n, 50) AS number, -- n(반복횟수)를 50으로 나눈 나머지 값
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

-- concert_schedule 테이블
INSERT INTO concert_schedule (id, location, concert_id, concert_at, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('Location', LPAD(n, 8, '0')) AS location, -- 'Location' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('ID', LPAD(n, 8, '0')) AS concert_id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) + FLOOR(RAND() * 2 * 31536000) - 31536000) AS concert_at, -- 과거 1년 ~ 미래 1년 사이의 랜덤한 날짜 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

-- concert 테이블
INSERT INTO concert (id, title, price, created_at, updated_at)
WITH RECURSIVE cte (n) AS
   (
       SELECT 1
       UNION ALL
       SELECT n + 1 FROM cte WHERE n < 10000000 -- 생성하고 싶은 더미 데이터의 개수
   )
SELECT
    CONCAT('ID', LPAD(n, 8, '0')) AS id, -- 'ID' 다음에 8자리 숫자로 구성된 문자열 생성
    CONCAT('Title', LPAD(n, 8, '0')) AS title, -- 'Title' 다음에 8자리 숫자로 구성된 문자열 생성
    CAST(1000 + (RAND() * (100000 - 1000)) AS UNSIGNED) AS price, -- 1000부터 100000 사이의 랜덤 정수를 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS created_at, -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
    FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - FLOOR(RAND() * 31536000)) AS updated_at -- 현재 시간에서 최대 1년(31,536,000초) 내의 랜덤한 과거 시점을 생성
FROM cte;

