create table budget (
                        date date,
                        budget_id bigint not null auto_increment,
                        category_id bigint,
                        user_id bigint,
                        value bigint,
                        primary key (budget_id)
);
create table category (
                          category_id bigint not null auto_increment,
                          category_type enum ('CUSTOM','STANDARD'),
                          name varchar(255),
                          primary key (category_id)
);
create table expenditure (
                             expended_date date,
                             category_id bigint,
                             expenditure_id bigint not null auto_increment,
                             user_id bigint,
                             value bigint,
                             exclude_spending_total enum ('EXCLUDE','INCLUDE'),
                             memo varchar(255),
                             primary key (expenditure_id)
);
create table user (
                      created_at datetime(6),
                      updated_at datetime(6),
                      user_id bigint not null auto_increment,
                      email varchar(255),
                      password varchar(255),
                      service_subscriber enum ('EVALUATION','RECOMMENDATION'),
                      user_role enum ('USER'),
                      value varchar(255),
                      primary key (user_id)
);
alter table budget
    add constraint FKrme3v2iww4j9qkxv4f93vv4mt
        foreign key (category_id)
            references category (category_id);
alter table budget
    add constraint FKkuh8cj1roovp9nh6ut2igrxm2
        foreign key (user_id)
            references user (user_id);
alter table expenditure
    add constraint FK61mdnsq3ik9y3mj7wot1pctxh
        foreign key (category_id)
            references category (category_id);
alter table expenditure
    add constraint FKfuskxwxlc0fofci8s9xc3ksm5
        foreign key (user_id)
            references user (user_id);