# Nempo Track API

A graph-based asset tracking system built with Spring Boot, Neo4j, and GraphQL for managing IT assets and their dependencies with impact analysis capabilities.

## Tech Stack

| Category | Technology |
|----------|------------|
| **Core** | Java 21, Spring Boot 3.2.4, Maven |
| **Database** | Neo4j 5 (Graph Database) |
| **API** | Spring GraphQL, Spring Web |
| **Security** | Spring Security, JWT (jjwt 0.11.5) |
| **Tools** | Lombok, java-dotenv |
| **Build** | GraalVM Native, Docker, Docker Compose |

## Project Structure

```
src/main/java/com/github/PickHD/nempo_track_api/
├── api/                   # Controllers, DTOs, Mappers
│   ├── controller/        # Handling Request & Responses
│   ├── dto/               # Converting Request/Response objects
│   └── mapper/            # Entity-DTO converters
├── config/                # Security, Database, Transaction configs
│   └── properties/        # Env property configs
├── domain/                # Business layer
│   ├── model/             # Entities
│   └── repository/        # Neo4j repositories
├── exception/             # Global Exception Handler, Custom Error Exception
├── security/              # JWT filter, handlers
├── service/               # Logic interface
│   └── impl/              # Logic implementation
```

## Quick Start

```bash
# 1. Configure environment
cp .env.example .env

# 2. Run with Docker Compose
docker-compose up -d

# 3. Access the API
# GraphQL: http://localhost:8080/graphql
# Neo4j Browser: http://localhost:7474
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | Login & get JWT token |

### GraphQL Queries
```graphql
# Get all assets (paginated)
assets(page: Int, size: Int): AssetResponse

# Get asset by ID
assetById(id: ID): Asset

# Get assets by type
assetsByType(type: String): [Asset]

# Impact analysis
impactAnalysis(assetId: ID, statusFilter: String, nameFilter: String): [Asset]
```

### GraphQL Mutations
```graphql
# Create new asset
addAsset(input: AssetInput!): Asset

# Link assets as dependencies
linkAssets(sourceId: ID, targetId: ID): Asset
```

## How to Implement a Feature

### 1. Create Entity Model
```java
@Node("YourEntity")
public class YourEntity {
    @Id @GeneratedValue private UUID id;
    private String name;
}
```

### 2. Create Repository
```java
@Repository
public interface YourEntityRepository extends Neo4jRepository<YourEntity, UUID> {
}
```

### 3. Create DTOs (Request/Response)
```java
@Data
public class YourEntityRequest { private String name; }

@Data
public class YourEntityResponse { private UUID id; private String name; }
```

### 4. Create Mapper
```java
@Component
public class YourEntityMapper {
    public YourEntity toEntity(YourEntityRequest req) { ... }
    public YourEntityResponse toResponse(YourEntity entity) { ... }
}
```

### 5. Create Service
```java
@Service
public class YourEntityServiceImpl implements YourEntityService {
    // Business logic here
}
```

### 6. Create Controller
```java
// For REST
@RestController
@RequestMapping("/api/your-entity")
public class YourEntityController { ... }

// For GraphQL
@Controller
public class YourEntityGraphController {
    @QueryMapping public YourEntity yourEntityById(@Argument ID id) { ... }
    @MutationMapping public YourEntity addYourEntity(@Argument YourEntityInput input) { ... }
}
```

### 7. Update GraphQL Schema (if applicable)
```graphql
type YourEntity { id: ID!, name: String! }
extend type Query { yourEntityById(id: ID!): YourEntity }
extend type Mutation { addYourEntity(input: YourEntityInput!): YourEntity }
```

## Data Model

```
(:Asset)-[:DEPENDS_ON]->(:Asset)
(:Asset)-[:OWNED_BY]->(:Team)
(:User {username, password, role})
```

## License

MIT License
