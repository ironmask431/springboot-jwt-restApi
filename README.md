# springboot-jwt-restApi

### 목적 : 
1. SpringSecurity + JWT 토큰인증/인가 구축
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


### 전체 API 실행결과 
1.로그인(토큰발급)

```POST - /api/auth/login```

![로그인(토큰발급)](https://user-images.githubusercontent.com/48856906/212468852-a7d7e8ed-b48a-43bd-b12f-b4631dc4b91c.png)


2.회원가입  

```POST  - /api/join/signup```

![회원가입](https://user-images.githubusercontent.com/48856906/212468858-f43e4f52-f902-4080-9e90-a78202ddef35.png)


3.email로 회원정보조회 - (ADMIN 유저만 가능)

```GET  - /api/user/email/{email}```

![email회원정보조회](https://user-images.githubusercontent.com/48856906/212468867-d6868029-f0b5-4e62-993b-266eed0a6b5e.png)


4.나의 회원정보조회 

```GET  - /api/user/myInfo```

![나의회원정보조회](https://user-images.githubusercontent.com/48856906/212468945-3955c8ae-91c9-422e-a944-6ad28075a41f.png)


5.전체상품목록 조회

```GET  - /api/product/all```

![전체상품목록조회](https://user-images.githubusercontent.com/48856906/212468883-6997b717-b50f-40bf-941d-09b48977aca6.png)


6.상품ID로 상품조회 

```GET  - /api/product/prdId/{prdId}```

![상품id로 상품조회](https://user-images.githubusercontent.com/48856906/212468887-a59b7009-7228-4679-aee6-7e9c7af248b5.png)

7.전체주문조회 - (ADMIN 유저만 가능)

```GET  - /api/order/all```

![전체주문조회(admin)](https://user-images.githubusercontent.com/48856906/212468894-8b6579c0-dab4-4898-9bcb-c0ed95569ffe.png)


8.주문ID로 주문조회 - (ADMIN 유저만 가능)

```GET  - /api/order/ordId/{ordId}```

![주문ID로 주문조회](https://user-images.githubusercontent.com/48856906/212468896-68cba4bb-111b-490b-847d-c2f58a0320ad.png)


8.회원ID로 주문조회 - (ADMIN 유저만 가능)

```GET  - /api/order/userId/{userId}```

![회원id로 주문조회](https://user-images.githubusercontent.com/48856906/212468898-507ee101-3306-4430-b30a-8f4eafad263e.png)

10.나의 주문조회 

```GET  - /api/order/myOrder```

![나의주문조회](https://user-images.githubusercontent.com/48856906/212468903-6783d8dd-9081-4c09-b876-157ae3cc5a83.png)


11.주문입력

```POST  - /api/order/save```

![주문입력](https://user-images.githubusercontent.com/48856906/212468907-f22929cb-8116-4591-b026-f863e9b200ab.png)



