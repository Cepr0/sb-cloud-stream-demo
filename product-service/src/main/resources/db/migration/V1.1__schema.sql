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

