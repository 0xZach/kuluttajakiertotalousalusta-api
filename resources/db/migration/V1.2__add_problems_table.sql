CREATE TABLE PROBLEMS (
	id bigserial PRIMARY KEY,
	app_problem_id bigint unique,
	app_item_id bigint,
	app_category_id bigint,
	problem varchar(255),
	lang varchar(255),
	icon varchar(255),
	item_id bigint,
	created_at bigint,
	updated_at bigint,
	CONSTRAINT fk_item_id
	    FOREIGN KEY(item_id)
	    REFERENCES ITEMS(id)
);
