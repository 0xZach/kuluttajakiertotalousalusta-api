CREATE TABLE logs(
	id bigserial PRIMARY KEY,
  	logsTime varchar(255) NOT NULL,
  	keywordEn varchar(255) NOT NULL,
  	keywordFi varchar(255) NOT NULL,
  	destinationUrl varchar(255) NOT NULL,
  	serviceName varchar(255) NOT NULL,
  	serviceTypeName varchar(255) NOT NULL,
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