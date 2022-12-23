CREATE TABLE MOTIVES (
	id bigserial PRIMARY KEY,
	motive varchar(255),
	motive_category varchar(255),
	hits bigint,
	created_at bigint,
	updated_at bigint
);
