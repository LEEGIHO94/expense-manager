-- Schema

CREATE TABLE IF NOT EXISTS  budget (
                        date date,
                        budget_id bigint not null auto_increment,
                        category_id bigint,
                        user_id bigint,
                        value bigint,
                        primary key (budget_id)
);
CREATE TABLE IF NOT EXISTS category (
                             category_id bigint not null auto_increment,
                             total_budget_id bigint,
                             name varchar(255),
                             category_type enum ('CUSTOM','STANDARD'),
                             primary key (category_id)
);
CREATE TABLE IF NOT EXISTS expenditure (
                             expended_date date,
                             category_id bigint,
                             expenditure_id bigint not null auto_increment,
                             user_id bigint,
                             value bigint,
                             exclude_spending_total enum ('EXCLUDE','INCLUDE'),
                             memo varchar(255),
                             primary key (expenditure_id)
);
CREATE TABLE IF NOT EXISTS user (
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

create table total_budget (
                              total_budget bigint,
                              total_budget_id bigint not null auto_increment,
                              primary key (total_budget_id)
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


-- init DATA

-- USER
INSERT INTO user(user_role, created_at, updated_at, email, password, service_subscriber)
VALUES
    ('USER', NOW(), NOW(),  'test01@gmail.com', '{noop}password001!@#', 'RECOMMENDATION'),
     ('USER', NOW(), NOW(), 'test02@gmail.com', '{noop}password001!@#', 'RECOMMENDATION'),
     ('USER', NOW(), NOW(), 'test03@gmail.com', '{noop}password001!@#', 'RECOMMENDATION'),
     ('USER', NOW(), NOW(), 'test04@gmail.com', '{noop}password001!@#', 'RECOMMENDATION'),
     ('USER', NOW(), NOW(), 'test05@gmail.com', '{noop}password001!@#', 'RECOMMENDATION');

INSERT INTO
    total_budget(total_budget)
VALUES
    (1000000),
    (2000000),
    (3000000),
    (4000000);

-- CATEGORY
INSERT INTO
    category(category_type, name,total_budget_id)
VALUES
    ('STANDARD','식비',1),
    ('STANDARD','교통비',2),
    ('STANDARD','경조사비',3),
    ('STANDARD','기타',4),
    ('STANDARD','문화비',5);



-- Budget
INSERT INTO
    budget(date, category_id, user_id, value)
VALUES
    ('2020-01-01',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-01-01',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),

    ('2020-02-01',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-02-01',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),

    ('2020-03-01',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-03-01',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),

    ('2020-04-04',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-04-04',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),

    ('2020-05-05',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-05-05',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),

    ('2020-06-06',1, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',2, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',3, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',4, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',5, 1, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',1, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',2, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',3, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',4, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000),
    ('2020-06-06',5, 2, ROUND((RAND() * (950000) + 50000) / 1000) * 1000);

--Expenditure
INSERT INTO
    expenditure(expended_date, category_id, user_id, value, exclude_spending_total, memo)
VALUES
    ('2020-01-01', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-01', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-02', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-02', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-03', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-03', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-04', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-04', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-05', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-05', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-06', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-06', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-07', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-07', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-08', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-08', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-09', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-09', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-10', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-10', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-11', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-11', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-12', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-12', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),

    ('2020-01-13', 1, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 2, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 3, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 4, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 5, 1, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 1, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 2, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 3, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 4, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모'),
    ('2020-01-13', 5, 2, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', '메모');


