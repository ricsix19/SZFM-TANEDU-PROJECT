# TanEDU – Tanulmányi Rendszer 🎓📚

A **TanEDU** egy modern, moduláris, full-stack tanulmányi rendszer, amely célja, hogy egyszerűbbé és átláthatóbbá tegye a diákok, tanárok és adminisztrátorok mindennapi tanulmányi folyamatait.

A projekt két részből épül fel:
- **Backend**: Java Spring Boot alapú REST API
- **Frontend**: React + TypeScript felhasználói felület

A rendszer könnyen bővíthető, biztonságos és jól skálázható architektúrára épül.

---

## 🚀 Fő funkciók

### 👥 Felhasználókezelés
- Tanár / diák / Rendszergazda szerepkörök  
- Bejelentkezés és offline alapú regisztráció (Rendszergazda hozza létre a diák fiókját)
- JWT alapú hitelesítés
- Biztonságos hozzáféréskezelés (RBAC)

### 📘 Kurzus- és tantárgykezelés
- Kurzusok létrehozása és listázása  
- Tanárok hozzárendelése tantárgyakhoz  
- Diákok kurzusra jelentkezése  

### 📝 Jegykezelés és statisztikák
- Jegyek rögzítése (tanár)
- Jegyek, átlagok és teljesítmény mutatók megtekintése
- Tantárgyi statisztikai összesítések

### 🛠 Admin funkciók
- Felhasználók, tantárgyak, kurzusok teljes CRUD műveletei  
- Rendszerjogosultságok kezelése  

### 🎨 Modern felhasználói felület
- React + TypeScript alapokra építve  
- Reszponzív, modern dizájn  
- Diákbarát kurzus- és jegynézet  

---

## 🧑‍💻 Technológiai Stack

### **Frontend**
- React + TypeScript  
- Axios (API kommunikáció)
- React Router  

### **Backend**
- Java 17  
- Spring Boot 3  
- Spring Security (JWT alapú hitelesítés)
- Spring Data JPA + Hibernate  
- PostgreSQL adatbázis  
- Lombok  

### Infrastruktúra / Eszközök
- Maven  
- GitHub version control  
- .env alapú konfigurációkezelés  

---

## 🔐 Biztonság

- **JWT alapú hitelesítés**
- **Role-Based Access Control (RBAC)**
- **Biztonságos jelszóhash-elés (BCrypt)**
- **Globális hibakezelés**
- **CORS konfigurálás a frontend számára**

---

## ⚙️ Konfiguráció (.env használata)

A backend konfigurációinak nagy része **környezeti változókból** származik `.env` vagy rendszer-szintű environment változókon keresztül.

## 🚀 Full-Stack indítás

### 1️⃣ Backend indítása

```bash
git clone https://github.com/ricsix19/SZFM-TANEDU-BACKEND
cd SZFM-TANEDU-BACKEND/backend
mvn spring-boot:run
```
### 2️⃣ Frontend indítása

```bash
git clone https://github.com/FrostyFreet/SZFM-TANEDU-FRONTEND
cd SZFM-TANEDU-FRONTEND
npm install
npm run dev
```