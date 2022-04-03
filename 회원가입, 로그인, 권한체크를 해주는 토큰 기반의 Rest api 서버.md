## 회원가입, 로그인, 권한체크를 해주는 토큰 기반의 HTTP api 서버

1. 빈 주입 방법으로 @RequiredArgsConstructor 를 적극 활용하자
2. /api/ 로 시작하게 하자
3. 엔티티 이름으로 User 는 사용하지 말자. 스프링에서 자체적으로 User 라는 클래스를 지원하는데, 이름 너무 헷갈림... 사용자테이블 이름은 Account 로 하자.

전통적인 로그인 방식 (세션기반)

```
유저네임, 패스워드 로그인 정상

서버는 세션 ID 생성
클라이언트 쿠키 세션 ID를 응답

요청할때마다 쿠키값 세션 ID 를 항상 들고 서버쪽으로 요청하기 때문에 서버는 세션 ID가 유효한지 판단해서 유효하면 인증이 필요한 페이지로 접근하게 하면 됨. 서버는 세션 ID 를 기억하고 있어야 함.
```

이 프로젝트의 로그인 방식(토큰기반)

```
유저네임, 패스워드 로그인 정상

서버는 JWT 토큰을 생성
클라이언트로 JWT 토큰을 응답

요청할 때마다 JWT 톹큰을 가지고 요청. 서버는 JWT 토큰이 유효한지를 판단(필터를 만들어야 함). 세션방식일 떄와 다르게, 서버는 JWT 정보를 기억하지 않음
```



## 회원가입

```
{
   "email" : "yeonnex@gmail.com",
   "name" : "seoyeon Jang",
   "password" : "12345678"
}
```



## 로그인

```
{
   "email" : "yeonnex@gmail.com",
   "password" : "12345678"
}
```



- HTTP API 설계 원칙을 기반으로 API 스펙을 디자인하기로 했다.
- JSON을 직렬화 포맷으로 결정했다.
- Authorization 헤더로 인증 정보를 명시하기로 했다.
- 인증 스키마에 JWT 기반의 Bearer를 사용하기로 했다.
- API 스펙을 프론트엔드에게 전달할 때는 (현재 README.md -> GitBook 예정)
- 깃헙 레포 Project 탭

```text
1. 빈 주입 방법으로 @RequiredArgsConstructor 를 적극 활용하자
2. /api/.. 로 되는 경로는 권한이 필요(USER or MANAGER or ADMIN)
3. 사용자 엔티티 이름으로 User 를 사용하지 않았다. 스프링에서 자체적으로 User 라는 클래스를 지원하는데, 이름 너무 헷갈림... 사용자 엔티티 이름은 Account 로 하였음
```

### 데이터베이스

- MySQL
- H2 (test)

### JWT

```java
// rawSing 를 HS256으로 암호화한 것이 Signature
String rawSig = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" + "."
+"eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
+ "."
+ "wkdtjdusAbcd"; <- secret key 🔑
```

#### JWT

BASE64(헤더).
BASE64(페이로드).
BASE64(HS256암호화(rawSig))

## API Spec

#### /api/v1/user/**  

- ROLE_USER 또는 MANAGER, ADMIN 권한이 없다면 403 상태코드 반환

#### /api/v1/manager/**

- ROLE_MANAGER 또는 ADMIN 권한이 없다면 403 상태코드 반환

#### /api/v1/admin/**

- ROLE_ADMIN 권한이 없다면 403 상태코드 반환

#### 그 외 다른 모든 요청은 권한 필요없이 permitAll 로 하였다



## 인프라

AWS의 EC2서버, RDS(Relational Database Service)

## Spring Security

시큐리티 필터 동작 순서. 일반 필터들은 시큐리티 필터가 모두 동작한 후 **그다음에** 동작한다. SecurityContextPersistenceFilter 가 가장 먼저 동작하게 된다.

![img](https://raw.githubusercontent.com/yeonnex/image-server/main/img/SecurityFilterChain2.JPG)