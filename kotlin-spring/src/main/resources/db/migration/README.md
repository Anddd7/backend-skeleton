- DB migration should be compatible with previous versions
  - Case: Migration will execute before application starts (health check alive).
  When you use rolling deployment, there might be 2 instances over a period of time.
  You users will access old instance with new db schema and get an unexpected error.
  - Solution: Make your migration is compatible with previous version.
  e.g if you want to delete a column in DB and related code, you can remove it from code first.
  Wait current version is stable, no one else is using, then drop it in next migration script.
 
 
# tips

### How to update properties in a jsonb array
Ref:[how-to-update-complex-jsonb-column](https://dba.stackexchange.com/questions/146683/how-to-update-complex-jsonb-column)

Case: insert a new item into json array, and update the order of others
```json
[{
  "id":1,
  "name": "name",
  "order": 1
}]
```
```postgresql
UPDATE target_table
SET target_column = (
  SELECT jsonb_agg(
    CASE WHEN VALUE ->> 'order' >= '3' 
           THEN jsonb_set(VALUE, '{order}', to_jsonb((VALUE ->> 'order')::INT + 1))
         ELSE VALUE
    END)
  FROM jsonb_array_elements(target_column)) || '{"id":6,"name":"new item","order":3}';
```