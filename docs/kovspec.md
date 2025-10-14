# TanEdu — Követelmény Specifikáció

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

| Modul          | Név                | Kifejtés                                                                 |
|----------------|--------------------|------------------------------------------------------------------------|
| Jogosultság    | Bejelentkezés      | Email/jelszó alapú belépés, hibás próbálkozás esetén hibaüzenet.        |
| Felhasználói   | Tantárgymegjelenítés   | A diák csak azokat a tantárgyakat látja amelyekhez a Rendszergazda hozzárendelte |
| Felhasználói   | Jegymegtekintés    | A diák saját jegyeit és statisztikáit láthatja.                     |
| Oktatói        | Jegybeírás         | Az oktató rögzítheti, módosíthatja a diákok eredményeit.             |
| Oktatói        | Témazárók kezelése   | Oktató Témazárókat írhat ki, időponttal.       |
| Rendszergazda | Felhasználókezelés | Rendszergazda felveszi, törli és jogosultsággal látja el az Oktatókat.      |
| Rendszergazda | Tantárgykezelés    | Rendszergazda létrehozhat, módosíthat vagy törölhet tantárgyakat.               |
| Egyéb          | Értesítések        | A rendszer automatikus üzeneteket küld (pl. Érdemjegy beírás).    |
| Egyéb          | Statisztikák           | Rendszer készít statisztikákat a diákok átlaguk és jegyeik alapján.         |
| Rendszer       | Naplózás           | Minden módosítás és tranzakció naplózásra kerül.                        |

### Fogalomtár

| Fogalom        | Meghatározás                                                                 |
|----------------|----------------------------------------------------------------------------|
| Diák       | A rendszer felhasználója, aki tantárgyakat vesz fel. |
| Oktató         | Tanár, aki jegyeket rögzít, diákok hiányzását kezeli.              |
| Rendszergazda | Jogosult felhasználó, aki kezeli a rendszer működését és beállításait.     |
| Tantárgy       | Kurzus, amelyhez órák és diákok kapcsolódnak.                  |
| Riport/Statisztikák        | Összesítés a diákok és oktatók teljesítményéről, tantárgyakról, vizsgákról.|