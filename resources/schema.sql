
    create table user_point (
       id bigint not null auto_increment,
        created_at datetime(6),
        accumulated_point bigint not null,
        modified_at datetime(6),
        user_id varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_point_history (
       id bigint not null auto_increment,
        created_at datetime(6),
        event bigint not null,
        point_type varchar(255),
        point_type_detail varchar(255) not null,
        user_id varchar(255),
        user_point bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table user_point 
       add constraint UK_c30japbjwomtd7t6mb3lgxve6 unique (user_id);

    alter table user_point_history 
       add constraint UK_r4otgv1wkku7grt74rwc9tlmg unique (user_id);

    alter table user_point_history 
       add constraint FK3byxjredq58ly5lijv3uvvlh4 
       foreign key (user_point) 
       references user_point (id);

    create table user_point (
       id bigint not null auto_increment,
        created_at datetime(6),
        accumulated_point bigint not null,
        modified_at datetime(6),
        user_id varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table user_point_history (
       id bigint not null auto_increment,
        created_at datetime(6),
        event bigint not null,
        point_type varchar(255),
        point_type_detail varchar(255) not null,
        user_id varchar(255),
        user_point bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table user_point 
       add constraint UK_c30japbjwomtd7t6mb3lgxve6 unique (user_id);

    alter table user_point_history 
       add constraint UK_r4otgv1wkku7grt74rwc9tlmg unique (user_id);

    alter table user_point_history 
       add constraint FK3byxjredq58ly5lijv3uvvlh4 
       foreign key (user_point) 
       references user_point (id);
