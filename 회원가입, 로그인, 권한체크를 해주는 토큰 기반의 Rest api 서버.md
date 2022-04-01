## 회원가입, 로그인, 권한체크를 해주는 토큰 기반의 Rest api 서버

...OAuth 조만간은.. 포기요....⛳

```java
1. 빈 주입 방법으로 @RequiredArgsConstructor 를 적극 활용하자
2. /api/ 로 시작하게 하자
3. 엔티티 이름으로 User 는 사용하지 말자. 스프링에서 자체적으로 User 라는 클래스를 지원하는데, 이름 너무 헷갈림... 사용자테이블 이름은 Account 로 하자.
```

### 데이터베이스

- H2

### JWT

```java
String rawSig = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" 
+"eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
+ "wkdtjdusAbcd"; <- secret key 🔑
```

#### JWT

BASE64(헤더).
BASE64(페이로드).
BASE64(HS256암호화(rawSig))