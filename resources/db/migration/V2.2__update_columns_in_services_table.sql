ALTER TABLE SERVICES
DROP COLUMN service_type,
ADD COLUMN app_service_id bigint,
ADD COLUMN app_hri_id varchar(100),
ADD COLUMN app_business_id varchar(100),
ADD COLUMN app_service_type_id varchar(100),
ADD COLUMN service_type_name varchar(100);
