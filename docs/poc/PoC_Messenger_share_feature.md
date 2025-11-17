# 📄 Proof of Concept – Messenger Megosztási Funkció (Share Grade Feature)
**Projekt:** TanEdu  
**Dátum:** 2025.11.20  
**Státusz:** PoC – Megvalósítása jelenleg nem lehetséges  

---

## 🎯 1. A funkció célja

A TanEdu rendszerbe egy olyan megosztási funkció beépítését terveztük, amely lehetővé tette volna, hogy:

- a diák **a kapott jegyét egy gombnyomással megossza Messengerben**,  
- a Messenger felülete megnyíljon, ahol kiválaszthatja a címzettet,
- a jegyhez tartozó információ automatikusan bekerüljön az üzenetbe.

Ez gyorsabb kommunikációt teremtett volna a szülők, barátok és tanárok felé.

---

## ⚙️ 2. Tervezett működés

### Felhasználói folyamat
1. A diák megnyitja az „Érdemjegyek” oldalt.
2. A jegy mellett megjelenik egy **Megosztás Messengerben** gomb.
3. Gombra kattintva meghívódott volna a Messenger Share API.
4. A felhasználó kiválasztja, kinek küldi el.
5. A jegy adatai üzenetként elküldésre kerülnek.

## 🛑 3. A megvalósítás akadályai

A funkció implementálása jelenleg **nem kivitelezhető**, több okból:

### 3.1 Facebook Messenger API korlátozások

- A **Send API** csak *Facebook Pages → felhasználó* kommunikációt támogat.  
- **User-to-user üzenetküldés tiltott** és technikailag blokkolt.  
- A korábban használható **Send Dialog**-ot a Meta 2023-ban **deprecálta**, teljesen megszüntette.  
- A szükséges engedélyek App Review során **nem szerezhetők meg** egy oktatási vagy teszt jellegű alkalmazáshoz.

### 3.2 GDPR és adatvédelmi kockázat

- Jegyek (értesítések) továbbítása külső platformra **adatvédelmi szempontból problémás**.
- EU oktatási környezetben a Messenger **nem hivatalos kommunikációs csatorna**.
- Külön felhasználói beleegyezésre, adatkezelési módosításokra lenne szükség.

### 3.3 Implementációs komplexitás

- A teljes folyamat (FB Login + tokenkezelés + jogosultságok) **túl bonyolult** egy egyetemi projekt számára.
- Kötelező lenne:
  - **HTTPS**
  - **Verified domain**
  - **Meta App Review**
- Ezek mind jelentős többletmunkát és infrastruktúrát igényelnének.

---

## 4. Következtetés

A tervezett Messenger-megosztási funkció:

- felhasználói élmény szempontjából **ötletes és hasznos**,  
- technikailag **elsőre megvalósíthatónak tűnhet**,  
- **de** a Meta API-k korlátozásai, a token- és jogosultságkezelés, valamint a GDPR miatt **gyakorlatban nem bevezethető** a projekt jelenlegi keretei között.

**Ezért a funkció a dokumentációban Proof of Concept (PoC) szinten szerepel:**

Egy olyan **potenciális bővítési irányként**, amely megmutatja, merre lehetne a rendszert a jövőben továbbfejleszteni, amennyiben a technikai és jogi feltételek kedvezően változnának.
