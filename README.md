# club-mileage

### 실행 방법

##### Repository Clone

```
git clone https://github.com/malaheaven/club-mileage.git
```

##### Docker

docker가 설치되어 있는 경우 사용 가능. docker를 설치해 주세요.

docker 설치: [Download Docker](https://www.docker.com/products/docker-desktop/)

아래 쉘 파일 run

```
sh start.sh
```

```bash
# start.sh 내용

rm -rf ./docker/db/  # remove docker volume directory
./gradlew clean      # spring boot build clean
./gradlew bootJar    # spring boot bootJar

cd docker/           # move docker directory

#sh docker-clear.sh  # docker 현재 실행하는 컨테이너 제거 및 docker-compose.yml 정의 파일에서 지정한 컨테이너를 정지시키고 모든 이미지를 삭제 (필요시 주석 해제 후 사용)
sh docker-start.sh   # docker run
```

health_check

```curl
curl --location --request GET 'http://localhost:8080/health'
```

참고 사항

- Spring Boot 가 계속 재실행 되는 구간이 있다. 10번 이상 지속되지 않으니 기다리면 Spring Boot가 정상 동작한다.

---

### version

|kind|name|version|etc |
|-----|----|-------|----|
|DBMS|MySQL|8.0||
|NOSQL|MongoDB|4.0|AWS DocumentDB (MongoDB 4.0 버전을 따름)|
|Language|JAVA|11||
|Framework|Spring Boot |2.7.1||

---

### 개발 문서

- TimeZone : UTC
```
  @PostConstruct
    public void construct() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
```

개발 문서는 Repository 내 wiki 탭에 기재되어 있습니다.
[wiki 바로가기](https://github.com/malaheaven/club-mileage/wiki)

- 요구사항
- ERD
- API 문서

#### Spring Rest Docs 적용

|환경|url|ETC|
|---|----|---|
|Docker|http://localhost/docs/index.html| Docker로 실행하면 해당 URL|
|Local|http://localhost:8081/docs/index.html||

---

### 개발 방식

#### 개발하면서 중요하게 여기고 싶은 항목

1. 기능 위주로 개발하기.
2. 먼저 이슈를 생성하고 이슈에 대한 기능 개발을 해야하나, 이를 생략하고 기능 위주인 PR로 개발 내용 및 history 관리

##### Branch 규칙

- feature : 새로운 기능 개발이나 버그 수정을 위한 브랜치
- develop : 새로운 기능(feature)에 대한 검토(PR)가 완료되면 소스코드를 합치기 위한 브랜치
- main : 정식 배포되는 안정적인 소스코드를 이곳에서 관리

##### Commit 규칙

|Name     |Description|
|---------|-----------|
|ADD      |코드 추가|
|MOD      |코드 수정, 버그 수정 (Modify)|
|DOC      |문서(Docs)|
|STY      |코드 줄 맞춤, 오탈자 수정 (Style)|
