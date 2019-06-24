insert into user (id,  name, surname, email, password, role)
values (1, 'User', 'I', 'user@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (2, 'Admin', 'II', 'admin@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'ADMIN') ;

insert into user (id,  name, surname, email, password, role)
values (3, 'Dimitri', 'Ursad', 'dimitri@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id, name, surname, email, password, role)
values (4, 'Ihana', 'Mar', 'ihana@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id, name, surname, email, password, role)
values (5, 'Oleks', 'Anders', 'oleks@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (6, 'Marek', 'Zuadi', 'marek@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into user (id,  name, surname, email, password, role)
values (7, 'Pinus', 'Mole', 'pinus@okto.com', '$2a$06$2qeXm6qDiaLtHN26Blt.9.5Waeo9HBQSFrYrrS7/MHiQPrnxT/Bci', 'USER') ;

insert into subject ( user_id, title, description, creation, due) values ( 4, 'Topic 1', '', DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -3 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 4 DAY));
insert into subject ( user_id, title, description, creation, due) values ( 3, 'Topic 2', '', DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 DAY), DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 10 DAY));

-- drop schema opto;
-- create schema opto;

-- Select * from subject
-- Select * from user;