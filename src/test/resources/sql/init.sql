insert into
    user(user_role, created_at, updated_at, email, password)
VALUES
    ('USER',now(),now(),'test001@gmail.com','password001!@#'),
    ('USER',now(),now(),'test002@gmail.com','password001!@#!'),
    ('USER',now(),now(),'test003@gmail.com','password001!@#!');

insert into
    category(category_type, name)
VALUES
    ('STANDARD','식비'),
    ('STANDARD','교통비'),
    ('STANDARD','경조사비'),
    ('STANDARD','문화비');

insert into
    budget(date, category_id, user_id, value)
VALUES
    ('2020-01-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-03-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-04-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-05-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-06-01',FLOOR(1 + RAND() * 4),1,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-01-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-02-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-03-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-04-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-05-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 ),
    ('2020-06-01',FLOOR(1 + RAND() * 4),2,FLOOR(1+ RAND() * 30)*1000 );

