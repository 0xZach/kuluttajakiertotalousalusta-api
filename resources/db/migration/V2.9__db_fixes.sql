drop table Logs;
create table Logs(
	id bigserial primary key,
	log_timestamp text,
	service_or_tuto text,
	result_id int,
	created_at bigint,
	updated_at bigint
);

alter table problems
	drop column app_category_id;

alter table results
	drop column app_item_id,
	drop column app_category_id,
	drop column app_problem_id,
	drop column item_id,
	drop column min_skill,
	drop column category_id,
	drop column content_type,
	drop column app_result_id;

alter table services
	drop column service_type_name,
	drop column app_service_id;


drop table problem_services;
create table problem_services (
    id bigserial primary key,
	problem_id int,
	service_id int,
	created_at bigint,
	updated_at bigint,
	foreign key (problem_id) references problems(id),
	foreign key (service_id) references services(id)
);

create table service_types (
	id bigserial primary key,
	type_name text,
	lang text,
	created_at bigint,
	updated_at bigint
);

alter table skill_level
    rename column min_skill_en to label,
    rename column min_skill_fi to lang;

alter table content_type
    rename column content_type_en to label,
    rename column content_type_fi to lang;