# API Dokumentáció

## Tartalomjegyzék
- [Authentication API](#authentication-api)
- [User API](#user-api)
- [Department API](#department-api)
- [Course API](#course-api)
- [Grade API](#grade-api)
- [Attendance API](#attendance-api)
- [Message API](#message-api)
- [Upload API](#upload-api)

---

## Authentication API

**Base URL:** `/api/auth`

Az Auth API a felhasználói autentikáció és regisztráció kezelésére szolgál.

### Endpointok

#### 1. Bejelentkezés
- **Endpoint:** `POST /api/auth/login`
- **Leírás:** Bejelentkezés e-mail és jelszó alapján. Sikeres bejelentkezéskor JWT token generálódik.

**Request:**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**
```json
{
  "token": "jwt-token-here",
  "type": "Bearer",
  "username": "user@example.com"
}
```

#### 2. Regisztráció
- **Endpoint:** `POST /api/auth/register`
- **Leírás:** Új felhasználó regisztrálása.

**Request:**
```json
{
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "password": "password123",
  "role": "STUDENT",
  "subject": "Mathematics",
  "department": { "id": 1 },
  "classLeaderOf": "10/A",
  "birthDate": "2000-01-01"
}
```

**Megjegyzések:**
- `subject`: csak TEACHER esetén kötelező
- `department`: opcionális
- `classLeaderOf`: csak CLASSLEADER esetén
- `birthDate`: opcionális

**Response (201 Created):**
```json
{
  "message": "User registered successfully"
}
```

#### 3. Token ellenőrzés
- **Endpoint:** `GET /api/auth/check-token`
- **Leírás:** Ellenőrzi a JWT token érvényességét.
- **Headers:** `Authorization: Bearer <jwt-token>`

**Response (200 OK):**
```json
{
  "valid": true,
  "expired": false
}
```

#### 4. Kijelentkezés
- **Endpoint:** `POST /api/auth/logout`
- **Leírás:** Kijelentkezteti a felhasználót, eltávolítja a session-t.

**Response (200 OK):**
```json
{
  "message": "Successfully logged out"
}
```

#### 5. Jelszó módosítása
- **Endpoint:** `PUT /api/auth/change-password`
- **Leírás:** Jelszó módosítása az aktuális bejelentkezett felhasználó számára.

**Request:**
```json
{
  "password": "newPassword123"
}
```

---

## User API

**Base URL:** `/api/users`

A User API a felhasználók kezelésére szolgál, beleértve a felhasználók lekérdezését, frissítését, törlését, valamint a tanárok és diákok adatait.

### DTO-k

#### UserDTO
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "birthDate": "2000-01-01",
  "role": "STUDENT",
  "departmentName": "Science",
  "deptId": 1,
  "fullName": "John Doe",
  "subject": "Mathematics",
  "classLeaderOf": "10/A"
}
```

**Mezők:**
- `id`: Felhasználó azonosító
- `firstName` / `lastName`: Kereszt- és vezetéknév
- `email`: E-mail cím
- `birthDate`: Születési dátum
- `role`: Szerepkör (STUDENT, TEACHER, SYSADMIN, CLASSLEADER)
- `departmentName` / `deptId`: Tanszék neve és ID-ja
- `fullName`: Teljes név
- `subject`: Tantárgy (csak tanároknak)
- `classLeaderOf`: Osztályfőnök által vezetett osztály (ha van)

#### TeacherEmailDTO
```json
{
  "email": "teacher@example.com",
  "subject": "Mathematics"
}
```

### Endpointok

#### 1. Összes felhasználó lekérdezése
- **Endpoint:** `GET /api/users/getAll`
- **Response (200 OK):** Lista `UserDTO` objektumokkal

#### 2. Összes tanár e-mail lekérdezése
- **Endpoint:** `GET /api/users/getAllTeachersEmail`
- **Response (200 OK):** Lista `TeacherEmailDTO` objektumokkal

#### 3. Összes diák e-mail lekérdezése
- **Endpoint:** `GET /api/users/getAllStudentsEmail`
- **Response (200 OK):** Lista stringekkel (e-mail címek)

#### 4. Összes tantárgy lekérdezése
- **Endpoint:** `GET /api/users/getAllSubjects`
- **Response (200 OK):** Lista `Subject` objektumokkal

#### 5. Összes elérhető tantárgy neve
- **Endpoint:** `GET /api/users/getAllAvailableSubjects`
- **Response (200 OK):** Lista stringekkel (tantárgynevek)

#### 6. Diákok tanszék szerint
- **Endpoint:** `GET /api/users/getAllStudentsByDepartment/{departmentName}`
- **Response (200 OK):** Lista `UserDTO` objektumokkal

#### 7. Aktuális felhasználó adatai
- **Endpoint:** `GET /api/users/getCurrentUser`
- **Response (200 OK):** `UserDTO` objektum

#### 8. Aktuális felhasználó szerepköre
- **Endpoint:** `GET /api/users/getCurrentUserRole`
- **Response (200 OK):** Role string

#### 9. Felhasználó lekérdezése ID alapján
- **Endpoint:** `GET /api/users/getById/{id}`
- **Response (200 OK):** `UserDTO` objektum

#### 10. Felhasználó frissítése
- **Endpoint:** `PUT /api/users/update/{id}`
- **Request:** `User` objektum (JSON)
- **Response (200 OK):** Frissített `User` objektum

#### 11. Felhasználó törlése
- **Endpoint:** `DELETE /api/users/delete/{id}`
- **Response (200 OK):** `"User deleted successfully"`

---

## Department API

**Base URL:** `/api/departments`

A Department API a tanszékek kezelésére szolgál, beleértve a létrehozást, lekérdezést, frissítést és törlést.

### DTO

#### DepartmentDTO
```json
{
  "id": 1,
  "name": "Science",
  "courses": [],
  "students": [],
  "classLeader": {}
}
```

### Endpointok

#### 1. Tanszék létrehozása
- **Endpoint:** `POST /api/departments/create`

**Request:**
```json
{
  "name": "Science",
  "classLeader": { "id": 1 },
  "students": [ { "id": 101 }, { "id": 102 } ],
  "courses": [ { "id": 1 }, { "id": 2 } ]
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Science",
  "courses": [],
  "students": [],
  "classLeader": {}
}
```

#### 2. Összes tanszék lekérdezése
- **Endpoint:** `GET /api/departments/getAll`
- **Response (200 OK):** Lista `DepartmentDTO` objektumokkal

#### 3. Tanszék osztályfőnöke név alapján
- **Endpoint:** `GET /api/departments/getDepartmentClassLeader/{name}`
- **Response (200 OK):** String (osztályfőnök neve)

#### 4. Tanszék ID alapján
- **Endpoint:** `GET /api/departments/getById/{id}`
- **Response (200 OK):** `DepartmentDTO` objektum

#### 5. Tanszék törlése
- **Endpoint:** `DELETE /api/departments/deleteById/{id}`
- **Response (200 OK):** `"Department deleted successfully"`

#### 6. Tanszék nevének frissítése
- **Endpoint:** `PUT /api/departments/updateById/{id}`
- **Request:** String (új név)
- **Response (200 OK):** `"Department updated successfully"`

---

## Course API

**Base URL:** `/api/course`

A Course API a kurzusok kezelésére szolgál.

### DTO

#### CourseDTO
```json
{
  "id": 1,
  "name": "Mathematics",
  "day": "Monday",
  "duration": "08:00-10:00",
  "teacherName": "John Doe",
  "departmentName": "Science"
}
```

### Endpointok

#### 1. Összes kurzus lekérdezése
- **Endpoint:** `GET /api/course/getAll`
- **Response (200 OK):** Lista `CourseDTO` objektumokkal

#### 2. Kurzus ID alapján
- **Endpoint:** `GET /api/course/getById/{id}`
- **Response (200 OK):** `CourseDTO` objektum

#### 3. Aktuális tanár kurzusai
- **Endpoint:** `GET /api/course/getByCurrentTeacher`
- **Jogosultság:** TEACHER
- **Response (200 OK):** Lista `CourseDTO` objektumokkal

#### 4. Kurzus diákjai
- **Endpoint:** `GET /api/course/{courseId}/students`
- **Jogosultság:** TEACHER, SYSADMIN
- **Response (200 OK):** Lista diák objektumokkal

#### 5. Kurzusok tanszék szerint (aktuális felhasználónak)
- **Endpoint:** `GET /api/course/getCourseByDepartmentNameForCurrentUser`
- **Response (200 OK):** Lista `CourseDTO` objektumokkal

#### 6. Kurzusok tanszék szerint
- **Endpoint:** `GET /api/course/getCourseByDepartmentName`
- **Response (200 OK):** Lista `CourseDTO` objektumokkal

#### 7. Kurzus nevének frissítése
- **Endpoint:** `PUT /api/course/updateName/{id}`
- **Request:** String (új név)
- **Response (200 OK):** Frissített `CourseDTO` objektum

#### 8. Kurzus időtartamának frissítése
- **Endpoint:** `PUT /api/course/updateDuration/{id}`
- **Request:** String (pl. "10:00-12:00")
- **Response (200 OK):** Frissített `Course` objektum

#### 9. Kurzus napjának frissítése
- **Endpoint:** `PUT /api/course/updateDay/{id}`
- **Request:** String (pl. "Wednesday")
- **Response (200 OK):** Frissített `Course` objektum

#### 10. Kurzus létrehozása
- **Endpoint:** `POST /api/course/create`

**Request:**
```json
{
  "name": "Physics",
  "day": "Monday",
  "duration": "08:00-10:00",
  "teacher": { "id": 1 },
  "department": { "id": 1 }
}
```

**Response (200 OK):**
```json
{
  "message": "Course created successfully",
  "course": {
    "id": 3,
    "name": "Physics",
    "day": "Monday",
    "duration": "08:00-10:00",
    "teacherName": "John Doe",
    "departmentName": "Science"
  }
}
```

#### 11. Kurzus törlése
- **Endpoint:** `DELETE /api/course/deleteById/{id}`
- **Response (200 OK):** `"Course deleted successfully"`

---

## Grade API

**Base URL:** `/api/grade`

A Grade API a diákok jegyeinek kezelésére szolgál.

### DTO

#### GradeDTO
```json
{
  "id": 1,
  "value": 5,
  "subject": "Mathematics",
  "createdAt": "2025-11-17T10:30:00",
  "studentName": "John Doe",
  "teacherName": "Jane Smith"
}
```

### Endpointok

#### 1. Jegy létrehozása
- **Endpoint:** `POST /api/grade/create`

**Request:**
```json
{
  "value": 5,
  "subject": "Mathematics",
  "student": { "id": 101 },
  "teacher": { "id": 1 }
}
```

**Response (200 OK):** `GradeDTO` objektum

#### 2. Diák jegyei ID alapján
- **Endpoint:** `GET /api/grade/getAllByStudentId/{id}`
- **Response (200 OK):** Lista `GradeDTO` objektumokkal

#### 3. Diák jegyei e-mail alapján
- **Endpoint:** `GET /api/grade/getAllGradesByStudentEmail/{email}`
- **Response (200 OK):** Lista `GradeDTO` objektumokkal

#### 4. Aktuális felhasználó jegyei
- **Endpoint:** `GET /api/grade/getAllByCurrentUser`
- **Response (200 OK):** Lista `GradeDTO` objektumokkal

#### 5. Jegyek tantárgy szerint
- **Endpoint:** `GET /api/grade/getAllGradesBySubject/{subject}`
- **Response (200 OK):** Lista számokkal (pl. `[5, 4, 3, 5]`)

#### 6. Jegy törlése
- **Endpoint:** `DELETE /api/grade/deleteGradeById/{id}`
- **Response (200 OK):** `"Grade deleted successfully"`

---

## Attendance API

**Base URL:** `/api/attendance`

Az Attendance API a tanulók jelenlétének kezelésére szolgál.

### DTO

#### AttendanceDTO
```json
{
  "id": 1,
  "date": "2025-11-17",
  "present": true,
  "studentId": 101,
  "studentName": "John Doe",
  "courseId": 1,
  "courseName": "Mathematics",
  "timeSlot": "08:00-10:00"
}
```

### Endpointok

#### 1. Jelenlét rögzítése
- **Endpoint:** `POST /api/attendance`
- **Jogosultság:** TEACHER

**Request:**
```json
{
  "courseId": 1,
  "date": "2025-11-17",
  "timeSlot": "08:00-10:00",
  "students": [
    {
      "studentId": 101,
      "present": true
    },
    {
      "studentId": 102,
      "present": false
    }
  ]
}
```

**Response (200 OK):**
```json
{
  "message": "Attendance saved successfully"
}
```

#### 2. Saját jelenlét lekérdezése
- **Endpoint:** `GET /api/attendance/my`
- **Response (200 OK):** Lista `AttendanceDTO` objektumokkal

#### 3. Jelenlét törlése
- **Endpoint:** `DELETE /api/attendance/{attendanceId}`
- **Jogosultság:** TEACHER, SYSADMIN
- **Response (200 OK):** `{ "message": "Attendance deleted successfully" }`

#### 4. Jelenlét kurzus és dátum szerint
- **Endpoint:** `GET /api/attendance/{courseId}`
- **Jogosultság:** TEACHER, STUDENT
- **Query Parameters:** `date` (kötelező), `timeSlot` (opcionális)
- **Response (200 OK):** Lista `AttendanceDTO` objektumokkal

---

## Message API

**Base URL:** `/api/messages`

A Message API a felhasználók közötti üzenetküldés kezelésére szolgál.

### DTO-k

#### MessageRequestDTO
```json
{
  "receiver": {
    "email": "receiver@example.com"
  },
  "message": "Hello!"
}
```

#### MessageResponseDTO
```json
{
  "id": 1,
  "message": "Hello!",
  "senderId": 101,
  "senderFullName": "John Doe",
  "senderEmail": "john@example.com",
  "receiverId": 102,
  "receiverFullName": "Jane Smith",
  "receiverEmail": "jane@example.com",
  "deptId": 1,
  "createdAt": "2025-11-17T10:30:00"
}
```

### Endpointok

#### 1. Üzenet küldése
- **Endpoint:** `POST /api/messages/sendMessage`

**Request:**
```json
{
  "receiver": { "email": "receiver@example.com" },
  "message": "Hello!"
}
```

**Response (200 OK):** `MessageResponseDTO` objektum

#### 2. Aktuális felhasználó üzenetei
- **Endpoint:** `GET /api/messages/getMessagesByCurrentUser`
- **Response (200 OK):** Lista `MessageResponseDTO` objektumokkal

#### 3. Tanszéki üzenetek lekérdezése
- **Endpoint:** `GET /api/messages/departments/{deptId}/messages`
- **Response (200 OK):** Lista `MessageResponseDTO` objektumokkal

#### 4. Üzenet küldése tanszékhez
- **Endpoint:** `POST /api/messages/departments/{deptId}/messages`

**Request:**
```json
{
  "message": "Meeting at 3 PM"
}
```

**Response (200 OK):** Lista `MessageResponseDTO` objektumokkal

---

## Upload API

**Base URL:** `/api/upload`

Az Upload API képek feltöltésére szolgál.

### Endpointok

#### 1. Kép feltöltése
- **Endpoint:** `POST /api/upload`
- **Content-Type:** `multipart/form-data`

**Request Parameters:**

| Paraméter | Típus | Leírás |
|-----------|-------|--------|
| `file` | MultipartFile | Feltöltendő kép |

**Response (200 OK):**
```json
{
  "url": "https://res.cloudinary.com/.../image.jpg"
}
```