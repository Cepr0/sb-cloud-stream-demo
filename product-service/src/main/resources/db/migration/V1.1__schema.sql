create table products (
  id      integer not null constraint products_pkey primary key,
  version integer,
  name    varchar(255)
);

create table product_amounts (
  product_id integer not null constraint product_amounts_pkey primary key,
  version    integer,
  amount     integer not null,
  constraint fk_product_amounts_product_id foreign key (product_id) references products (id)
);

create table product_orders (
  product_id integer not null constraint fk_product_orders_product_id references products(id),
  order_id   bigint  not null constraint product_orders_pkey primary key
)

