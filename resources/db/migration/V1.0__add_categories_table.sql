CREATE TABLE CATEGORIES (
	id bigserial PRIMARY KEY,
	app_category_id bigint unique,
	name varchar(255),
	lang varchar(255),
	icon varchar(255),
	created_at bigint,
	updated_at bigint
);
