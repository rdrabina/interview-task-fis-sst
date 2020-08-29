# interview-task-fis-sst
### Swagger
https://automotive-store.herokuapp.com/swagger-ui.html#/

### Actuators
https://automotive-store.herokuapp.com/actuator


### Postgres management:
- CREATE DATABASE automotive_store;
- CREATE USER automotive_user WITH PASSWORD '$eCr3t';
- GRANT ALL PRIVILEGES ON DATABASE automotive_store TO automotive_user; 

### Heroku management
1. heroku create
2. heroku apps:rename --app random_already_generated_name your_name
3. git init
4. git add .
5. git commit -m "commit message"
6. git push heroku master
7. heroku logs -t

### Example of using the swagger
- /api/v1/brands/{name}
<br> available brand names: AUDI, BMW, VOLKSWAGEN
- /api/v1/car-parts/{id}
<br> car-parts id range: 44-63
- /api/v1/car-parts/{id}/availability
- /api/v1/car-parts/{id}/sales-arguments
- /api/v1/car-parts/{id}/service-actions
- /api/v1/car-parts/grouped-by-brand-and-model
- /api/v1/car-parts/grouped-by-brand-and-model/{car-part-name-description-filter}
<br> e.g. car, this
- /api/v1/service-actions/{start-date}/{end-date}
<br> start date and end date in format: yyyy-MM-dd, e.g. 2020-08-27