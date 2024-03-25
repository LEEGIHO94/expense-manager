DELIMITER //

CREATE PROCEDURE create_user_procedure()
BEGIN
        DECLARE outerVariable INT;

        SET outerVariable = 1;

        WHILE outerVariable <= 1000 DO
            INSERT INTO user(user_role, created_at, updated_at, email, password, service_subscriber)
            VALUES ('USER', NOW(), NOW(), CONCAT('test', outerVariable, '@gmail.com'), 'password001!@#', 'RECOMMENDATION');

            SET outerVariable = outerVariable + 1;
END WHILE;
END//

CREATE PROCEDURE create_budget(year VARCHAR(255))
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 1000 DO
        CALL loop_month(year,i);
        SET i = i + 1;
END WHILE;
END//

CREATE PROCEDURE loop_month(year VARCHAR(255),user int)
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 12 DO
        CALL loop_category(year, i,user);
        SET i = i + 1;
END WHILE;
END//

CREATE PROCEDURE loop_category(year VARCHAR(255), month VARCHAR(255),user int)
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 5 DO
        INSERT INTO budget(date, category_id, user_id, value)
        VALUES (CONCAT(year, '-', month, '-01'),i, user, ROUND((RAND() * (950000) + 50000) / 1000) * 1000);
        SET i = i + 1;
END WHILE;
END//

CREATE PROCEDURE create_expenditure(IN year VARCHAR(255))
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 1000 DO
        CALL loop_month_expenditure(year, i);
        SET i = i + 1;
END WHILE;
END//

CREATE PROCEDURE loop_month_expenditure(IN year VARCHAR(255), IN user INT)
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 12 DO
        CALL loop_day(year, i, user);
        SET i = i + 1;
END WHILE;
END//

CREATE PROCEDURE loop_day(IN year VARCHAR(255), IN month VARCHAR(255), IN user_id INT)
BEGIN
    DECLARE i INT;
    SET i = 1;

    IF month = '2' THEN
        WHILE i <= 28 DO
            CALL loop_expenditure(year, month, i, user_id);
            SET i = i + 1;
END WHILE;
ELSE
        WHILE i <= 30 DO
            CALL loop_expenditure(year, month, i, user_id);
            SET i = i + 1;
END WHILE;
END IF;
END//

CREATE PROCEDURE loop_expenditure(IN year VARCHAR(255), IN month VARCHAR(255), IN day VARCHAR(255), IN user INT)
BEGIN
    DECLARE i INT;
    SET i = 1;

    WHILE i <= 10 DO
        INSERT INTO expenditure(expended_date, category_id, user_id, value, exclude_spending_total, memo)
        VALUES (CONCAT(year, '-', month, '-', day), FLOOR(1 + RAND() * 5), user, FLOOR(1 + RAND() * 10) * 1000, 'INCLUDE', CONCAT('메모', i));
        SET i = i + 1;
END WHILE;
END//

DELIMITER ;


insert into
    category(category_type, name)
VALUES
    ('STANDARD','식비'),
    ('STANDARD','교통비'),
    ('STANDARD','경조사비'),
    ('STANDARD','기타'),
    ('STANDARD','문화비');

CALL create_user_procedure();
CALL create_budget('2020');
CALL create_expenditure('2020');
CALL create_expenditure('2021');
CALL create_expenditure('2021');
CALL create_expenditure('2022');
CALL create_expenditure('2022');
CALL create_expenditure('2022');
CALL create_expenditure('2022');
