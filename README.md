# springboot-jwt-restApi

### 목적 : 
1. SpringSecurity + JWT 토큰인증 구축
2. JWT 토큰인증방식 REST API 에 적용
3. JPA 활용 공부
4. JUNIT 테스트 코드 공부
5. Swagger로 API문서 작성
6. Docker local설치 후  도커 이미지파일 생성, local 에서 구동해보기

### 기능 :
1. JWT 토큰인증을 이용한 회원가입, 로그인(토큰발급), 
상품조회, 유저조회, 주문하기 REST API 제공

### 개발환경 
* 언어  : java 11
* 프레임워크  : springboot 2.6.4
* 빌드도구  : gradle 7.4.1
* 개발도구  : Intellij IDEA (Community Edition)
* ORM : JPA
* 데이터베이스 : h2
* 인증방식 : Spring Security + JWT
* 테스트 : Junit 4.13.1
* api문서 : Swagger 2.9.2

### JWT 토큰인증 API 테스트

* API문서 Swagger 적용

![스웨커api목록](https://user-images.githubusercontent.com/48856906/160974619-5b7eb8da-8c0b-457f-abe4-bb67e4eaf11b.PNG)

* 회원가입 REQUEST

![회원가입request](https://user-images.githubusercontent.com/48856906/160974640-ff289729-43b0-462c-8a1c-3d54c53fc28a.PNG)

* 회원가입 RESPONSE

![회원가입 response](https://user-images.githubusercontent.com/48856906/160974647-4d230745-e35d-4516-9413-6cacd9516a31.PNG)

* 로그인(토큰발급요청) REQUEST

![로그인request](https://user-images.githubusercontent.com/48856906/160974658-0af2eb94-0c3f-4718-a8bf-81bdfe69a68c.PNG)

* 로그인(토큰발급) RESPONSE 

![로그인response](https://user-images.githubusercontent.com/48856906/160974667-952e9c0c-3cfc-4258-989e-a4111bd3917d.PNG)

* 주문하기 - header에 토큰을 입력 후 요청 (status : 200)

![토큰으로요청](https://user-images.githubusercontent.com/48856906/160974676-b1368523-67e7-4f77-9127-aae465a786eb.PNG)

* 주문하기 - header에 토큰 없을 시 요청 결과 (status : 401) 

![토큰없이 요청](https://user-images.githubusercontent.com/48856906/160974683-a3ff58ba-3fe0-4a1c-b011-6fbbab16343f.PNG)

* 주문하기 - header에  토큰 임의 변경 시 요청 결과 (status : 401)

![토큰변경시](https://user-images.githubusercontent.com/48856906/160974687-e3defb27-d6e8-4cb3-85ed-696e6aadd27a.PNG)


