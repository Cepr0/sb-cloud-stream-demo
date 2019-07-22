#!/usr/bin/env bash

export PGUSER=postgres

databases=(
	order_service
	product_service
)

for db in ${databases[*]} ; do
#	createdb -U postgres ${db}
	echo "select 'create database ${db};' where not exists (select from pg_database where datname = '${db}')\gexec" | psql
#	echo "grant all privileges on database ${db} to ${PGUSER};\gexec" | psql
done

