insert into
    user(user_role, created_at, updated_at, email, password,service_subscriber)
VALUES
    ('USER',now(),now(),'test001@gmail.com','password001!@#','RECOMMENDATION'),
    ('USER',now(),now(),'test002@gmail.com','password001!@#!','RECOMMENDATION'),
    ('USER',now(),now(),'test003@gmail.com','password001!@#!','RECOMMENDATION');

insert into
    category(category_type, name)
VALUES
    ('STANDARD','식비'),
    ('STANDARD','교통비'),
    ('STANDARD','경조사비'),
    ('STANDARD','기타'),
    ('STANDARD','문화비');

-- insert into
--     budget(date, category_id, user_id, value)
-- VALUES
--     ('2020-01-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-02-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-03-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-04-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-05-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-06-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-01-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-02-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-03-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-04-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-05-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
--     ('2020-06-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 );
insert into
    budget(date, category_id, user_id, value)
VALUES
    ('2020-01-01',1,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',2,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',3,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',4,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',5,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',1,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',2,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',3,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',4,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',5,1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',1,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',2,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',3,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',4,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',5,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',1,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',2,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',3,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',4,2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',5,2,FLOOR(1+ RAND() * 30)*1000 );

insert into
    expenditure(expended_date, category_id, user_id, value, exclude_spending_total, memo)
VALUES
    ('2020-01-01',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-02',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-03',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-04',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-05',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-06',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-07',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-08',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-09',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-01',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-02',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-03',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-04',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-05',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-06',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-07',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-08',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모'),
    ('2020-01-09',FLOOR(1 + RAND() * 5),1,FLOOR(1+ RAND() * 10)*1000,'INCLUDE','메모');