# Code Review: piksa-1

**Date:** 2026-03-10
**Reviewer:** Claude Sonnet 4.6
**Project:** Quarkus REST application (Java 25, Maven)
**Scope:** Full project review

---

## Project Overview

Two REST endpoints:
- `GET /hello` — returns a greeting string
- `POST /items` — accepts an `Item` JSON payload and echoes it back

---

## Critical

### 1. Missing `@Valid` on `ItemResource.create()` — `ItemResource.java:17`

```java
// current
public Response create(Item item)

// should be
public Response create(@Valid Item item)
```

Without `@Valid`, Bean Validation constraints on `Item` are silently ignored. The endpoint accepts any input including nulls, empty strings, or garbage without any validation being triggered.

---

## High

### 2. No persistence in `ItemResource` — `ItemResource.java:17-20`

`create()` responds with `201 Created` but discards the posted item immediately. This is either a bug (missing persistence layer) or unfinished stub code. Should either:
- Wire up a persistence layer (Panache/JPA), or
- Add an explicit comment/TODO marking this as a demo stub.

### 3. No negative test cases — `ItemResourceTest.java`

Only the happy path is tested. Missing tests for:
- `null` fields in the request body
- empty/blank strings
- malformed JSON payload
- missing required fields
- expected 4xx error responses

---

## Medium

### 4. Null checks lack error messages — `Item.java:8-9`

```java
// current
Objects.requireNonNull(name)
Objects.requireNonNull(description)

// better
Objects.requireNonNull(name, "Item name cannot be null")
Objects.requireNonNull(description, "Item description cannot be null")
```

Without messages, a `NullPointerException` gives no diagnostic context.

### 5. No blank string validation — `Item.java`

`new Item("", "")` passes null checks but is semantically invalid. If blank names and descriptions are not meaningful, add validation:

```java
if (name.isBlank()) throw new IllegalArgumentException("Item name cannot be blank");
```

Or use `@NotBlank` from Bean Validation (effective once `@Valid` is added at the boundary).

### 6. No service layer — `ItemResource.java`

Business logic belongs in a dedicated service class (`ItemService`), not the resource boundary. For a small demo this is acceptable, but the boundary and business logic should be separated as the project grows.

---

## Low

### 7. Hardcoded greeting message — `GreetingResource.java:14`

The string `"Hello from Quarkus REST"` is hardcoded. For a configurable application, externalize it:

```java
@ConfigProperty(name = "greeting.message", defaultValue = "Hello from Quarkus REST")
String message;
```

### 8. No logging in `ItemResource` — `ItemResource.java`

POST requests leave no audit trail. Add at minimum a `Logger` for debug/info logging of incoming requests.

### 9. `application.properties` is empty

Logging level, HTTP port, and environment-specific configuration should be explicit rather than relying entirely on Quarkus defaults.

### 10. Java 25 target — `pom.xml:13`

Java 25 is pre-release. Java 21 is the current LTS and the stable choice for production or course material unless Java 25 features are specifically needed.

---

## Security Notes

- Both endpoints are unauthenticated and open. Acceptable for demo code — document this explicitly if intentional.
- No input sanitization beyond null checks. Low risk for current usage but relevant if strings are persisted or processed downstream.
- No CORS configuration. Quarkus defaults apply.

---

## Test Coverage Summary

| Category              | Status       |
|-----------------------|--------------|
| Happy path (GET)      | Covered      |
| Happy path (POST)     | Covered      |
| Null / invalid input  | Not tested   |
| Error responses (4xx) | Not tested   |
| Integration (packaged)| Present, minimal |

---

## Recommended Actions (Priority Order)

1. Add `@Valid` to `ItemResource.create(@Valid Item item)` — correctness issue, one-line fix.
2. Add blank-string validation to `Item` record (either guard clauses or `@NotBlank`).
3. Write negative tests: null fields, blank strings, malformed JSON, expected 4xx responses.
4. Either wire persistence into `ItemResource` or add a `// TODO: persistence not implemented` comment.
5. Add error messages to `Objects.requireNonNull()` calls.
