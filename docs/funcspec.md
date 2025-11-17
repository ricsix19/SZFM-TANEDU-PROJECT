# Funkcionális specifikáció — TanEdu

## 1. Bevezetés  

### 1.1 Cél  
A rendszer célja, hogy oktatási környezetben működő tanulmányi adminisztrációs felületet biztosítson: diák adatok kezelése, tantárgyak, órarend, jegyek, üzenetek, valamint tanári és adminisztratív funkciók támogatása.  

### 1.2 Hatókör  
- Diák felület (bejelentkezés, adatmegjelenítés stb.)  
- Oktatói felület (órarend kezelése, jegybeírás, vizsgák kiírása, üzenetek)  
- Adminisztrátori modul (felhasználói jogosultságok, intézményi beállítások)  
- Értesítések / üzenetküldés  
- Adatkezelés és jelentések  

### 1.3 Definíciók, rövidítések  
- **Diák**: aki felhasználóként be tud lépni  
- **Tantárgy**: kurzus, amelyhez órák kapcsolódnak  
- **Órarend**: heti beosztás tantárgyakkal, termekkel  
- **Jegy**: Témazáró vagy szimpla dolgozat értékelés  

---

## 2. Felhasználói szerepek és jogosultságok  

| Szerep        | Jogosultságok / tevékenységek |
|---------------|--------------------------------|
| Diák      | Tantárgyfelvétel, órarend megtekintése, jegyek, személyes adatok kezelése, témazárók megtekintése |
| Oktató        | Tantárgyakhoz órák hozzárendelése, jegybeírás, témazárók kiírása, üzenetek kezelése |
| Osztályfőnök | Csoportos műveletek, statisztikák, üzenetek csoportnak |
| Rendszergazda         | Felhasználók kezelése, jogosultságok, intézményi beállítások, rendszerszintű konfiguráció |

---

## 3. Funkcióleírások  

### 3.1 Bejelentkezés / regisztráció  
- Bejelentkezés (felhasználónév(ID alapú) / jelszó)  
- Új hallgató/diák regisztrációja előzetes személyes beiratkozás után (Rendszergazda készíti el a hallgató/diák fiókját)
- Jelszó visszaállítás

### 3.2 Személyes profil  
- Név, hallgatói azonosító/OEM azonosító, szak/képzés adatok  
- Elérhetőségek (email, telefonszám)  
- Értesítési beállítások  

### 3.3 Tantárgyak és kurzusok  
- Tantárgy létrehozása / szerkesztése / törlése (Rendszergazda jogkör) 
- Tantárgyak diákokhoz rendelése

### 3.4 Órarend kezelése  
- Órák időpontjának és teremének kiosztása  
- Ütközés-ellenőrzés  
- Diákoknak órarend generálása  

### 3.7 Jegykezelés  
- Jegyek felvitele, módosítása (oktató)  
- Jegyek megtekintése (diák)  
- Statisztikák, átlagok  

### 3.8 Értesítések / üzenetküldés  
- Rendszerüzenetek (pl. jegybeírás)  
- Oktató–diák üzenetküldés  
  
### 3.9 Admin funkciók  
- Felhasználók kezelése  
- Rendszerparaméterek (max kreditek, szabályok)  
- Naplózás / audit  
- Mentés, visszaállítás  

---

## 4. Nem-funkcionális követelmények  

- **Biztonság**: jelszókezelés, jogosultságkezelés  
- **Teljesítmény**: gyors válaszidő, cache  
- **Használhatóság**: áttekinthető UI, reszponzív felület  
- **Bővíthetőség**: moduláris kialakítás  
- **Adatbiztonság**: adatmentés és visszaállítás lehetősége  

---

## 5. Use case-ek  

| Use case         | Szereplő | Leírás | Előfeltételek | Eredmény |
|------------------|----------|--------|---------------|----------|
| Bejelentkezés    | Diák | Diák bejelentkezik a rendszerbe | Létezik felhasználói fiók | Sikeres belépés |
| Tantárgyfelvétel | Diák/Rendszergazda | Rendszergazda hozzárendeli a diákot a tárgyhoz | Tantárgy osztályonként különbözik, nincs időbeli ütközés | Tantárgy hozzárendelődik az adott diákhoz |
| Oktató hozzárendelés tárgyhoz | Oktató/Rendszergazda | A Rendszergazda hozzárendeli az oktatót a kurzushoz mint kurzus vezető | Az oktató adminisztraciós jellegű jogokkal rendelkezik | Oktató adminisztrátori jogosultságokkal rendelkezik a saját kirendelt kurzusában |
| Érdemjegy felvitele  | Oktató   | Oktató rögzíti a diák jegyét | Hallgató/Diák sikeresen teljesítette a kurzus által megadott feltételeket | Jegy megjelenik a  Hallgató/Diák profilján |

---