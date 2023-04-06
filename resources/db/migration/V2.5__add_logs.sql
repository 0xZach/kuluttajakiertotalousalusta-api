CREATE TABLE logs(
	id bigserial PRIMARY KEY,
  	log_timestamp varchar(255) NOT NULL,
  	keyword_en varchar(255) NOT NULL,
  	keyword_fi varchar(255) NOT NULL,
  	destination_url varchar(255) NOT NULL,
  	service_name varchar(255) NOT NULL,
  	service_type_name varchar(255) NOT NULL,
  	created_at bigint,
    updated_at bigint
);

--drop table logs;

-- example --
--insert into logs (logTime, resultPageUrl, destinationUrl, serviceName, serviceTypeName) values (
--	'2023-01-30',
--	'http://localhost:3000/en-GB/results?problemId=5&keywordEn=ho…en+korjata+reik%C3%A4+housut+ohje+Vaatteet+Housut+Reik%C3%A4',
--	'https://kilonompelimo.fi',
--	'Kilon ompelimo',
--	'Dressmaker'
--);

--select * from logs;