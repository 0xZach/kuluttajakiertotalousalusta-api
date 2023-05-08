drop table Logs;
create table Logs(
	id bigserial primary key,
	log_timestamp date,
	service_or_tuto text,
	result_id int,
	created_at int,
	updated_at int
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
	problem_id int,
	service_id int,
	created_at int,
	updated_at int,
	primary key (problem_id, service_id),
	foreign key (problem_id) references problems(id),
	foreign key (service_id) references services(id)
);

create table service_types (
	id bigserial primary key,
	type_name text,
	lang text,
	created_at int,
	updated_at int
);