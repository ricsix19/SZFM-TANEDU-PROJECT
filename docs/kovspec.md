# TanEDU — Követelmény Specifikáció

### Áttekintés

Az alkalmazás célja, hogy diákok, oktatók és adminisztrátorok számára átlátható és könnyen kezelhető felületet biztosítson a tanulmányi ügyek intézésére.  
A rendszer támogatja a diákok jegyek megtekintését, az oktatók és diákok órarend kezelését és az oktatók jegykezelését, valamint az adminisztrátorok jogosultságkezelését és intézményi beállításait.  

A felület kialakításánál elsődleges szempont az átláthatóság, a stabil működés és a reszponzív megjelenés.  
A rendszer lehetővé teszi az értesítések és üzenetek továbbítását, valamint riportok és statisztikák készítését a diákok és oktatók teljesítményéről.  

### Jelenlegi helyzet

A jelenlegi oktatási adminisztrációs rendszerek sok esetben bonyolultak, nehezen átláthatók, és nem biztosítanak gyors hozzáférést a legfontosabb információkhoz.  
A diákok nehézkesen tudják kezelni az órarendjüket az oktatók pedig körülményesen rögzítik a jegyeket.  
Az adminisztratív feladatok (felhasználókezelés, jogosultságok kiosztása, riportok készítése) sokszor időigényesek és nem elég felhasználóbarátak.  

### Vágyálom rendszer

A projekt célja egy olyan webes rendszer létrehozása, amely:  
- Egyszerűsíti a diákok, az oktatók és adminisztrátorok tevékenységeit.  
- Átlátható felületet biztosít a legfontosabb tanulmányi adatokhoz.  
- Stabil, gyors és skálázható működést nyújt.  
- Támogatja a folyamatos kommunikációt (értesítések, üzenetek).  
- Automatikusan generál statisztikákat és riportokat a felhasználók számára.  

### Funkcionális követelmények

| Kategória       | Funkció                  | Leírás                                                                 |
|-----------------|--------------------------|------------------------------------------------------------------------|
| Felhasználói    | Bejelentkezés és profil  | Diákok, oktatók és adminisztrátorok bejelentkezhetnek, profiladatok kezelése. |
| Felhasználói    | Órarend megtekintése     | A diákok és oktatók saját órarendjüket megtekinthetik.              |
| Felhasználói    | Jegymegtekintés          | A diákok elérhetik témazáró és félév során kapott jegyeiket.                 |
| Oktatói         | Jegybeírás               | Oktatók rögzíthetik és módosíthatják a diákok jegyeit.             |
| Oktatói         | Üzenetek küldése          | Oktatók üzenetet tudnak küldeni diákjaiknak, hogy értesíthetsék őket esetleges témazárókról, dolgozatokról.  |
| Adminisztrációs | Jogosultságkezelés       | Az adminisztrátorok felhasználókat és jogosultságokat kezelnek.        |
| Adminisztrációs | Tantárgy- és kurzuskezelés | Új tantárgyak létrehozása, módosítása, törlése, Oktatók és Diákokat kisorolni kurzusaikhoz.                        |
| Adminisztrációs | Rendszerkonfiguráció     | Intézményi beállítások, szabályok rögzítése.            |
| Egyéb           | Értesítések, üzenetküldés| Felhasználók közti kommunikáció és automatikus rendszerüzenetek.       |
| Egyéb           | Riportok és statisztikák | Riportok, legfőbbkép statisztikák(átlag, etc.) készítése és megjelenítése |
| Egyéb           | Naplózás                 | A rendszer minden változást naplóz, biztosítva az átláthatóságot.      |

### A rendszerre vonatkozó törvények, rendeletek, szabványok

- GDPR és adatvédelmi előírások betartása (személyes adatok kezelése).  
- Elektronikus nyilvántartási előírások teljesítése.  
- ISO/IEC 27001 szabvány az információbiztonságra.  
- ISO/IEC 25010 szabvány a szoftver minőségi jellemzőire (használhatóság, megbízhatóság, teljesítmény).  

