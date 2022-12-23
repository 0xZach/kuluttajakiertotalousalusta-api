CREATE TABLE SERVICES (
	id bigserial PRIMARY KEY,
	lang varchar(10),
	service_type varchar(255),
	name varchar(255),
	latitude float8,
	longitude float8,
	url varchar(255),
	address varchar(255),
	phone varchar(255),
	email varchar(255),
	details text,
	created_at bigint,
	updated_at bigint
);
