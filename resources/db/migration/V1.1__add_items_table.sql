CREATE TABLE ITEMS (
	id bigserial PRIMARY KEY,
	app_item_id bigint unique,
	app_category_id bigint,
	name varchar(255),
	lang varchar(255),
	icon varchar(255),
	category_id bigint,
	created_at bigint,
	updated_at bigint,
	CONSTRAINT fk_category_id
        FOREIGN KEY(category_id)
        REFERENCES CATEGORIES(id)
);
