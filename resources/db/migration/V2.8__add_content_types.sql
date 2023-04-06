create table content_type (
	id bigserial primary key,
	content_type_en varchar(255),
	content_type_fi varchar(255),
	created_at bigint,
    updated_at bigint
);

alter table results add constraint FK_content_type foreign key (app_category_id) references content_type(id);