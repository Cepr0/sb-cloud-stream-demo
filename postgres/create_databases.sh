#!/usr/bin/env bash

databases=(
	order_service
	product_service
)

for db in ${databases[*]} ; do
	echo "select 'create database ${db}' where not exists (select from pg_database where datname = '${db}')\gexec" | psql
done

