create sequence if not exists orders_id_seq start 1 increment 1;
create sequence if not exists order_line_items_id_seq start 1 increment 1;

create table order_line_items
(
    id       bigint primary key default nextval('order_line_items_id_seq'),
    sku_code varchar not null,
    price    decimal not null,
    quantity int     not null
);

create table orders
(
    id              bigint primary key default nextval('orders_id_seq'),
    order_number    varchar not null unique,
    order_line_item bigint  references order_line_items (id)
);