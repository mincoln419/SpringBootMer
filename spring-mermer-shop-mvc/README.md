## 
    Author: Mermer Cho
    Version: 0.0.1 - Abstract planning
    Version: 0.0.2 - gitignore, 사용자 인증 관련 설명 추가
##



### Introduce ###
- 이 프로젝트는 Springboot 와 react.js 를 활용하여 만든 e-commerce 프로그램입니다.
- 아직 주력 상품에 대해서는 정하지 않았으나 기본적인 사용자 관리, 게시판 등록으로 프로그램 제작을 시작하고 있습니다.
- 우선 계획하고 있는 추가 기능은 다음과 같습니다.
  -  데이터 수집 알고리즘, google, naver api를 연동하여 데이터를 조회할 수 있는 기능
  -  카카오, 네이버 로그인 기능
  -  사용자 권한관리
  -  SMTP를 이용한 본 소속 유저 인증기능
  -  Python Project와 데이터 interface

### Build Spec ###
- maven 4.0
- spring-boot-starter 2.6.5
- spring 5.3.12
- spring-data-jdbc 2.2.6
- spring-hateos 1.3.5
- hibernate-validator 6.2.0
- spring-boot-configuration-processor 2.5.6
- h2 1.4.200
- lombok 1.18.22
- spring-restdocs-mockmvc 2.0.6.RELEASE
- 

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





