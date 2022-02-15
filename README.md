
branch
- main
  - 경매 프로그램 테스트용 코드 작성 -> 5장부터 책 따라서 공부해볼 예정


1. [참가자 엔티티 설계](https://w97ww.tistory.com/60)
2. [참가자 등록](https://w97ww.tistory.com/63)  
3. [프로젝트 버전 변경](https://w97ww.tistory.com/64)  
4. [mysql 연동 및 팀 엔티티 설계](https://w97ww.tistory.com/67)  
5. [GlobalExceptionHandler](https://w97ww.tistory.com/74)
6. [참가자 등록 계층별로 다시 테스트, validation 추가](https://w97ww.tistory.com/87)

- copybook
  - 책 따라 구현한 코드 (4장까지)

  

## feature/10-oauth-login
- 책 읽고 OAuth2 + security 로직 이해 
- JWT추가 
- 이해하며 내 프로제트에 맞게 리팩토링해보기 

Authorization code 방식으로 구현할 예정 (설명 잘못됐을수도)
- 프론트엔드에서 소셜 로그인을 요청하면 백엔드로 리다이렉트 되어 로그인 서버에게서 Access Token을 획득한다.
- 획득한 AccessToken으로 유저정보를 요청해 JWT토큰을 발급하고, 이를 가지고 클라이언트에게 전달한다.
- 클라이언트는 해당 JWT토큰으로 서버와 통신한다. -> Jwt Token + refresh Token 구조


## 경기 등록 서비스

경기 일정 등록 프로젝트
- 1. 로그인 기능 구현
  - 1. 네이버로그인으  로 회원가입한다  
    - 1. 회원가입 후 자신의 롤 아이디를 입력하여 연동한다 . 중복체크  
    - 2. 만약 이상한사람이 이상한 아이디를 체크했을 경우 뭔가 처리해준다.  
    - 3. 회원정보는 네이버아이디/이름/소환사명  
  - 2. 마이페이지  
    - 1. 소환사명과 연동  
    - 2. 인증받은 회원 체킹  
  - 3. admin / user 분리  
  - 4. 회원 관리 기능  
    - 1. 이상한 회원 삭제  
    - 2. 회원 등급 수정  
    - 3. 소환사명 변경  
- 2. 경기 일정 등록  
  - 1. 리그 생성  
    - 1. 리그가 열리면 리그를 생성한다  
    - 2. 리그는 리그 종류, 기수, 리그 이름, 날짜,  
  - 2. 경기 등록 ,수정,삭제  
    - 1. 날짜  
    - 2. 참가자  
    - 3. 우선순위또는 경기 레벨  
  - 3. 리그 별 경기 조회  
    - 1. 경기와 게임id 매칭시켜 결과 저장후 연동  
  - 4. 리그 목록 조회,수정,삭제  
- 3. 회원에 따른 팀 생성  
  - 1. 한명의 회원은 하나의 리그당 한 팀에만 소속될 수 있다  
  - 2. 한명의 회원은 여러 팀을 가질 수 있다  
  - 3. 팀을 생성한 후 팀 멤버 수정을 통해 회원을 추가할 수 있다  
  - 4. 팀이 삭제되면 회원에 등록된 팀 정보가 사라진다. 회원은 삭제되지 않는다  
