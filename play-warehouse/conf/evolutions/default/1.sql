# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id                            bigint not null,
  ean                           varchar(255),
  name                          varchar(255),
  description                   varchar(255),
  picture                       varbinary(255),
  constraint pk_product primary key (id)
);
create sequence product_seq;

create table stock_item (
  id                            bigint not null,
  product_id                    bigint,
  quantity                      integer,
  constraint pk_stock_item primary key (id)
);
create sequence stock_item_seq;

alter table stock_item add constraint fk_stock_item_product_id foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_stock_item_product_id on stock_item (product_id);


# --- !Downs

alter table stock_item drop constraint if exists fk_stock_item_product_id;
drop index if exists ix_stock_item_product_id;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists stock_item;
drop sequence if exists stock_item_seq;

