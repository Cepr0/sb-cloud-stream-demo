create table orders (
  id         uuid       not null constraint orders_pkey primary key,
  version    integer,
  created_at timestamp  not null,
  product_id integer    not null,
  status     varchar(9) not null
);
