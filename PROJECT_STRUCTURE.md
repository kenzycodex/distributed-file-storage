# Distributed File Storage - Project Structure

```
📦 distributed-file-storage
├── 📂 docker
│   ├── 📂 load-balancer
│   │   └── 📄 Dockerfile
│   ├── 📂 storage-container
│   │   └── 📄 Dockerfile
│   └── 📄 docker-compose.yml
│
├── 📂 src
│   ├── 📂 main
│   │   ├── 📂 java/com/cloudstore
│   │   │   ├── 📄 CloudStoreApplication.java
│   │   │   ├── 📂 config
│   │   │   │   ├── 📄 SecurityConfig.java
│   │   │   │   ├── 📄 AsyncConfig.java
│   │   │   │   ├── 📄 SwaggerConfig.java
│   │   │   │   └── 📄 WebConfig.java
│   │   │   ├── 📂 controller
│   │   │   │   ├── 📄 AuthController.java
│   │   │   │   ├── 📄 FileController.java
│   │   │   │   ├── 📄 AdminController.java
│   │   │   │   └── 📄 LoadBalancerController.java
│   │   │   ├── 📂 model
│   │   │   │   ├── 📂 entity
│   │   │   │   │   ├── 📄 User.java
│   │   │   │   │   ├── 📄 FileMetadata.java
│   │   │   │   │   ├── 📄 FileChunk.java
│   │   │   │   │   ├── 📄 Container.java
│   │   │   │   │   └── 📄 AuditLog.java
│   │   │   │   └── 📂 dto
│   │   │   │       ├── 📄 UserDTO.java
│   │   │   │       ├── 📄 FileDTO.java
│   │   │   │       └── 📄 ContainerStatusDTO.java
│   │   │   ├── 📂 repository
│   │   │   │   ├── 📄 UserRepository.java
│   │   │   │   ├── 📄 FileRepository.java
│   │   │   │   └── 📄 ContainerRepository.java
│   │   │   ├── 📂 service
│   │   │   │   ├── 📂 auth
│   │   │   │   │   ├── 📄 AuthService.java
│   │   │   │   │   └── 📄 JwtService.java
│   │   │   │   ├── 📂 file
│   │   │   │   │   ├── 📄 FileService.java
│   │   │   │   │   ├── 📄 ChunkingService.java
│   │   │   │   │   └── 📄 EncryptionService.java
│   │   │   │   ├── 📂 loadbalancer
│   │   │   │   │   ├── 📄 LoadBalancerService.java
│   │   │   │   │   ├── 📄 RoundRobinStrategy.java
│   │   │   │   │   ├── 📄 FCFSStrategy.java
│   │   │   │   │   └── 📄 PriorityStrategy.java
│   │   │   │   └── 📂 monitoring
│   │   │   │       ├── 📄 HealthCheckService.java
│   │   │   │       └── 📄 MetricsService.java
│   │   │   └── 📂 util
│   │   │       ├── 📄 Constants.java
│   │   │       └── 📄 SecurityUtil.java
│   │   └── 📂 resources
│   │       ├── 📄 application.yml
│   │       ├── 📄 application-dev.yml
│   │       └── 📄 application-prod.yml
│   └── 📂 test
│       └── 📂 java/com/cloudstore
│           ├── 📂 service
│           │   ├── 📄 FileServiceTest.java
│           │   └── 📄 LoadBalancerServiceTest.java
│           └── 📂 controller
│               ├── 📄 FileControllerTest.java
│               └── 📄 LoadBalancerControllerTest.java
│
├── 📂 frontend
│   ├── 📂 src
│   │   ├── 📂 main
│   │   │   ├── 📂 java/com/cloudstore/gui
│   │   │   │   ├── 📄 MainApplication.java
│   │   │   │   ├── 📂 controller
│   │   │   │   │   ├── 📄 LoginController.java
│   │   │   │   │   ├── 📄 DashboardController.java
│   │   │   │   │   └── 📄 AdminController.java
│   │   │   │   └── 📂 util
│   │   │   │       └── 📄 GuiUtils.java
│   │   │   └── 📂 resources
│   │   │       ├── 📂 fxml
│   │   │       │   ├── 📄 login.fxml
│   │   │       │   ├── 📄 dashboard.fxml
│   │   │       │   └── 📄 admin.fxml
│   │   │       └── 📂 css
│   │   │           └── 📄 styles.css
│   │   └── 📂 test
│   │       └── 📂 java/com/cloudstore/gui
│   │           └── 📂 controller
│   │               └── 📄 LoginControllerTest.java
│   └── 📄 pom.xml
│
├── 📂 scripts
│   ├── 📄 setup-databases.sh
│   ├── 📄 init-containers.sh
│   └── 📄 run-tests.sh
│
├── 📄 .gitignore
├── 📄 pom.xml
└── 📄 README.md
