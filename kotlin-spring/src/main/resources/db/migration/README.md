- DB migration should be compatible with previous versions
  - Case: Migration will execute before application starts (health check alive).
  When you use rolling deployment, there might be 2 instances over a period of time.
  You users will access old instance with new db schema and get an unexpected error.
  - Solution: Make your migration is compatible with previous version.
  e.g if you want to delete a column in DB and related code, you can remove it from code first.
  Wait current version is stable, no one else is using, then drop it in next migration script.
 