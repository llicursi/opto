
insert into subject ( id, user_id, title, description, start, due, active) values (1, 1, 'Topic 1', '', DATEADD(DAY, -3, CURRENT_TIMESTAMP ), DATEADD(DAY, 4, CURRENT_TIMESTAMP ), true);
insert into subject ( id, user_id, title, description, start, due, active) values (2, 3, 'Topic 2 Inactive', '', DATEADD(DAY, -3, CURRENT_TIMESTAMP ), DATEADD(DAY, 4, CURRENT_TIMESTAMP ), false);
insert into subject ( id, user_id, title, description, start, due, active) values (3, 1, 'Topic 3 Expired', '', DATEADD(DAY, -15, CURRENT_TIMESTAMP ), DATEADD(DAY, -4, CURRENT_TIMESTAMP ), true);
