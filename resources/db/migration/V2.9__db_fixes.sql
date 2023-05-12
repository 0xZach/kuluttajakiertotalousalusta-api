-- Sylvain 12/05/23 --

-- made big mistakes when first making the logs table, here i revamped it so that we can have the most important infos --
-- also made it so that we have a field that makes the distinction between services and tutorials, may need to find something better --

drop table Logs;
create table Logs(
	id bigserial primary key,
	log_timestamp text,
	service_or_tuto text,
	result_id bigint,
	created_at bigint,
	updated_at bigint
);


-- dropped duplicated columns and app_id columns to prepare for the language amelioration which will actually let us use the ids used in the google sheets --

alter table problems
	drop column app_category_id,
	drop column app_item_id;

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
	drop column app_service_id,
	alter column app_service_type_id TYPE bigint using app_service_type_id::bigint;


-- remade the problem_services table to only keep the most useful infos --
-- TO DO: find out how to use a concatenated key so that instead of giving another field we can just use problem_id + service_id as the Primary Key --

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


-- implementing the service types with the downside of having to use an "app_id", explanation below with content type and skill levels

create table service_types (
	id bigserial primary key,
	type_name text,
	lang text,
	app_id bigint,
	created_at bigint,
	updated_at bigint
);



-- i made a mistake and copied how the Payload works --

alter table skill_level
    rename column min_skill_en to label;

alter table skill_level
    rename column min_skill_fi to lang;


alter table content_type
    rename column content_type_en to label;

alter table content_type
    rename column content_type_fi to lang;


-- adding as its own column the real id (the one used in the google sheets) so that we can link tables with it while also having the lang field useable --


alter table content_type
    add column app_id bigint;

alter table skill_level
    add column app_id bigint;