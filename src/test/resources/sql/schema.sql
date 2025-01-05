drop table if exists balance;
drop table if exists concert;
drop table if exists concert_schedule;
drop table if exists payment;
drop table if exists reservation;
drop table if exists seat;
drop table if exists user;
drop table if exists waiting_queue;

create table balance
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    user_id    varchar(13)  not null comment '유저 ID',
    balance    decimal      not null comment '잔액',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '잔고';

create table concert
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    title      varchar(255) not null comment '타이틀',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '콘서트';

create table concert_schedule
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    concert_id varchar(13)  not null comment '콘서트 ID',
    concert_at timestamp    not null comment '콘서트 시작',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '콘서트 일정';

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
    id                  varchar(13)  not null comment 'PK'
        primary key,
    concert_schedule_id varchar(13)  not null comment '콘서트 일정 ID',
    seat_id             varchar(13)  not null comment '좌석 ID',
    user_id             varchar(13)  not null comment '유저 ID',
    status              varchar(255) not null comment 'PENDING / COMPLETED / CANCEL',
    created_at          timestamp(6) not null comment '생성 시점',
    updated_at          timestamp(6) not null comment '마지막 수정 시점'
)
    comment '예약';

create table seat
(
    id                  varchar(13)  not null comment 'PK'
        primary key,
    concert_schedule_id varchar(13)  not null comment 'PK',
    location            varchar(255) not null comment '장소',
    number              int          not null comment '좌석번호',
    created_at          timestamp(6) not null comment '생성 시점',
    updated_at          timestamp(6) not null comment '마지막 수정 시점'
)
    comment '좌석';

create table user
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '유저';

create table waiting_queue
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    user_id    varchar(13)  not null comment 'PK',
    token      varchar(255) not null comment '토큰 (UUID)',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점',
    constraint waiting_queue_pk
        unique (token),
    constraint waiting_queue_pk_2
        unique (user_id)
)
    comment '대기열';
