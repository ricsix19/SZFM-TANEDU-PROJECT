# TanEDU – Rendszerterv

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

### 6.1 Felhasználói regisztráció kezelése

A TanEDU rendszerben a felhasználói fiókok **nem online önregisztráció** útján jönnek létre.  
A hallgatók és oktatók hozzáférését az intézmény **rendszergazdája (SYSADMIN)** hozza létre és kezeli.

---

#### Indoklás

Mivel a rendszer oktatási környezetben működik, a felhasználói kör **előre ismert és nyilvántartott** (iskolai adminisztráció alapján).  
Az online, nyilvános regisztráció:

- **biztonsági kockázatot jelentene**, mivel illetéktelen felhasználók is hozzáférést szerezhetnének,
- **feleslegesen növelné az adminisztrációt**, pl. e-mail-hitelesítés és szerepkör jóváhagyása,
- **ellentétes lenne** az intézményben alkalmazott **központi csoport- és szerepkör-kezeléssel**.

Ezért a felhasználói fiókok létrehozása **központosított és ellenőrzött folyamat** keretében történik.

---

#### Működési folyamat

1. A hallgatók és oktatók adatai az intézményi adminisztrációs rendszerből kerülnek átvételre.
2. A rendszergazda az alábbi adatok alapján létrehozza a felhasználói fiókot:
   - teljes név
   - e-mail cím
   - szerepkör *(hallgató / oktató / osztályfőnök / admin)*
   - szervezeti egység vagy osztály
3. A rendszer automatikusan **kezdő jelszót** generál.
4. Az első bejelentkezéskor a felhasználó **kötelező jelszóváltoztatást** hajt végre a biztonság növelése érdekében.

---

#### Nem célok

A rendszer **nem** biztosít:

- nyilvános felületet **önálló online regisztrációhoz**,
- külső szolgáltatók alapú hitelesítést *(Google / Facebook / SSO)*,
- automatikus szerepkör- vagy jogosultság-hozzárendelést felhasználói adatmegadás alapján.

---

## 7. Tesztelési terv
- **Unit tesztek:** backend logika ellenőrzése
- **Integrációs tesztek:** API végpontok
- **UI tesztek:** felhasználói felület funkciói
- **Funkcionális tesztek:** tipikus felhasználói folyamatok (pl. kurzusfelvétel, jegybeírás)

---