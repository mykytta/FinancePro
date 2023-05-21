create table if not exists users
(
    id bigint primary key AUTO_INCREMENT,
    email varchar(255) not null,
    password varchar(255),
    name varchar(255) not null,
    image_url varchar(255) not null,
    email_verified tinyint,
    provider varchar(45) not null,
    provider_id varchar(45) not null
);

create table if not exists stocks
(
    id bigint auto_increment primary key,
    stock_change double       null,
    company_name varchar(255) null,
    currency     varchar(255) null,
    price        double       null,
    symbol       varchar(255) null,
    volume       bigint       null
    );

create table if not exists user_stocks
(
    user_id  bigint,
    stock_id bigint,
    CONSTRAINT u_ID FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT s_ID FOREIGN KEY (stock_id) REFERENCES stocks (id)
    );