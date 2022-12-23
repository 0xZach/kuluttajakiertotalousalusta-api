CREATE TABLE PROBLEM_SERVICES (
	id bigserial PRIMARY KEY,
	app_problem_id bigint,
    app_item_id bigint,
    app_category_id bigint,
	service_id bigint,
	problem_id bigint,
	item_id bigint,
	created_at bigint,
	updated_at bigint,
	CONSTRAINT fk_problem_id
        FOREIGN KEY(problem_id)
        REFERENCES PROBLEMS(id),
    CONSTRAINT fk_service_id
        FOREIGN KEY(service_id)
        REFERENCES SERVICES(id),
    CONSTRAINT fk_item_id
        FOREIGN KEY(item_id)
        REFERENCES ITEMS(id)
);
