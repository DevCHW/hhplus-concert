-- 모든 테이블 삭제 쿼리 생성
set @tables = NULL;
select group_concat('`', table_name, '`') into @tables
from information_schema.tables
where table_schema = (select DATABASE());

-- 삭제 쿼리 실행
set @sql = CONCAT('DROP TABLE IF EXISTS ', @tables);
prepare stmt from @sql;
execute stmt;
deallocate prepare stmt;

-- 테이블 생성
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

create table users
(
    id         varchar(13)  not null comment 'PK'
        primary key,
    created_at timestamp(6) not null comment '생성 시점',
    updated_at timestamp(6) not null comment '마지막 수정 시점'
)
    comment '유저';

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
