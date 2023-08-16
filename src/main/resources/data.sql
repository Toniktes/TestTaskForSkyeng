DELETE FROM post_office;

ALTER TABLE post_office ALTER COLUMN id RESTART WITH 1;

MERGE INTO post_office KEY(id)
    VALUES (1, 184041, 'Отделение 1', 'адрес 1'),
    (2, 184042, 'Отделение 2', 'адрес 2'),
    (3, 184043, 'Отделение 3', 'адрес 3');