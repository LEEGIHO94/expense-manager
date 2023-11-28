# expense-manager
요구 사항을 분석해 만든 예산 관리 APP

## 요구사항 링크

[요구사항 링크](https://bow-hair-db3.notion.site/90cba97a58a843e4a2563a226db3d5b5)

## 요구사항 분석 및 회고

[요구사항 분석](https://funky-reward-c30.notion.site/API-eec9a9c8090643f1b446a4ec565fbf68?pvs=4)

프로젝트를 진행하며 지속적인 추가 예정

**총 구현 퍼센트 : 60%**
통계 및 알림 기능의 미구현으로 인해 낮은 구현 퍼센트
빠른 시일 내 완성 예정 

## DB Table
[DB 다이어그램 링크](https://www.erdcloud.com/d/W6hzt6WeCXKfjQF9Z)

<img width="1235" alt="image" src="https://github.com/LEEGIHO94/expense-manager/assets/116015708/14630cc5-7fd1-448e-bb8c-443be2e458a5">


## 구현 API 범위

### BudgetAPI
- POST
- GET Recommend

### UserAPI
- POST

### Expenditure
- POST
- GET Details
- GET List By Condition
    - category  **선택**
    - 최대 최소 금액 **선택** 최대 금액 , 최소 금액 중 둘 중 하나만 존재해도 상관 X
    - 조회 기간 **필수**
    - 사용자 식별자 **필수** 로그인을 통해 데이터 전달
- DELETE 지출 삭제




## API 이외의 구현

### Spring Security를 활용한 보안 서비스 구현

### SequenceDiagram

- 시큐리티는 구현되어 있으나 해당 시퀀스다이어그램은 추 후 이미지화 시켜 첨부할 예정


## 칸반을 활용한 프로젝트 관리
[링크](https://github.com/users/LEEGIHO94/projects/3)
<img width="1095" alt="image" src="https://github.com/LEEGIHO94/expense-manager/assets/116015708/b64f2953-6cf7-4221-8721-c1299c763f5a">

이슈를 활용한 칸반보드로 리팩토링이 필요할 경우 이슈를 미리 만들어 우선순위가 높은 작업을 처리한 이후 작업


## 미 구현 요구사항
1. Discord를 통한 알람 서비스
2. 지출 통계 및 추천 서비스



