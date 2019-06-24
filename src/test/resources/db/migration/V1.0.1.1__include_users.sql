insert into user (id,  name, surname, email, password, role)
values (1, 'User', 'I', 'user@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (2, 'Admin', 'II', 'admin@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'ADMIN') ;

insert into user (id,  name, surname, email, password, role)
values (3, 'Dimitri', 'Ursad', 'dimitri@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id, name, surname, email, password, role)
values (4, 'Ihana', 'Mar', 'ihana@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id, name, surname, email, password, role)
values (5, 'Oleks', 'Anders', 'oleks@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (6, 'Marek', 'Zuadi', 'marek@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (7, 'Pinus', 'Mole', 'pinus@opto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into subject ( user_id, title, description, start, due, active) values ( 1, 'Topic 1', '', DATEADD(DAY, -3, CURRENT_TIMESTAMP ), DATEADD(DAY, 4, CURRENT_TIMESTAMP ), true);
insert into subject ( user_id, title, description, start, due, active) values ( 3, 'Topic 2 Inactive', '', DATEADD(DAY, -3, CURRENT_TIMESTAMP ), DATEADD(DAY, 4, CURRENT_TIMESTAMP ), false);
insert into subject ( user_id, title, description, start, due, active) values ( 1, 'Topic 3 Expired', '', DATEADD(DAY, -15, CURRENT_TIMESTAMP ), DATEADD(DAY, -4, CURRENT_TIMESTAMP ), true);

-- drop schema opto;
-- create schema opto;

-- Select * from subject
-- Select * from user;