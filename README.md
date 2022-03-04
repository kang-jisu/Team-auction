
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

