create table postal(
	id bigserial primary key,
	postal_code varchar(255),
	street_name varchar(255),
	municipality varchar(255),
	lang varchar(255),
	created_at bigint,
	updated_at bigint
);