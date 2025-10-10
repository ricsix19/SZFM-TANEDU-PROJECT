document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const errorMsg = document.getElementById("errorMsg");
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("username").value.trim();
            const password = document.getElementById("password").value.trim();
            try {
                const response = await fetch("http://localhost:8080/api/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ username, password }),
                });
                if (!response.ok) {
                    if (response.status === 401) {
                        errorMsg.textContent = "Hibás felhasználónév vagy jelszó!";
                    } else {
                        errorMsg.textContent = "Hiba történt a bejelentkezéskor. Kérlek, próbáld újra később.";
                    }
                    errorMsg.style.display = "block";
                    return;
                }
                const felhasznalo = await response.json();
                localStorage.setItem("bejelentkezettFelhasznalo", JSON.stringify(felhasznalo));
                window.location.href = "homepage.html";
            } catch (err) {
                console.error("Hiba a backend elérésében:", err);
                errorMsg.textContent = "Hiba történt a bejelentkezéskor. Kérlek, próbáld újra később.";
                errorMsg.style.display = "block";
            }
        });
    }
    if (window.location.pathname.includes("adatok.html")) {
        const felhasznaloJSON = localStorage.getItem("bejelentkezettFelhasznalo");
        if (!felhasznaloJSON) {
            alert("Kérlek, jelentkezz be először!");
            window.location.href = "index.html";
            return;
        }
        const felhasznalo = JSON.parse(felhasznaloJSON);
        const cellak = document.querySelectorAll(".tablazat td:nth-child(2)");
        const adatok = [
            felhasznalo.teljesNev,
            felhasznalo.azonosito,
            felhasznalo.anyjaNeve,
            felhasznalo.szuletesHelye,
            felhasznalo.szuletesIdeje,
            felhasznalo.telefon,
            felhasznalo.email,
            felhasznalo.kepzesNeve,
            felhasznalo.kezdesIdopontja,
            felhasznalo.varhatoVegzes
        ];
        cellak.forEach((cell, index) => {
            cell.textContent = adatok[index];
        });
    }
});
