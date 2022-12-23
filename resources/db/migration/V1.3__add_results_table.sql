CREATE TABLE RESULTS (
	id bigserial PRIMARY KEY,
	app_problem_id bigint,
	app_item_id bigint,
	app_category_id bigint,
	lang varchar(255),
	service_type varchar(255),
	tutorial_name varchar(255),
    tutorial_intro varchar(255),
	tutorial_url varchar(255),
	min_cost varchar(255),
	min_skill varchar(255),
	min_time varchar(255),
	problem_id bigint,
	item_id bigint,
	category_id bigint,
	created_at bigint,
	updated_at bigint,
	CONSTRAINT fk_problem_id
        FOREIGN KEY(problem_id)
        REFERENCES PROBLEMS(id),
    CONSTRAINT fk_item_id
        FOREIGN KEY(item_id)
        REFERENCES ITEMS(id),
	CONSTRAINT fk_category_id
        FOREIGN KEY(category_id)
        REFERENCES CATEGORIES(id)
);
