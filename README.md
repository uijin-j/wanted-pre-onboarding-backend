# 프리온보딩 백엔드 인턴십 선발과제

## Introduction

⏳ 개발 기간: 2024.07.25 ~ 진행 중

🛠️ 기술스택 : Java & Spring

👩🏻‍💻 프로젝트 소개

- 본 서비스는 기업의 채용을 위한 웹 서비스입니다.
- 회사는 채용공고를 생성하고, 이에 사용자는 지원합니다.

## ERD
<img width="739" alt="스크린샷 2024-08-05 16 15 46" src="https://github.com/user-attachments/assets/71921bdd-3947-4c0f-861f-792c498d4a5b">

## 📂 서비스 주요 기능

    - 채용공고 등록
    - 채용공고 수정
    - 채용공고 삭제
    - 채용공고 리스트 조회
    - 키워드로 채용공고 검색
    - 채용공고 상세 조회
    - 채용공고 지원

## Description

### 실행방법

1️⃣ docker를 실행해 주세요. (도커가 없다면 설치해주세요.)

2️⃣ ```docker/docker-compose.yml``` 파일을 실행해주세요. </br>
💡 포트 번호 및 환경 변수는 로컬 환경에 맞게 변경하셔도 됩니다:) 변경 후 ```resource/application.yml``` 파일도 수정해주세요.

3️⃣ 프로그램을 실행해 주세요.

### 테스트

- **전체 라인 커버리지(_80%_)**
    - domain 레이어 (_100%_)
    - business 레이어 (_100%_)
    - persistence 레이어 (_100%_)
    - presentation 레이어 (_2%_)

- domain 레이어는 단위 테스트, business 레이어는 persistence 레이어와 통합 테스트로 진행


