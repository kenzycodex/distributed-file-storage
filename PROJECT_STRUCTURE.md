# Distributed File Storage - Project Structure

```
└── 📁distributed-file-storage
 ┣ 📂.vscode
 ┃ ┗ 📜settings.json
 ┣ 📂backend
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AsyncConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RateLimitConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoadBalancerController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtAuthenticationException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtAuthenticationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerStatusDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLog.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Container.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileChunk.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileMetadata.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLogRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂security
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂event
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationFailureEvent.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationSuccessEvent.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PasswordChangeEvent.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂auth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TokenBlacklistService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDetailsServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂file
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChunkingService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EncryptionService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FileService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂loadbalancer
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FCFSStrategy.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LoadBalancerService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜PriorityStrategy.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜RoundRobinStrategy.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂monitoring
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜HealthCheckService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MetricsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂utils
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Constants.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityUtil.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CloudStoreApplication.java
 ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┃ ┣ 📜application-dev.yml
 ┃ ┃ ┃ ┃ ┣ 📜application-prod.yml
 ┃ ┃ ┃ ┃ ┣ 📜application.yml
 ┃ ┃ ┃ ┃ ┗ 📜schema.sql
 ┃ ┃ ┗ 📂test
 ┃ ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileControllerTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoadBalancerControllerTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoadBalancerServiceTest.java
 ┃ ┣ 📂target
 ┃ ┃ ┣ 📂classes
 ┃ ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtConfig.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RateLimitConfig.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityConfig.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthController$AuthenticationRequest.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthController$ChangePasswordRequest.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthController$RefreshTokenRequest.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthController$RegistrationRequest.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AuthController.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtAuthenticationException.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JwtAuthenticationFilter.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDTO.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLog$ActionType.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLog$AuditLogBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLog.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Container$ContainerBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Container$ContainerStatus.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Container.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileChunk$ChunkStatus.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileChunk$FileChunkBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileChunk.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileMetadata$FileMetadataBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileMetadata$FileStatus.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileMetadata$SharingPermission.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileMetadata.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User$ProfileField.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User$UserBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User$UserRole.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜User.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditLogRepository.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerRepository.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileRepository.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂security
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂event
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationFailureEvent.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationSuccessEvent.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PasswordChangeEvent.class
 ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂auth
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthService.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JwtService.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TokenBlacklistService.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDetailsServiceImpl.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂file
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EncryptionService$KeyManager.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜EncryptionService.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂loadbalancer
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂monitoring
 ┃ ┃ ┃ ┃ ┃ ┣ 📂utils
 ┃ ┃ ┃ ┃ ┃ ┗ 📜CloudStoreApplication.class
 ┃ ┃ ┃ ┣ 📜application-dev.yml
 ┃ ┃ ┃ ┣ 📜application-prod.yml
 ┃ ┃ ┃ ┣ 📜application.yml
 ┃ ┃ ┃ ┗ 📜schema.sql
 ┃ ┃ ┣ 📂generated-sources
 ┃ ┃ ┃ ┗ 📂annotations
 ┃ ┃ ┣ 📂generated-test-sources
 ┃ ┃ ┃ ┗ 📂test-annotations
 ┃ ┃ ┗ 📂test-classes
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┗ 📜pom.xml
 ┣ 📂docker
 ┃ ┣ 📂load-balancer
 ┃ ┃ ┗ 📜Dockerfile
 ┃ ┣ 📂secrets
 ┃ ┃ ┣ 📜mysql_root_password
 ┃ ┃ ┗ 📜mysql_user_password
 ┃ ┣ 📂storage-container
 ┃ ┃ ┗ 📜Dockerfile
 ┃ ┗ 📜docker-compose.yml
 ┣ 📂docs
 ┃ ┣ 📜DB_SCHEMA.md
 ┃ ┣ 📜KEY_DEV.md
 ┃ ┣ 📜LoadBalancerService.java
 ┃ ┗ 📜test.java
 ┣ 📂frontend
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂gui
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AdminController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜DashboardController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoginController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜GuiUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜MainApplication.java
 ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┃ ┗ 📜styles.css
 ┃ ┃ ┃ ┃ ┗ 📂fxml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜admin.xml
 ┃ ┃ ┃ ┃ ┃ ┣ 📜dashboard.fxml
 ┃ ┃ ┃ ┃ ┃ ┗ 📜login.fxml
 ┃ ┃ ┗ 📂test
 ┃ ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂gui
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoginControllerTest.java
 ┃ ┣ 📂target
 ┃ ┃ ┣ 📂classes
 ┃ ┃ ┃ ┣ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┗ 📂gui
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┣ 📂css
 ┃ ┃ ┃ ┃ ┗ 📜styles.css
 ┃ ┃ ┃ ┣ 📂fxml
 ┃ ┃ ┃ ┃ ┣ 📜admin.xml
 ┃ ┃ ┃ ┃ ┣ 📜dashboard.fxml
 ┃ ┃ ┃ ┃ ┗ 📜login.fxml
 ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┣ 📂generated-sources
 ┃ ┃ ┃ ┗ 📂annotations
 ┃ ┃ ┣ 📂generated-test-sources
 ┃ ┃ ┃ ┗ 📂test-annotations
 ┃ ┃ ┗ 📂test-classes
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┗ 📂gui
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂controller
 ┃ ┗ 📜pom.xml
 ┣ 📂scripts
 ┃ ┣ 📜init-containers.sh
 ┃ ┣ 📜run-tests.sh
 ┃ ┗ 📜setup-databases.sh
 ┣ 📂shared
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂main
 ┃ ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂shared
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂constants
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileOperationStatus.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SystemConstants.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRoles.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerStatusDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exceptions
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthenticationException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileStorageException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜LoadBalancerException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileChunkMetadata.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜StorageContainer.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂security
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜EncryptionUtil.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileEncryptor.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜PasswordValidator.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂validation
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileValidationService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserValidationService.java
 ┃ ┃ ┗ 📂resources
 ┃ ┣ 📂target
 ┃ ┃ ┣ 📂classes
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂cloudstore
 ┃ ┃ ┃ ┃ ┃ ┗ 📂shared
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂constants
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileOperationStatus.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRoles.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerStatusDTO$ContainerStatusDTOBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ContainerStatusDTO.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FileDTO$FileDTOBuilder.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FileDTO.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exceptions
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FileStorageException.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂security
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜EncryptionUtil.class
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂validation
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FileValidationService.class
 ┃ ┃ ┣ 📂generated-sources
 ┃ ┃ ┃ ┗ 📂annotations
 ┃ ┃ ┣ 📂generated-test-sources
 ┃ ┃ ┃ ┗ 📂test-annotations
 ┃ ┃ ┗ 📂test-classes
 ┃ ┗ 📜pom.xml
 ┣ 📜.gitignore
 ┣ 📜DEVELOPMENT-WORKFLOW.md
 ┣ 📜pom.xml
 ┣ 📜PROJECT_STRUCTURE.md
 ┗ 📜README.md