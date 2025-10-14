# TanEdu – Rendszerterv

## 1. A rendszer célja
A rendszer célja, hogy a hallgatók, oktatók és adminisztrátorok számára egy központi, 
biztonságos és felhasználóbarát felületet biztosítson a tanulmányi ügyek kezelésére 
(pl. kurzusfelvétel, jegyek nyomon követése, adminisztrációs feladatok).

---

## 2. Funkcionális követelmények

### 2.1 Hallgatói modul
- Bejelentkezés és profil kezelés
- Kurzusfelvétel és órarend megtekintés
- Érdemjegyek nyomon követése
- Kommunikáció az oktatókkal és adminisztrátorokkal

### 2.2 Oktatói modul
- Kurzusok létrehozása és szerkesztése
- Hallgatói jelenlét és jegyek kezelése
- Üzenetküldés hallgatóknak

### 2.3 Adminisztrátori modul
- Felhasználók (hallgatók, oktatók) kezelése
- Kurzusok és tantárgyak nyilvántartása
- Jogosultságkezelés
- Riportok készítése

---

## 3. Nem funkcionális követelmények
- **Használhatóság:** reszponzív, mobilbarát felület
- **Biztonság:** jelszó titkosítás, jogosultsági szintek
- **Megbízhatóság:** magas rendelkezésre állás
- **Teljesítmény:** több száz párhuzamos felhasználó támogatása
- **Bővíthetőség:** moduláris architektúra

---

## 4. Architektúra
- **Frontend:** webes kliens (pl. React / Angular)
- **Backend:** REST API (Spring Boot)
- **Adatbázis:** relációs adatbázis (pl. PostgreSQL / MySQL)
- **Hitelesítés:** JWT alapú autentikáció

---

## 6. Jogosultságkezelés
- **Hallgató/Diák:** saját adatok és tantárgy kezelése
- **Oktató:** saját tantárgyak és hallgatók kezelése
- **Rendszergazda:** teljes rendszer feletti kontroll

---

## 7. Tesztelési terv
- **Unit tesztek:** backend logika ellenőrzése
- **Integrációs tesztek:** API végpontok
- **UI tesztek:** felhasználói felület funkciói
- **Funkcionális tesztek:** tipikus felhasználói folyamatok (pl. kurzusfelvétel, jegybeírás)

---