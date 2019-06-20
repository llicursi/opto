-- Create 1 user permission

insert into users (id, name, surname, email, password, role)
SELECT * FROM ( SELECT 2, 'Admin', '', 'admin', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'ADMIN') as tmp
WHERE NOT EXISTS (
    SELECT name FROM users WHERE user_id=2
) LIMIT 1;
