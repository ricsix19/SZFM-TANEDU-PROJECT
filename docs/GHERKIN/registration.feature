Feature: Felhasználói fiók létrehozása

  Scenario: Új felhasználói fiók sikeres létrehozása rendszergazda által
    Given a rendszergazda be van jelentkezve a TanEDU rendszerbe
    And a rendszergazda megnyitja a "Felhasználók kezelése" oldalt
    When a rendszergazda megadja az új felhasználó teljes nevét
    And megadja az e-mail címét
    And kiválasztja a szerepkörét (hallgató, oktató, admin)
    And rögzíti az adatokat
    Then a rendszer automatikusan generál egy kezdő jelszót
    And létrehozza az új felhasználói fiókot
    And megjelenít egy visszajelzést: "Felhasználói fiók sikeresen létrehozva."