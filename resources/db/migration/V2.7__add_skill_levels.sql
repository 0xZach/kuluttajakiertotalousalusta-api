create table skill_level (
	id bigserial primary key,
	min_skill_en varchar(255),
	min_skill_fi varchar(255),
	created_at bigint,
	updated_at bigint
);

alter table results add constraint FK_skill foreign key (app_skill_level_id) references skill_level(id);