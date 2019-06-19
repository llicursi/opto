-- Create 4 user permission

insert into users (user_id, name, surname, email, password, role) values (99991, 'User', 'User', 'user@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER');
insert into users (user_id, name, surname, email, password, role) values (99992, 'Admin', 'User', 'admin@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'ADMIN');
insert into users (user_id, name, surname, email, password, role) values (99993, 'Manager', 'User', 'manager@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'MANAGER');
insert into users (user_id, name, surname, email, password, role) values (99994, 'Viewer', 'User', 'viewer@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'VIEWER');
