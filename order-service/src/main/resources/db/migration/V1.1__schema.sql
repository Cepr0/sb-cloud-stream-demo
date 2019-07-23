create sequence global_sequence increment 50;

create table orders (
  id         bigint     not null constraint orders_pkey primary key,
  version    integer,
  created_at timestamp  not null,
  product_id integer    not null,
  status     varchar(9) not null,
  reason     varchar(17)
);
