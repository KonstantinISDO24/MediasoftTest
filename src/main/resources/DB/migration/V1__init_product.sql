create table product(
    id pg_catalog.uuid,
    name varchar(255) not null,
    description text not null,
    article varchar(255) unique not null,
    category varchar(255) not null,
    price numeric(10, 2) not null,
    quantity int not null,
    creation_date date not null,
    quantity_last_update_date timestamp not null,
    constraint pk_product primary key (id)
);