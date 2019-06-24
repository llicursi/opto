-- Users
drop table if exists user;
create table user(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(120) UNIQUE,
    name VARCHAR(120) NOT NULL,
    surname VARCHAR(120) NOT NULL,
    password VARCHAR(70) NOT NULL,
    role VARCHAR(30) DEFAULT 'USER'
);

-- Subject
drop table if exists subject;
create table subject(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(70),
    description VARCHAR(255),
    creation DATE NOT NULL,
    due DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- Vote
drop table if exists vote;
create table vote(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    agree BOOL NOT NULL,
    changes INT DEFAULT 1,
    creation DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (subject_id) REFERENCES subject(id)
);

-- Oauth required tables
drop table if exists oauth_client_details;
create table oauth_client_details(
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

drop table if exists oauth_client_token;
create table oauth_client_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

drop table if exists oauth_access_token;
create table oauth_access_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication BLOB,
  refresh_token VARCHAR(255)
);

drop table if exists oauth_refresh_token;
create table oauth_refresh_token (
  token_id VARCHAR(255),
  token BLOB,
  authentication BLOB
);

drop table if exists oauth_code;
create table oauth_code (
  code VARCHAR(255), authentication BLOB
);

drop table if exists oauth_approvals;
create table oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);
