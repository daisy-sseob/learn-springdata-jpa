# README

> spring data jpa를 학습한다.

## Docker cli 참고하기
- postgres image 받기 및 실행 
```
$ docker run -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_USER=sseob -e POSTGRES_DB=springdata --name postgres_boot -d postgres
```

- postgres bash 진입
```
$ docker exec -i -t postgres_boot bash
```

- springdata 접속
```
$ psql --username=sseob --dbname=springdata
```