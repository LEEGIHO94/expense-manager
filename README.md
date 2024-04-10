# expense-manager

<img width="726" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/ea89e638-4950-4293-8939-1aa6da16d48e">

지출 내역 기록 및 관리, 분석을 통해 사용자의 절약 습관에 도움을 주는 서비스 백엔드 API

위 서비스는 사용자들이 예산을 설정하고 지출을 모니터링하며 재무 목표를 달성하는 데 도움이 됩니다.


## 목차
1. [요구사항](#요구사항-및-분석-및-회고-링크)
2. [DB Table](#db-table)
3. [API 문서](#api-문서)
4. [실행 방법](#실행-방법)
    - [Docker Compose 사용](#1-docker-compose-사용)
    - [Docker 미사용 배포](#2-docker-미사용-서버-실행)
5. [적용 기술 및 성능 개선](#적용-기술-및--성능-개선)
    - [Indexing을 통한 조회 성능 4배 개선](#indexing을-통한-조회-성능-4배-개선)
    - [DB 성능 튜닝을 통한 성능 개선 & Cache 적용](#db-성능-튜닝을-통한-성능-개선--cache-적용)
    - [Cache 통한 서비스 개선](#cache-통한-서비스-개선-부하-테스트-시-성능-최대-2배-개선)
    - [Spring Security 활용한 인증 & 인가](#spring-security를-활용한-인증--인가)
7. [TEST](#test)
    - [TestCoverage 90% 달성](#100여개의-테스트-진행을-통한-test-coverage-90-달성)
    - [Test 병렬 실행으로 인한 실행 시간 단축](#test-병렬-실행으로-인한-test-시간-단축-1m18s---46s-32s개선)
    - [TestContainer 적용](#testcontainer-적용)
8. [배포](#배포)
    - [Docker Container 활용](#docker-container-활용을-통한-배포-안정성-확립)
    - [Docker Compose 활용](#docker-compose-활용을-통한-여러-컨테이너-제어)
9. [협업](#협업)
    - [Github Issue 및 칸반 보드를 통한 일정 관리](#github-issue--칸반-보드를-통한-일정-관리)
    - [GitHook 활용을 통한 commit 제한](#githook을-활용한-commit-제한)
    - [코드 스타일 통일](#코드-스타일-통일)

---

## 프로젝트 작업 기간
> 1차 완성 : 2023. 11. 13 ~ 2023. 12. 25
>   - 요구사항 구현
>
> 2차 완성 : 2024. 02. 01 ~ 2024. 04. 02
>   - 성능 개선 (캐싱, db 구조 변경)
>   - 테스트 (커버리지 90% 달성)
>   - 배포 방식 수정(docker 활용)
>   - lint 통일


---

## 요구사항 및 분석 및 회고 링크
> 요구사항 분석 및 회고, 트러블 슈팅 등 프로젝트 전반에 걸친 분석, 학습 및 트러블 슈팅 정리
>- [요구사항 링크](https://bow-hair-db3.notion.site/90cba97a58a843e4a2563a226db3d5b5)
>- [요구사항 분석_링크](https://funky-reward-c30.notion.site/API-eec9a9c8090643f1b446a4ec565fbf68?pvs=4)

---

## DB Table
[DB 다이어그램 링크](https://www.erdcloud.com/d/W6hzt6WeCXKfjQF9Z)

<img width="1235" alt="image" src="https://github.com/LEEGIHO94/expense-manager/assets/116015708/14630cc5-7fd1-448e-bb8c-443be2e458a5">

---

## API 문서
> 서버 기동 이후 http://localhost:8080/index.html 에서 확인 가능

---

## 실행 방법

### 1. Docker-Compose 사용

#### 서버 실행

```shell
./script/buildNDocker.sh
```
- DBMS,REDIS 따로 구현할 피료 없이 스크립트로 docker-compose 를 통한 서버 기동
- 기동된 서버는 백그라운드에서 실행

#### 서버 종료
```shell
docker-compose down
```

### 2. Docker 미사용 서버 실행

#### 서버 실행

```shell
./script/build.sh
```

- 이미 가지고 있는 DBMS, REDIS가 존재할 경우 실행 하는 스크립트
- 백그라운드에서 서버가 기동됨

##### 설정된 파라미터
|      변수      |     값      |
|:------------:|:----------:|
|   database   |  expense   |
|   username   |    back    |
|   password   | back00112! |
|   db_port    |    3306    |
|  redis_port  |    6379    |


- **추 후 스크립트 실행 시 데이터 정보를 받는 형식으로 변경 예정**

#### 싫행 종료
```shell
kill -9 $(pgrep -f "java.*expense")
```
- 백그라운드에서 실행 중인 java 서버 종료

---

## 적용 기술 및  성능 개선

### 부하테스트를 통항 성능 개선 지표 확보


### Indexing을 통한 조회 성능 4배 개선
- 부하 테스트 중 connection 이 부족해지는 현상 발생 -> connection pool이 부족해지기 때문에 동시 접속자가 많아질 경우 시간 지연 발생
- HW를 늘리거나 connection-pool을 늘리는 방법을 통해 해결이 가능하나 진행 했던 부하테스트는 최대 부하에서 1시간동안 유지되는지 체크하기위한 것
    - 항상 최대 부하를 발생 시키는 것이 아니기 때문에 HW를 늘리거나 connection-pool을 늘리는 것은 비효율적
=> 각 서비스가 SQL에서 데이터를 가져오는게 걸리는 시간의 단축을 통한 성능 개선 방법 선정


<details>
<summary>index 적용 쿼리</summary>    
    
```sql
select * from expenditure as e
join
category as c
on e.category_id = c.category_id
where
    e.expended_date between :startDate and :endDate
and
    e.user_id = :userId;
```
    
</details> 

index 적용에 따른 실행 속도는 다음과 같다.

| index 순서                            | 실행 시간 (ms) |
| :----------------------------------- | ----------: |
| index 미적용                           | 528        |
| expended_date만 적용                   | 614        |
| userId, expended_date, category_id  | 106        |
| expended_date,  userId, category_id | 222        |
| category_id, expended_date, userId  | 324        |
index를 거는 순서에 따라 성능의 차이가 발생한다.

### Hibernate AutoCommit Check 최적화를 통한 쿼리 성능 개선
부하 테스트 시 로직을 확인해보니 `setAutoCommit(false)` 에서 시간적인 낭비 발생

- 기존의 Hibernate : AutoCommit 여부 확인을 위한 
```yml
transaction 시작 실행 시
    setAutoCommit(false) → 쿼리 1차 수행 → 쿼리 2차 수행 → setAutoCommit(true) → Commit or Rollback 로직 수행
```
- setAutoCommit을 위해서 조회를 진행해야되는데 이때 Query를 통해 autoCommit을 직접 조회
- 빈번한 setAutoCommit은 비효율적
- 이를 개선하기 위해 hibernate에서의 autocommit을 확인하느 과정 생략
- JDBC에서 autocommit을 false로 두고 이를 하이버네이터에서 참조하도록 수정
- 이를 통해 서비스 쿼리들의 성능 개선



### DB 성능 튜닝을 통한 성능 개선 & Cache 적용

**성능 개선이 필요한 API : recommendBudget API**

category_id에 따라 budget의 데이터들의 총 합을 활용한 예산 추천 API
저장된 데이터가 많아질 수록 성능이 떨어질 것이라 예상됨

#### DB 개선을 통한 성능 개선
- 데이터가 많아질 경우 DB에서 합계를 구할 때 많은 시간이 걸릴 것이라 판단
- category별 budget의 합계를 저장하는 테이블의 구현 및 데이터의 보관

##### 변경된 테이블 정보
<img width="1302" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/c0989264-9638-4b7d-9f3c-407469d757e4">
위와 같이 `total_budget`을 관리하는 테이블을 따로 만들어 합계를 관리 했다.
- 성능 테스트 지표는 아래 cache 적용 이후에서 확인이 가능하다.


**장점**
1. 데이터의 양과 상관 없이 균일한 성능 보여줌
2. TPS의 증가 및 mmt의 감소,부하 테스트에 걸리는 시간 감소, TPS그래프가 우상향하는 것을 확인할 수 있음

**단점**
1. 개발자의 실수가 발생할 가능성이 높다.
    - 개발자가 삽입, 삭제, 수정 작업을 구현할 때 total_budget 테이블에 데이터를 저장하지 않는다면 서비스 문제 발생
2. 불필요한 리소스 사용
- 아직 활용되는 곳이 많지 않기 때문에 table을 추가하는 것은 낭비라고 판닿
- cache의 적용을 통한 개선이 가능 할 것이라 판단

<br>

#### Cache 통한 서비스 개선 (부하 테스트 시 성능 최대 2배 개선)
- recommendBudget API에서 실시간으로 budget의 총합을 사용하는 것이 아닌 특정 시간마다 갱신되는 값을 활용할 경우 Cache 적용 가능
- 실시간 데이터와 특정 시간마다 갱신되는 데이터의 차이가 미비할 것이라 판단 -> 1시간동안 유효한 cache 적용

<img width="1294" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/82591bd9-5d89-48d3-8943-9e997a37beec">

- [Redis를 활용한 Global Cache 적용 및 Cache 선정 기준](https://funky-reward-c30.notion.site/d31e26a1377d475e88035a0093b2f23a?pvs=4)
    - 데이터의 양이 많지는 않지만 화면 구현 시 가장 많이 사용될 API Cache 구현 (`getCategory API`, `getCategoryList API`)
        - [부하테스트를 통한 성능개선](https://funky-reward-c30.notion.site/nGrinder-API-d6c868f3acf94975806112637e4264db?pvs=4)
            - **평균 1/5 테스트 시간 단축**
    - DB에서의 처리가 오래걸리는 API 캐싱 (`recommendBudget API`)
        - [부하 테스트를 통한 성능 개선](https://funky-reward-c30.notion.site/nGrinder-API-7d2efeed5b514576beb1ec69707d4831?pvs=4)
            - **평균 1/10 테스트 시간 단축**


### Spring Security를 활용한 인증 & 인가
#### 로그인 : JwtAuthenticationFilter
- `email`과 `password`로 인증 요청이 성공하면 `accessToken`을 `Authorization 헤더`로, `refreshToken`을 `Cookie`로 응답
#### 인증 : JwtVerificationFilter
- `Authorization 헤더`에 들어오는 `accessToken`을 해석, 목표 리소스에 접근할 수 있음
- `Authorization 헤더`에 들어오는 토큰 값이 정상적이지 않을 경우 응답을 통해 정보 전달
#### 인증된 유저 객체 : Principal
- UserDetail을 상속한 User를 상속한 객체
- `JwtAuthenticationFilter`에서 `조회된 유저`, `JwtVerificationFilter`에서 토큰에 있는 인증 정보를 바탕으로 인증된 유저 객체 생성

#### 인증된 사용자 정보 꺼내오는 커스텀 어노테이션 : `@CurrentUser`
- 인증된 사용자 객체에서 Controller가 활용하는 것은 id 정보이며, id에 null 값이 오는 것을 배제한 @AuthenticationPrincipal

#### 인가 : SecurityConfig
- `SecurityConfig`에서 각 리소스(URL)에 대해 필요한 권한 설정
- 설정한 권한을 가진 사람만 해당 리소스에 접근할 수 있음

---
## TEST
### 100여개의 테스트 진행을 통한 Test Coverage 90% 달성
- jacoco를 활용해 테스트 커버리지 90% 달성

<img width="1577" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/1b542804-d4b1-463f-8a31-774f124fde32">

- instruction 기준 90% 테스트 커버리지 달성



#### 회고
- 높은 테스트 커버리지를 통한 코드의 신뢰성 확보 가능
- 코드의 문제점 조기 발견으로 인한 서비스 안정성 확보
- 테스트 커버리지 만족을 위한 불필요한 테스트 진행
    - 테스트가 필요한 것과 테스트가 불필요한 것을 구분하는 눈이 생김
- 많은 테스트로 인한 build 시 시간 지연 발생

>[TestCoverage 학습 내용 링크](https://funky-reward-c30.notion.site/6d487438f1344e938f39d96ffbb8a9b9?pvs=4)
<br>

### Test 병렬 실행으로 인한 Test 시간 단축 1m18s -> 46s (32s개선)
기존의 Test 실행 방식은 하나의 테스트가 끝나면 다음 테스트를 실행하는 방식으로 테스트 진행

<img width="1723" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/92ec7dcd-c3db-4f98-8158-3305c803cd82">

#### 도입 배경
- 테스트 수가 많아지면 테스트로 인한 build 시간 증가
- repository layer의 경우는 docker container를 띄우기 때문에 더욱 시간 지연이 발생
    - docker container를 메서드별로 띄우는 것이 아닌 class 단위로 띄워 테스트 시간 단축

### TestContainer 적용

#### 도입 배경
- Test 중 Repository Layer 테스트 시 DB와의 연결 필요
- H2 활용해 Test 시 실제 DB와 다르기 때문에 적합하지 않다고 판단
- Test를 위한 DB 생성 시 리소스 낭비 발생
- **멱등성 보장**(언제 어디서 테스트를 하더라도 동일할 결과 보장)

#### 장점

1. 테스트의 멱등성 보장
2. Server에서 사용하는 DB를 활용한 테스트 가능

#### 단점
1. 테스트 시 소요 시간 증가
    - 추 후 테스트 코드 추가 시 더 많은 시간 지연 발생 예정
2. Docker가 설치 필수
3. Docker Container를 사용하기 위한 리소스 필요
   <br>
   단위 테스트만 진행했기에 Container를 테스트 전에 띄우고 테스트 하는 것이 효과적이나 TestContainer 활용한 이유는 다음과 같다.

1. Container의 port를 미리 사용하고 있을 경우 문제가 될 수 있음
2. 추 후 **통합테스트 진행시 Rollback이 되지 않기 때문**(TestRestTemplate 기준)

### 4. nGrinder를 활용한 부하 테스트
- nGrinder의 사용방법을 학습하고, 이를 활용한 부하 테스트 진행

> **nGrinder 선택한 이유**
>1. 직관적인 UI
>2. groovy를 통한 테스트 코드 작성(java 코드와 유사해 적용시키기 용이)
>3. 많은 레퍼런스
---

## 배포
- 배포 시 누구나 어떤 환경에서든 쉽게 배포할 수 있는 것이 배포 시 Docker를 활용한 취지

### Docker Container 활용을 통한 배포 안정성 확립
- 어떤 환경에서든 Docker만 설치 되어있다면 간단하고 안정적으로 서버 기동 가능
- 서버만을 Docker를 통해 기동하기 때문에 DB 등 서버에 연결된 외부 서버들은 사용자가 직접 설정해야하는 문제 발생

### Docker compose 활용을 통한 여러 컨테이너 제어
- 서버 기동에 필요한 모든 외부 서버들을 Docker Container를 통해 기동, docker-compose를 통한 제어
- 사용자가 다른 설정할 필요 없이 서버를 기동할 수 있음

<img width="1540" alt="image" src="https://github.com/SigLee2247/expense-manager/assets/116015708/398d1a0b-99f8-4497-9c06-2d0e764aae6d">


### script를 통한 명령어 적용
- [dockerfile을 만들 때 모든 code를 복사하는 것이 아닌 jar파일을 build한 뒤 해당 파일만을 복사](https://funky-reward-c30.notion.site/Docker-bcbc4e25dc7b439eb2b44dce4ccfecd6?pvs=4)
    - docker-compose를 하기 위해서는 `./gradlew build` 명령어의 활용이 필요
    - 위의 과정을 통합하기 위해 script에 적어둬 사용자가 명령어 한줄로 서버 기동

---

## 협업

### Github Issue & 칸반 보드를 통한 일정 관리
[칸반보드_링크](https://github.com/users/LEEGIHO94/projects/3)
<img width="1095" alt="image" src="https://github.com/LEEGIHO94/expense-manager/assets/116015708/b64f2953-6cf7-4221-8721-c1299c763f5a">

- 구현 내용에 대한 Issue 생성 및 칸반보드를 통해 개발 순서 설정 후 우선 순위가 높은 작업 먼저 진행
- 협업을 한다고 가정했을 때, 다른사람의 작업 및 본인의 작업을 직관적으로 파악할 수 있음


### Githook을 활용한 commit 제한
githook(pre-commit) 및 jacoco 설정을 통해 설정한 test coverage 80% 달성하지 못할 경우 commit이 실패하도록 수정

이를 통해 개발자가 test를 수행하도록 강제적인 규칙 적용 가능

#### 장점
1. 다른 사람과의 협업 시 테스트를 통한 코드의 신뢰성 확보 가능

#### 단점
1. commit 시 발생하는 Test 지연 시간 발생
2. Test가 많아질 경우 commit 시 대기 시간이 더욱 길어짐
3. 테스트 커버리지를 채우기 위한 불필요한 테스트 진행 가능

#### 개선점
- commit 시에는 메시지만 규칙을 지켰는지 확인하고 github에 push 할 때 테스트 커버리지 조건을 만족하도록 수정

### 코드 스타일 통일
- spotless 및 google java format을 활용한 코드 린트 통일

#### 장점
1. 협업을 한다고 가정했을 때, 다른사람의 코드 포맷에 대한 이야기할 필요 없음
2. 횡스크롤이 없어 코드 가독성 상승
3. 린트의 적용을 통해 code format 강제화를 통한 코드의 통일성 확립

#### 단점
1. lambda 코드의 많은 enter 활용
2. code format이 적용되지 않는다면 build 불가능
    - 이를 통한 style 변경으로 인한 대량의 코드 수정 발생

---

