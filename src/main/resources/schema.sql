-- -----------------------------------------------------
-- DROP Table
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_point_history`;
DROP TABLE IF EXISTS `user_point`;

-- -----------------------------------------------------
-- Table `user_point`
-- -----------------------------------------------------
create table if not exists `user_point`
(
    id                bigint auto_increment primary key,
    created_at        datetime(6)  null,
    accumulated_point bigint       not null,
    modified_at       datetime(6)  null,
    user_id           varchar(255) not null,
    constraint unique_user_id
        unique (user_id)
);

-- -----------------------------------------------------
-- Table `user_point_history`
-- -----------------------------------------------------
create table if not exists `user_point_history`
(
    id                bigint auto_increment primary key,
    user_id           varchar(255) not null,
    event             bigint       not null,
    point_type        varchar(255) not null,
    point_type_detail varchar(255) null,
    created_at        datetime(6)  null,
    user_point        bigint       null,
    constraint fk_user_point_history_user_point
        foreign key (user_point) references user_point (id)
);

create index user_id
    on user_point_history (user_id);
