# Project Structure – Arith Core Education (ACE)

--- 

## Objetivo
Um sistema de educação que visa oferecer todo controle e acesso aos usuarios com praticidade

---
---

## Root Structure

```
com.ace
├── student
├── teacher
├── class
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
│   │   ├── Note.java
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
│   └── search
│       └── StudentElasticRepository.java
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

## 🏫 MODULE: CLASS

```
class
├── domain
│   ├── entity
│   │   ├── Class.java
│   │   └── Subject.java
│   ├── repository
│   │   └── ClassRepository.java
│   └── exception
│       ├── ClassFullException.java
│       └── TeacherAlreadyAssignedException.java
│
├── application
│   └── usecase
│       ├── CreateClassUseCase.java
│       └── EnrollStudentUseCase.java
│
├── interface
│   └── graphql
│       └── ClassResolver.java
│
└── infrastructure
│   └── persistence
│       └── ClassJpaRepository.java

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

## 🧱 SHARED (CROSS-CUTTING)

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
