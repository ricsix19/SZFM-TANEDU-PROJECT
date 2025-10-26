document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const errorMsg = document.getElementById("errorMsg");

    // === LOGIN ===
    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("username")?.value.trim() || "";
            const password = document.getElementById("password")?.value.trim() || "";

            if (!username || !password) {
                errorMsg.textContent = "Kérlek, töltsd ki mindkét mezőt!";
                errorMsg.style.display = "block";
                return;
            }

           
            
            try {
                // const felhasznalo = window.dummyFelhasznalok?.find(u => u.username === username && u.password === password);
                 // if (!felhasznalo) throw new Error("Hibás felhasználónév vagy jelszó!");

                console.log(username);
                console.log(password);

                localStorage.setItem("bejelentkezettFelhasznalo", JSON.stringify(username));
                window.location.href = "homepage.html";
                const response = await fetch("http://localhost:8080/api/auth/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ username, password })
                });
                console.log(response);
                

                if (!response.ok) {
                    const text = await response.text();
                    throw new Error(text || "Hibás felhasználónév vagy jelszó!");
                }

                const felhasznalo = await response.json();
                if (!felhasznalo || typeof felhasznalo !== "object") {
                    throw new Error("Érvénytelen válasz a szervertől.");
                }

                localStorage.setItem("bejelentkezettFelhasznalo", JSON.stringify(felhasznalo));
                window.location.href = "homepage.html";
            } catch (error) {
                console.error("Bejelentkezési hiba:", error);
                errorMsg.textContent = error.message || "Hiba történt a bejelentkezés során.";
                errorMsg.style.display = "block";
            }
        });
    }

    // === SEGÉDFÜGGVÉNYEK ===
    function getFelhasznalo() {
        const json = localStorage.getItem("bejelentkezettFelhasznalo");
        if (!json) {
            alert("Kérlek, jelentkezz be először!");
            window.location.href = "index.html";
            return null;
        }
        try {
            return JSON.parse(json);
        } catch {
            localStorage.removeItem("bejelentkezettFelhasznalo");
            alert("Hibás felhasználói adatok! Jelentkezz be újra.");
            window.location.href = "index.html";
            return null;
        }
    }

    function biztonsagosSzoveg(szoveg) {
        const div = document.createElement("div");
        div.innerText = szoveg ?? "";
        return div.innerText;
    }

    // === ADATOK OLDAL ===
    if (window.location.pathname.endsWith("adatok.html")) {
        const felhasznalo = getFelhasznalo();
        if (!felhasznalo) return;

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
            felhasznalo.aktualisFelev,
            felhasznalo.varhatoVegzes
        ];

        cellak.forEach((cell, index) => {
            cell.innerText = biztonsagosSzoveg(adatok[index]);
        });
    }

    // === ÓRAREND OLDAL ===
    if (window.location.pathname.endsWith("orarend.html")) {
        const felhasznalo = getFelhasznalo();
        if (!felhasznalo) return;
    
    const fejlecCella = document.querySelectorAll(".tablazat thead th");
    const napokNevek = ["Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek"];

    const ma = new Date();
    const napIndex = (ma.getDay() + 6) % 7; // hétfő = 0
    const hetfo = new Date(ma);
    hetfo.setDate(ma.getDate() - napIndex);

    if (napIndex >= 0 && napIndex <= 4) {
        const childIndex = napIndex + 2; // 0 -> 2 (Hétfő), 1 -> 3 (Kedd)

        document.querySelectorAll(`.tablazat thead tr th:nth-child(${childIndex})`)
            .forEach(th => th.classList.add("aktualis-nap-fejlec"));

        document.querySelectorAll(`.tablazat tbody tr td:nth-child(${childIndex})`)
            .forEach(td => td.classList.add("aktualis-nap"));
}

    // Magyar dátumformázó
    const formatter = new Intl.DateTimeFormat("hu-HU", { month: "long", day: "numeric" });

    fejlecCella.forEach((th, i) => {
        if (i > 0 && i <= 5) {
            const napDatum = new Date(hetfo);
            napDatum.setDate(hetfo.getDate() + (i - 1));
            let datumSzoveg = formatter.format(napDatum);

            datumSzoveg = datumSzoveg.charAt(0).toUpperCase() + datumSzoveg.slice(1);

            th.innerHTML = `${napokNevek[i - 1]}<br><small>${datumSzoveg}</small>`;
        }
    });

    const orarend = felhasznalo.orarend || {};
    const napok = ["hetfo", "kedd", "szerda", "csutortok", "pentek"];
    const sorok = document.querySelectorAll(".tablazat tbody tr");

    sorok.forEach((sor, index) => {
        const cellak = sor.querySelectorAll("td");

        napok.forEach((nap, i) => {
        const cella = cellak[i + 1];
        const napiOrak = orarend[nap] || [];
        const ora = napiOrak[index];

        if (ora && ora.targy) {
            cella.innerHTML = `<strong>${biztonsagosSzoveg(ora.targy)}</strong><br>${biztonsagosSzoveg(ora.terem)}`;
            cella.classList.add("orarend-ora");

            cella.addEventListener("click", () => {
            document.getElementById("oraCim").innerText = biztonsagosSzoveg(ora.cim);
            document.getElementById("oraTargy").innerText = biztonsagosSzoveg(ora.targy);
            document.getElementById("oraTanar").innerText = biztonsagosSzoveg(ora.tanar);
            document.getElementById("oraTerem").innerText = biztonsagosSzoveg(ora.terem);

            const modal = document.getElementById("oraModal");
            modal.hidden = false;           
            modal.style.display = "flex";
            modal.setAttribute("aria-hidden", "false");
            });
        } else {
            cella.innerHTML = "";
            cella.classList.remove("orarend-ora");
        }
        });
    });

    const oraModal = document.getElementById("oraModal");
    const oraClose = document.getElementById("oraClose");

    oraClose.addEventListener("click", () => {
        oraModal.style.display = "none";
        oraModal.setAttribute("aria-hidden", "true");
    });

    window.addEventListener("click", (e) => {
        if (e.target === oraModal) {
        oraModal.style.display = "none";
        oraModal.setAttribute("aria-hidden", "true");
        }
    });
    }

    // === JEGYEK OLDAL ===
    if (window.location.pathname.endsWith("jegyek.html")) {
        const felhasznalo = getFelhasznalo();
        if (!felhasznalo) return;

        const jegyekTabla = document.getElementById("jegyek-tartalom");
        if (!jegyekTabla) return;

        const targyak = felhasznalo.targyak || [];
        const jegyek = felhasznalo.jegyek || {};

        targyak.forEach((targy) => {
            const sor = document.createElement("tr");
            const ertekeles = jegyek[targy] || "nincs értékelve";
            sor.innerHTML = `
                <td>${biztonsagosSzoveg(targy)}</td>
                <td>${biztonsagosSzoveg(ertekeles)}</td>
            `;
            jegyekTabla.appendChild(sor);
        });
    }

    // === ÜZENETEK OLDAL ===
