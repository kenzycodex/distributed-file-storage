# Distributed File Storage System - Detailed Development Workflow

## Phase 0: Project Initialization & Core Configuration
### 0.1 Root Project Configuration
1. `/pom.xml`
   - Define project dependencies
   - Configure Maven modules
   - Set up Spring Boot parent POM
   - Add core dependencies (Spring Web, Security, JPA)

2. `/docker/docker-compose.yml`
   - Define services for:
     * MySQL Database
     * SQLite Container
     * Load Balancer
     * Storage Containers

3. `/docker/load-balancer/Dockerfile`
   - Configure load balancer container image
   - Set up networking and port exposures

4. `/docker/storage-container/Dockerfile`
   - Create container image for storage nodes
   - Configure storage and networking

## Phase 1: Database & Core Model Design
### 1.1 Database Schema
1. `/src/main/java/com/cloudstore/model/entity/`
   - `User.java`: User entity with authentication details
     * id, username, email, password, role
     * JPA annotations
     * Authentication methods

   - `FileMetadata.java`: File metadata tracking
     * id, filename, size, owner, upload_date
     * Chunk management methods

   - `FileChunk.java`: Distributed file chunk representation
     * id, fileId, chunkNumber, containerID
     * Encryption and distribution methods

   - `Container.java`: Storage container model
     * id, name, capacity, current_load, status
     * Health monitoring methods

   - `AuditLog.java`: System audit and tracking
     * id, userId, action, timestamp
     * Logging methods

### 1.2 Repository Interfaces
1. `/src/main/java/com/cloudstore/repository/`
   - `UserRepository.java`: CRUD operations for users
   - `FileRepository.java`: File metadata management
   - `ContainerRepository.java`: Storage container tracking
   - `AuditLogRepository.java`: System audit logging

## Phase 2: Security & Authentication
### 2.1 Authentication Services
1. `/src/main/java/com/cloudstore/service/auth/`
   - `AuthService.java`: User registration, login logic
   - `JwtService.java`: JWT token generation and validation
   - `UserDetailsServiceImpl.java`: Custom user details service

2. `/src/main/java/com/cloudstore/config/`
   - `SecurityConfig.java`: Spring Security configuration
     * Password encoding
     * JWT filter
     * Authentication providers

### 2.2 Authentication Controllers
1. `/src/main/java/com/cloudstore/controller/`
   - `AuthController.java`: 
     * Registration endpoint
     * Login endpoint
     * Token refresh
     * Password reset

## Phase 3: File Management System
### 3.1 File Services
1. `/src/main/java/com/cloudstore/service/file/`
   - `FileService.java`: Core file management
     * Upload
     * Download
     * Delete
     * Share

   - `ChunkingService.java`: File distribution logic
     * Split files into chunks
     * Distribute across containers
     * Reconstruct files

   - `EncryptionService.java`: Security layer
     * AES encryption for file chunks
     * Key management
     * Secure file operations

### 3.2 File Controllers
1. `/src/main/java/com/cloudstore/controller/`
   - `FileController.java`:
     * File upload endpoints
     * Download endpoints
     * File sharing mechanism
     * Permissions management

## Phase 4: Load Balancer Implementation
### 4.1 Load Balancing Services
1. `/src/main/java/com/cloudstore/service/loadbalancer/`
   - `LoadBalancerService.java`: Core load distribution
   - `RoundRobinStrategy.java`: Round-robin algorithm
   - `FCFSStrategy.java`: First-come-first-serve
   - `PriorityStrategy.java`: Priority-based selection

2. `/src/main/java/com/cloudstore/service/monitoring/`
   - `HealthCheckService.java`: 
     * Container health monitoring
     * Performance metrics
     * Automatic container management

## Phase 5: Frontend Development
### 5.1 JavaFX GUI
1. `/frontend/src/main/java/com/cloudstore/gui/`
   - `MainApplication.java`: Primary application entry
   - `controller/`
     * `LoginController.java`
     * `DashboardController.java`
     * `AdminController.java`

2. `/frontend/src/main/resources/`
   - `fxml/`: UI layouts
     * `login.fxml`
     * `dashboard.fxml`
     * `admin.fxml`
   
   - `css/`: Styling
     * `styles.css`

## Phase 6: Configuration & Properties
1. `/src/main/resources/`
   - `application.yml`: Primary configuration
   - `application-dev.yml`: Development profile
   - `application-prod.yml`: Production profile

## Phase 7: Utilities & Helpers
1. `/src/main/java/com/cloudstore/util/`
   - `Constants.java`: Constant values
   - `SecurityUtil.java`: Security-related utilities
   - `FileUtils.java`: File operation helpers

## Phase 8: Testing
1. `/src/test/java/com/cloudstore/`
   - `service/`: Service layer tests
     * `FileServiceTest.java`
     * `LoadBalancerServiceTest.java`
   
   - `controller/`: Controller tests
     * `FileControllerTest.java`
     * `AuthControllerTest.java`

## Phase 9: Deployment Scripts
1. `/scripts/`
   - `setup-databases.sh`: Database initialization
   - `init-containers.sh`: Container setup
   - `run-tests.sh`: Automated testing script

## Continuous Integration
1. `.github/workflows/`: CI/CD configurations
   - `maven.yml`: Build and test workflow
   - `docker-publish.yml`: Container publishing

## Documentation
1. `README.md`: Project overview
2. `CONTRIBUTING.md`: Contribution guidelines
3. `LICENSE`: Project licensing