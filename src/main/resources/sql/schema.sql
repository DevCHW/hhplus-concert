drop table if exists balance cascade;
drop table if exists concert cascade;
drop table if exists concert_schedule cascade;
drop table if exists payment cascade;
drop table if exists reservation cascade;
drop table if exists seat cascade;
drop table if exists token cascade;
drop table if exists users cascade;

-- 테이블 생성
create table balance
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    user_id    varchar(13)  not null comment '유저 ID',
    balance    decimal      not null comment '잔액',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점',
    constraint balance_unique
        unique (user_id)
)
    comment '잔고';

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
    constraint reservation_unique
        unique (seat_id)
)
    comment '예약';

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

create table token
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    user_id    varchar(13)  not null comment '유저 ID',
    token      varchar(255) not null comment '토큰 (UUID)',
    status     varchar(255) not null comment '토큰 상태 (CREATED: 생성 / ACTIVE: 활성)',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점',
    constraint token_unique
        unique (token),
    constraint token_unique_2
        unique (user_id)
)
    comment '토큰';

create table users
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    username   varchar(255) not null comment '이름',
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '유저';