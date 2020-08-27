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