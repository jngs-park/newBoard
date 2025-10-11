# NewBoard

Spring Boot + MySQL + Kafka + Redis + JWT 기반 게시판 API

- 게시글 CRUD
- Kafka Producer/Consumer (topic: `article-topic`)로 DB 로그 적재
- Redis 캐시 (마지막 게시글 제목 등)
- JWT 인증/인가
- Swagger(OpenAPI) 문서 제공

## 기술 스택
- Java 17, Spring Boot 3.5.6
- Spring Data JPA(Hibernate), MySQL 8.0
- Apache Kafka, Zookeeper
- Redis
- Spring Security + JWT
- Swagger UI (springdoc-openapi)

## 아키텍처
```mermaid
flowchart LR
  Client -->|HTTP| App[Spring Boot]
  subgraph Infra
    DB[(MySQL)]
    R[(Redis)]
    K[(Kafka)]
    Z[(Zookeeper)]
  end
  App --> DB
  App --> R
  App -->|produce/consume| K
  K --- Z
