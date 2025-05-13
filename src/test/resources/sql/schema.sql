drop table if exists balance cascade;
drop table if exists concert cascade;
drop table if exists concert_schedule cascade;
drop table if exists payment cascade;
drop table if exists reservation cascade;
drop table if exists seat cascade;
drop table if exists users cascade;
drop table if exists outbox cascade;

create table balance
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    user_id    varchar(13)  not null comment '유저 ID',
    balance    decimal      not null comment '잔액',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점',
    constraint balance_unique_1
        unique (user_id)
)
    comment '잔고';

CREATE TABLE point (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   user_id BIGINT NOT NULL,
   balance INT NOT NULL,
   version BIGINT NOT NULL
)
    comment '포인트';

create table concert
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    title      varchar(255) not null comment '타이틀',
    price      decimal      not null comment '가격',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '콘서트';

create table concert_schedule
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    location   varchar(255) not null comment '장소',
    concert_id varchar(13)  not null comment '콘서트 ID',
    concert_at timestamp    not null comment '콘서트 시작 시점',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '콘서트 일정';

create index idx_concert_schedule_concert_id_at
    on concert_schedule (concert_id, concert_at);

create table payment
(
    id             varchar(13)  not null comment 'PK'
        primary key,
    user_id        varchar(13)  not null comment '유저 ID',
    reservation_id varchar(13)  not null comment '예약 ID',
    amount         decimal      not null comment '결제 금액',
    created_at     timestamp(6) not null comment '생성 시점',
    updated_at     timestamp(6) not null comment '마지막 수정 시점'
)
    comment '결제';

create table reservation
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    seat_id    varchar(13)  not null comment '좌석 ID',
    user_id    varchar(13)  not null comment '유저 ID',
    pay_amount varchar(255) not null comment '결제 금액',
    status     varchar(255) not null comment '예약 상태(PENDING: 대기 / COMPLETED: 완료 / CANCEL: 취소)',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점',
    constraint reservation_unique_1
        unique (seat_id)
)
    comment '예약';

create index idx_reservation_status
    on reservation (status);

create index idx_reservation_status_created_at
    on reservation (status, created_at);

create table seat
(
    id                  varchar(13)  not null comment 'PK'
        primary key,
    concert_schedule_id varchar(13)  not null comment '콘서트 일정 ID',
    number              int          not null comment '좌석번호',
    created_at          timestamp(6) not null comment '생성 시점',
    updated_at          timestamp(6) not null comment '마지막 수정 시점'
)
    comment '좌석';

create index idx_seat_concert_schedule_id
    on seat (concert_schedule_id);

create table users
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    username   varchar(255) not null comment '이름',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '유저';

create table outbox
(
    `id`              varchar(13)  not null comment 'PK'
        primary key,
    `idempotency_key` varchar(255) not null comment '멱등성을 보장하기 위한 키, 중복 처리를 방지',
    `topic`           varchar(255) not null comment '메시지가 발행될 토픽명',
    `event_key`       varchar(255) null comment 'Kafka 메시지의 Key (파티셔닝에 사용 가능)',
    `message`         mediumblob   not null comment '이벤트 메시지 본문 (직렬화된 데이터)',
    `status`          varchar(255) not null comment '이벤트 처리 상태 (예: PENDING, SENT, FAILED)',
    `created_at`      timestamp(6) not null comment '생성 시점',
    `updated_at`      timestamp(6) not null comment '마지막 수정 시점'
)
    comment '아웃박스';