if (window.location.pathname.endsWith("uzenetek.html")) {
    const felhasznalo = getFelhasznalo();
    if (!felhasznalo) return;

    const modal = document.getElementById("uzenetModal");
    const openBtn = document.getElementById("openMessageBtn");
    const closeBtnSend = document.getElementById("Uzenetclose");
    const form = document.getElementById("uzenetForm");
    const tabla = document.querySelector(".uzenet-lista table");
    const vezerelemek = document.querySelector(".uzenet-vezerelemek");
    const select = document.getElementById("uzenet-darab");

    const megtekintModal = document.getElementById("megtekintModal");
    const megtekintClose = document.getElementById("megtekintClose");
    const megtekintFelado = document.getElementById("megtekintFelado");
    const megtekintCimzett = document.getElementById("megtekintCimzett");
    const megtekintTargy = document.getElementById("megtekintTargy");
    const megtekintDatum = document.getElementById("megtekintDatum");
    const megtekintUzenet = document.getElementById("megtekintUzenet");

    if (!modal || !openBtn || !closeBtnSend || !form || !tabla || !vezerelemek || !select || !megtekintModal || !megtekintClose) {
        console.error("Hiányzó elem az üzenetek oldalról!");
        return;
    }

    modal.hidden = true;
    modal.setAttribute("aria-hidden", "true");
    megtekintModal.style.display = "none";
    megtekintModal.setAttribute("aria-hidden", "true");

    openBtn.addEventListener("click", () => {
        modal.hidden = false;
        modal.setAttribute("aria-hidden", "false");
    });

    closeBtnSend.addEventListener("click", () => {
        modal.hidden = true;
        modal.setAttribute("aria-hidden", "true");
    });


    window.addEventListener("click", (e) => {
        if (e.target === modal) {
            modal.hidden = true;
            modal.setAttribute("aria-hidden", "true");
        }
        if (e.target === megtekintModal) {
            megtekintModal.style.display = "none";
            megtekintModal.setAttribute("aria-hidden", "true");
        }
    });



    if (megtekintClose && megtekintModal) {
    megtekintClose.addEventListener("click", () => {
        console.log("❌ bezár gomb működik");
        megtekintModal.style.display = "none";
        megtekintModal.setAttribute("aria-hidden", "true");
    });
}


    
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const cimzett = document.getElementById("cimzett")?.value.trim();
        const targy = document.getElementById("targy")?.value.trim();
        const uzenet = document.getElementById("uzenet")?.value.trim();
        const felhasznalo = getFelhasznalo();
        if (!felhasznalo) return;

        if (!cimzett || !targy || !uzenet) {
            alert("Kérlek, töltsd ki az összes mezőt!");
            return;
        }

        const ujUzenet = {
            felado: felhasznalo.email,
            cimzett,
            targy,
            uzenet,
            datum: new Date().toLocaleString()
        };

        const elkuldottUzenetek = JSON.parse(localStorage.getItem("elkuldottUzenetek") || "[]");
        elkuldottUzenetek.push(ujUzenet);
        localStorage.setItem("elkuldottUzenetek", JSON.stringify(elkuldottUzenetek));

        alert("Üzenet elküldve!");
        modal.hidden = true;
        modal.setAttribute("aria-hidden", "true");
        form.reset();
        renderUzenetek();
    });

    function renderUzenetek() {
        const hash = window.location.hash || "#beerkezett";
        const meret = parseInt(select.value) || 20;
        let uzenetek = [];
        let aktualisOldal = parseInt(localStorage.getItem(`aktualisOldal_${hash}`)) || 1;

        if (hash === "#beerkezett") {
            uzenetek = JSON.parse(localStorage.getItem("beerkezettUzenetek") || "[]");
            tabla.innerHTML = `<tr><th>Feladó</th><th>Tárgy</th><th>Dátum</th></tr>`;
        } else {
            uzenetek = JSON.parse(localStorage.getItem("elkuldottUzenetek") || "[]");
            tabla.innerHTML = `<tr><th>Címzett</th><th>Tárgy</th><th>Dátum</th></tr>`;
        }

        const osszesOldal = Math.ceil(uzenetek.length / meret) || 1;
        if (aktualisOldal > osszesOldal) aktualisOldal = osszesOldal;

        const start = (aktualisOldal - 1) * meret;
        const megjelenitendo = uzenetek.slice(start, start + meret);

        if (megjelenitendo.length === 0) {
            tabla.innerHTML += `<tr><td colspan="3">Nincs megjeleníthető üzenet.</td></tr>`;
        } else {
            megjelenitendo.forEach((u) => {
                const sor = document.createElement("tr");
                const elso = hash === "#beerkezett" ? biztonsagosSzoveg(u.felado) : biztonsagosSzoveg(u.cimzett);
                sor.innerHTML = `
                    <td>${elso}</td>
                    <td>${biztonsagosSzoveg(u.targy)}</td>
                    <td>${biztonsagosSzoveg(u.datum)}</td>
                `;

                sor.addEventListener("click", () => {
                    if (!u || typeof u !== "object") return;

                    megtekintFelado.innerText = biztonsagosSzoveg(u.felado || "");
                    megtekintCimzett.innerText = biztonsagosSzoveg(u.cimzett || "");
                    megtekintTargy.innerText = biztonsagosSzoveg(u.targy || "");
                    megtekintDatum.innerText = biztonsagosSzoveg(u.datum || "");
                    megtekintUzenet.innerText = biztonsagosSzoveg(u.uzenet || "");

                    megtekintModal.style.display = "flex";
                    megtekintModal.setAttribute("aria-hidden", "false");
                });

                tabla.appendChild(sor);
            });
        }

        vezerelemek.querySelector(".lapozo")?.remove();
        if (osszesOldal > 1) {
            let lapozasHTML = `<div class="lapozo">`;
            for (let i = 1; i <= osszesOldal; i++) {
                lapozasHTML += `<button class="oldalGomb ${i === aktualisOldal ? "aktiv" : ""}" data-oldal="${i}">${i}</button>`;
            }
            lapozasHTML += `</div>`;
            vezerelemek.insertAdjacentHTML("beforeend", lapozasHTML);

            document.querySelectorAll(".oldalGomb").forEach(btn => {
                btn.addEventListener("click", e => {
                    const ujOldal = parseInt(e.target.dataset.oldal);
                    localStorage.setItem(`aktualisOldal_${hash}`, ujOldal);
                    renderUzenetek();
                });
            });
        }

        select.onchange = () => {
            localStorage.setItem(`aktualisOldal_${hash}`, 1);
            renderUzenetek();
        };
    }

    window.addEventListener("hashchange", renderUzenetek);
    renderUzenetek();
}

    // === KIJELENTKEZÉS GOMB ===
    const kijelentkezesBtn = document.getElementById("kijelentkezesBtn");
    if (kijelentkezesBtn) {
        kijelentkezesBtn.addEventListener("click", () => {
            localStorage.removeItem("bejelentkezettFelhasznalo");
            window.location.href = "index.html";
        });
    }
});