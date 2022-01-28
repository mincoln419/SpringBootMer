## 
    Author: Mermer Cho
    Version: 0.0.1 - Abstract planning
    Version: 0.0.2 - gitignore, 사용자 인증 관련 설명 추가
    Version: 0.0.3 - 프로그램 기능 및 목적 서술 추가
    Version: 0.0.4 - 프로그램 기능 구체화
    Version: 0.0.5 - 프로그램 주요 비즈니스 변경 -> 단순화
    Version: 0.1.0 - 데이터 수신처리를 위해 배치 프로그램 추가
##



### Introduce ###
- 이 프로젝트는 Springboot 와 react.js 를 활용하여 만든 법률 검색 간편화 시스템입니다. 
- 공개법령 API의 xml 파일을 DB에 저장 후 보다 정제된 데이터를 json 방식으로 리턴해줍니다.
- 이 프로젝트의 목표는 다음과 같습니다

### 법률 검색 개선 프로그램
1. 의의
- 우리 법 체계는 절로 나누어져 있으나 디지털화 시키기에는 지나치게 문장화 되어있음
- 법을 학문으로 배울때에는 제1조에서 천 몇백조 까지 존재하는 수많은 법률을 트리구조로 나누어서 공부하지만 일반인들은 어떤 죄가 어디에 있는지 알기 위해서는 한줄한줄 찾아봐야 하는 번거로움이 있음
- 만약에 찾더라도 그 법이 다른 법과 어떻게 유기되어있는지 알기는 너무 어려운 현실

2. 주요 비즈니스
- 일부 법령에 대해 법학에서 배우는 법체계처럼 체계적으로 데이터화 시키고 접근 시킬 수 있다면 전체 법령에 대한 디지털 구조화에 대해 도움을 줄 수 있을 것으로 보임
- 우선은 민법 -부동산 임대차, 형법 - 250조 ~ 300조(살인죄~경제사범) 까지에 대해서 적용되는 일반법, 특별법을 데이터베이스에 체계화 시킴
- 공개법률 API에서 제공하는 1차원의 검색 수준을 좀더 끌어올려 좀더 다양한 조건으로 법령과 판례를 조회할 수 있는 알고리즘을 제공
- 기존 xml 형태로 제공하는 데이터를 json 형태로 변경시켜 돌려주는 서비스

3. 기타 기본적인 프로그램 기능
  -  데이터 수집 알고리즘, google, naver api를 연동하여 데이터를 조회할 수 있는 기능
  -  카카오, 네이버 로그인 기능
  -  사용자 권한관리
  -  SMTP를 이용한 본 소속 유저 인증기능
  -  Python Project와 데이터 interface
  -  회원정보 삭제는 사용여부 N으로 변경하고, 자동배치에 의해 하루 한번 마지막 로그인 후 1년이 경과된 회원에 대해서 삭제한다 - 자동배치프로그램 추가 필요
  
- Draft
  - 초안으로 기본적인 형사 범죄 행위에 대한 범죄사실을 입력하면 해당 행위의 형량이 나오는 프로그램
  - 구성요건, 위법성조각사유, 책임조각사유를 알아낼 수 있는 항목을 입력 form으로 제공
  - 적용 법률을 특별법, 일반법으로 선택할 수 있게 하여 형량 비교를 할 수 있도록 함

### Build Spec ###
- maven 4.0 (Spring Rest API)
- gradle 6.5.1(Spring batch)
- spring-boot-starter 2.6.5
- spring 5.3.12
- spring-data-jdbc 2.2.6
- spring-hateos 1.3.5
- hibernate-validator 6.2.0
- spring-boot-configuration-processor 2.5.6
- h2 1.4.200
- lombok 1.18.22
- spring-restdocs-mockmvc 2.0.6.RELEASE
- query DSL
- spring-boot-batch
- rest.js 
- next.js
- redux.js reduxSaga.js

### 사용자 인증 ###
- OAuth2 및 redis를 통한 token 저장
- 최초 계정 추가는 관리자 권한으로 바로 연결하여 등록할 수 있도록 함 - 이후 로그인으로 토큰 발급받아 사용가능

### Git Ignore ###
- 개발관련 개인정보 사항이 담긴 properties 파일에 대해서 형상관리에서 제외 -> 운영배포 서버에 직접 파일을 복사해서 사용


### Etc Development Tool ###
- [Json Parser Online][jsonParserLink]
- [Postman][postmanLink]

[jsonParserLink]: http://json.parser.online.fr "jsonParser Link"
[postmanLink]: https://www.postman.com "postman Link"





