## 
    Author: Mermer Cho
    Version: 0.0.1 - Abstract planning
    Version: 0.0.2 - gitignore, 사용자 인증 관련 설명 추가
    Version: 0.0.3 - 프로그램 기능 및 목적 서술 추가
    Version: 0.0.4 - 프로그램 기능 구체화
##



### Introduce ###
- 이 프로젝트는 Springboot 와 react.js 를 활용하여 만든 '형량 계산기' 프로그램입니다.
- 이 프로젝트의 목표는 다음과 같습니다

#### 대한민국 범죄 형량 계산기 ####
- 판례분석 : 어떤 방식으로 어떤 목적으로 할 것인가?    
    - 어떤 행동들을 종합해봤을 때 어느정도의 형량이 나오는지 평균치를 계산해주는 프로그램
    - 자신이 당했던 특정 행동에 대해서는 고소 가능성에 대해 검토할 수 있도록
    - 자기가 행했던 행동에 대해서는 자기반성의 시간을 갖고 피해자에게 사과할 마음이 들드록 가이드          
- 범위: 최근 이슈 범죄로 우선적 접근
	- 형사법판례위주
	- 샘플로 성추행 범죄 사건으로 좁혀서 판단
	- 세부 범주로 나누어서 살펴봄 : 아동청소년, 성인, 스토킹, 강력범죄
	- 법률의 양형으로 그래프 작성
	- 판례 통계 데이터
- 어떤 알고리즘을 사용할 것인가
    - 알고리즘 플로우 차트, 비즈니스 룰에 대한 별도 문서 필요
    - 데이터분석 관련 python의 라이브러리 pandas 고려중

- 기타 기본적인 프로그램 기능
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





