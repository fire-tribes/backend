
# ⚙️세부 기술스택
* Springboot 3.1.2
  * Spring Web
  * Spring Data Jpa
  * Spring Security
  * Spring Cache
  * Spring Cloud-openfeign
* DB/Cache
  * H2 DB
  * CaffeineCache(내장 캐시 용도) 3.1.1
* 기타 유틸
  * Swagger(springdoc 2.2.0)
  * Opencsv 5.5
  * jjwt(jwt 관련) 0.11.5

<br>

# 👉로그인 Flow
```mermaid
flowchart TD
    A[사용자] --> B[Frontend loginurl]
    B --> |1.auth code| C
    C[Kakao Auth Server] -->|2.access token| B
    B -->|3.access token| D[Kakao Resource Server]
    D -->|4.user info| B
    B -->|5.user info| E[backend loginurl]
    E -->|6.jwt token| B
```
<br>

# 👉ERD
![fires-erd](https://github.com/fire-tribes/backend/assets/126640838/e161944f-72ea-4f60-971b-e390d4abed70)
