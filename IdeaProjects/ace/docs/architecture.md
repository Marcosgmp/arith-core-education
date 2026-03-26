# Project Structure – Arith Core Education (ACE)

---

## Objective
Arith Core Education (ACE) is an educational management system designed to provide full academic and administrative control with practicality, security, and scalability. The architecture follows **Clean Architecture principles**, ensuring separation of concerns, domain isolation, and long-term maintainability.

---

## Architectural Principles

- Clean Architecture
- Layered Architecture
- Modular Monolith
- Domain-driven design (DDD-inspired)
- GraphQL as Interface Adapter
- Framework-agnostic Domain

> Controllers are intentionally not used. **GraphQL Resolvers act as Controllers**, following the Interface Adapters layer.

---

## Root Structure

```
com.ace
├── student
├── teacher
├── classroom
├── auth
├── report
├── notification
├── audit
├── shared
└── AceApplication.java
```

---

## MODULE: STUDENT

```
student
├── domain
│   ├── entity
│   │   ├── Student.java
│   │   ├── Grade.java
│   │   ├── Attendance.java
│   │   └── AcademicHistory.java
│   │
│   ├── valueobject
│   │   ├── Enrollment.java
│   │   ├── GPA.java
│   │   └── AcademicPeriod.java
│   │
│   ├── enums
│   │   ├── StudentStatus.java
│   │   └── AssessmentType.java
│   │
│   ├── repository
│   │   └── StudentRepository.java
│   │
│   ├── port
│   │   ├── NotificationPort.java
│   │   └── AuditPort.java
│   │
│   └── exception
│       ├── StudentNotFoundException.java
│       ├── InvalidEnrollmentException.java
│       └── InvalidGradeException.java
│
├── application
│   ├── usecase
│   │   ├── CreateStudentUseCase.java
│   │   ├── PostGradeUseCase.java
│   │   ├── CalculateGPAUseCase.java
│   │   └── ViewReportCardUseCase.java
│   │
│   └── service
│       └── StudentApplicationService.java
│
├── interface
│   └── graphql
│       ├── resolver
│       │   ├── StudentQueryResolver.java
│       │   └── StudentMutationResolver.java
│       │
│       ├── input
│       │   ├── CreateStudentInput.java
│       │   └── PostGradeInput.java
│       │
│       └── dto
│           ├── StudentDTO.java
│           └── ReportCardDTO.java
│
├── infrastructure
│   ├── persistence
│   │   ├── jpa
│   │   │   ├── StudentJpaEntity.java
│   │   │   └── StudentJpaRepository.java
│   │   └── mapper
│   │       └── StudentMapper.java
│   │
│   ├── search
│   │   └── StudentElasticRepository.java
│   │
│   └── adapter
│       ├── EmailNotificationAdapter.java
│       └── AuditEventAdapter.java
│
└── StudentModuleConfig.java
```

---

## MODULE: TEACHER

```
teacher
├── domain
│   ├── entity
│   │   └── Teacher.java
│   ├── enums
│   │   └── Degree.java
│   ├── repository
│   │   └── TeacherRepository.java
│   └── exception
│       └── TeacherNotFoundException.java
│
├── application
│   └── usecase
│       ├── CreateTeacherUseCase.java
│       └── AssignClassUseCase.java
│
├── interface
│   └── graphql
│       └── TeacherResolver.java
│
└── infrastructure
│   └── persistence
│       └── TeacherJpaRepository.java
```

---

## MODULE: CLASSROOM

```
classroom
├── domain
│   ├── entity
│   │   ├── Classroom.java
│   │   └── Subject.java
│   ├── repository
│   │   └── ClassroomRepository.java
│   └── exception
│       ├── ClassroomFullException.java
│       └── TeacherAlreadyAssignedException.java
│
├── application
│   └── usecase
│       ├── CreateClassroomUseCase.java
│       └── EnrollStudentUseCase.java
│
├── interface
│   └── graphql
│       └── ClassroomResolver.java
│
└── infrastructure
│   └── persistence
│       └── ClassroomJpaRepository.java
```

---

## MODULE: AUTH

```
auth
├── domain
│   ├── entity
│   │   └── User.java
│   ├── enums
│   │   └── Role.java
│   ├── repository
│   │   └── UserRepository.java
│   └── exception
│       ├── InvalidCredentialsException.java
│       └── BlockedUserException.java
│
├── application
│   └── usecase
│       ├── LoginUseCase.java
│       └── ValidateTokenUseCase.java
│
├── interface
│   └── graphql
│       └── AuthMutationResolver.java
│
└── infrastructure
│   ├── security
│   │   ├── JwtTokenProvider.java
│   │   └── JwtAuthenticationFilter.java
│   └── persistence
│       └── UserJpaRepository.java
```

---

## MODULE: REPORT

```
report
├── application
│   └── usecase
│       ├── GenerateReportCardPDFUseCase.java
│       └── GeneratePerformanceReportUseCase.java
├── interface
│   └── graphql
│       └── ReportQueryResolver.java
└── infrastructure
│   └── elasticsearch
│       └── ReportSearchRepository.java
```

---

## MODULE: NOTIFICATION

```
notification
├── application
│   └── usecase
│       └── SendEmailUseCase.java
├── infrastructure
│   └── mail
│       └── EmailService.java
```

---

## MODULE: AUDIT

```
audit
├── domain
│   └── AuditEvent.java
├── application
│   └── usecase
│       └── RecordAuditUseCase.java
└── infrastructure
    ├── mongo
    │   └── AuditMongoRepository.java
    └── listener
        └── AuditEventListener.java
```

---

## SHARED (CROSS-CUTTING)

```
shared
├── exception
│   ├── BusinessException.java
│   ├── EntityNotFoundException.java
│   ├── PermissionDeniedException.java
│   └── GlobalExceptionHandler.java
│
├── util
│   ├── DateUtils.java
│   └── PaginationUtils.java
│
├── events
│   └── DomainEvent.java
└── constants
    └── SecurityConstants.java
```

---

## Request Flow Summary

1. Client sends GraphQL Query/Mutation
2. Resolver validates input and acts as Controller
3. UseCase executes business logic
4. Domain enforces rules and invariants
5. Repositories persist data via Infrastructure
6. Domain Events trigger Notification and Audit asynchronously

---

## Final Notes

- Domain layer has **no dependency on Spring or frameworks**
- GraphQL Resolvers replace traditional Controllers
- Architecture supports future migration to microservices
- Current stage represents a production-ready foundation
