# Definition of Done (DoD) — TanEDU

A DoD lista meghatározza, mikor tekintünk egy feladatot késznek.

- **Funkcionalitás:** A fejlesztett feature teljesíti az elfogadási kritériumokat (acceptance criteria).
- **Unit tesztek:** Minden új logika esetén vannak unit tesztek, amelyek lefedik a kritikus ágakat.
- **Kód minőség:** Kód megfelel a projekt stílusának.
- **Biztonság:** Input validáció, jogosultság ellenőrzés és alapvető biztonsági ellenőrzések jelen vannak.
- **Naplózás:** Kritikus műveletek naplózása megvalósult.
- **Review:** PR átvizsgálva és jóváhagyva legalább egy reviewer által.
- **Deploy:** Alapvető deploy/indítási ellenőrzések sikeresek (pl. `./mvnw spring-boot:run`).

Ha minden fenti pont teljesül, a feladat lezárható és merge-elhető main branch-be.