### Jelenlegi üzleti folyamatok modellje

1. Diák ügyintézés  
   - Témazáró dolgozatok figyelése és észben tartása 10+ tantárgynál körülményes.
   - Jegybeírás és -lekérdezés körülményes folyamat.  

2. Oktatói feladatok  
   - Jegybeírás papíralapon vagy elavult rendszeren keresztül.  
   - Témazáró dolgozatok megszervezése és kommunikálása lassú.  

3. Adminisztráció  
   - Felhasználókezelés manuális, táblázatos formában.  
   - Kurzusok és szabályok frissítése körülményes.  

### Igényelt üzleti folyamatok modellje

- **Automatizált tantárgyfelvétel**: a rendszer ellenőrzi az előfeltételeket, ütközéseket.  
- **Digitális jegykezelés**: oktatók egyszerűen és gyorsan rögzíthetik a jegyeket.  
- **Témazáró kezelés**: Témazárók kiírása digitálisan történik.  
- **Felhasználó- és jogosultságkezelés**: adminisztrátorok könnyedén kezelhetik a jogosultságokat.  
- **Értesítési rendszer**: automatikus üzenetek és figyelmeztetések küldése a felhasználóknak.  

### Követelmény lista

| ID | Cím | Leírás | Prioritás | Elfogadási kritérium |
|----|-----|--------|----------:|---------------------|
| K001 | Bejelentkezés / JWT auth | Email/jelszó alapú bejelentkezés, JWT token kiadása | Magas | Sikeres bejelentkezéskor érvényes JWT, védett endpointokhoz szükséges token |
| K002 | Regisztráció | Diák regisztráció (admin jóváhagyással vagy előregenerált fiókkal) | Magas | Új felhasználó felvétele megtörténik és beléphet, e-mail formátum validáció |
| K003 | Jogosultságkezelés | Szerepek: DIÁK, OKTATÓ, ADMIN; jogosultságok védik a műveleteket | Magas | Csak jogosult szerep hajthat végre admin/oktatói műveleteket |
| K004 | Jegykezelés | Oktatók jegyeket rögzíthetnek és módosíthatnak; diákok megtekintik jegyeiket | Magas | Jegy megjelenik diák profilján, módosítás naplózva |
| K005 | Órarend megtekintés | Diákok és oktatók látják az órarendjüket | Közepes | Heti nézet generálható és ütközés-ellenőrzés fut |
| K006 | Üzenetküldés | Oktatók és diákok üzeneteket küldhetnek; értesítések | Közepes | Üzenet kerül elmentésre, címzett megkapja, olvasottság nyomon követhető |
| K007 | Fájl feltöltés | Oktatók/diákok fájlokat tölthetnek fel (Cloudinary/ helyi tároló) | Közepes | Sikeres feltöltés URL-el tárolva, méret- és típusellenőrzés |
| K008 | Tantárgy- és kurzuskezelés | Admin létrehozza, módosítja, törli a kurzusokat és hozzárendeli oktatókat | Közepes | Kurzusról adatok lekérdezhetők, oktató hozzárendelés működik |
| K009 | Naplózás / Audit | Minden fontos művelet naplózása (CRUD) | Közepes | Naplókban szerepel a felhasználó, művelet, időpont és eredmény |
| K010 | Teljesítmény | API válaszidő és skálázhatóság | Alacsony | Tipikus API hívás < 500ms fejlesztési környezetben (terhelés nélkül) |

### Fogalomtár

| Fogalom        | Meghatározás                                                                 |
|----------------|----------------------------------------------------------------------------|
| Diák       | A rendszer felhasználója, aki tantárgyakat vesz fel. |
| Oktató         | Tanár, aki jegyeket rögzít, diákok hiányzását kezeli.              |
| Rendszergazda | Jogosult felhasználó, aki kezeli a rendszer működését és beállításait.     |
| Tantárgy       | Kurzus, amelyhez órák és diákok kapcsolódnak.                  |
| Riport/Statisztikák        | Összesítés a diákok és oktatók teljesítményéről, tantárgyakról, vizsgákról.|