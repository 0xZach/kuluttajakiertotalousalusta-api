# turku-amk-api

#### Requirements:
- Create DB, named `turku-amk`.
- Install PostGis.
- Enable PostGis extension in DB by running `CREATE EXTENSION postgis;`
- Run migrations.
- Add services table index for geom_point using `CREATE INDEX service_location_index ON public.services USING gist(geom_point);`.
- Add trigger to update geom_point column in services table using:
```
CREATE OR REPLACE FUNCTION public.update_geom_point_column()
RETURNS TRIGGER AS $BODY$
begin
    UPDATE services
    SET geom_point = ST_GeomFromText(CONCAT('POINT(', new.longitude, ' ', new.latitude, ')'),4326)
    WHERE id = new.id;
    RETURN new;
END
$BODY$ language 'plpgsql';

DROP TRIGGER IF EXISTS update_geom_point_column_update on public.services;
DROP TRIGGER IF EXISTS update_geom_point_column_insert on public.services;

create trigger update_geom_point_column_update
after update
on services
for each row
when (new.latitude is distinct from old.latitude or new.longitude is distinct from old.longitude)
execute procedure public.update_geom_point_column();

create trigger update_geom_point_column_insert
after insert
on services
for each row
execute procedure public.update_geom_point_column();
```
