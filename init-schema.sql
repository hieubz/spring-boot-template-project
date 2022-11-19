create database demo;
use demo;
create table products
(
    id      bigint primary key auto_increment,
    name    varchar(255)                        not null,
    price   float                               not null,
    created timestamp default CURRENT_TIMESTAMP null
);

create table product_details
(
    id         bigint primary key auto_increment,
    product_id bigint                              not null,
    number     int                                 not null,
    author     varchar(255)                        null,
    image_path varchar(255)                        null,
    created    timestamp default CURRENT_TIMESTAMP null
);