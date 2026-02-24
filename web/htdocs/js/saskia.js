const saskiEdukiontzia = document.getElementById("saski-edukiontzia");

function crearTarjetasProductosCarrito() {
    saskiEdukiontzia.innerHTML = "";
    const produktuak = JSON.parse(localStorage.getItem("saskia")) || [];

    if (produktuak && produktuak.length > 0) {
        produktuak.forEach((produktua) => {
            const txartelBerria = document.createElement("div");
            txartelBerria.classList.add("produktu-txartela");
            txartelBerria.innerHTML = `
                <div class="produktu-irudia">
                    <img src="${produktua.image}" alt="${produktua.title}">
                </div>
                <div class="produktu-info">
                    <h3 class="produktu-izenburua">${produktua.title}</h3>
                    <div class="produktu-oina">
                        <span class="produktu-prezioa">${produktua.price.toFixed(2)}€</span>
                        <div class="kopurua-kontrola">
                            <button class="minus">-</button>
                            <span class="kopuru-testua">${produktua.cantidad}</span>
                            <button class="plus">+</button>
                        </div>
                    </div>
                </div>
            `;
            saskiEdukiontzia.appendChild(txartelBerria);

            txartelBerria.querySelector(".minus").addEventListener("click", () => {
                restarAlCarrito(produktua);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });

            txartelBerria.querySelector(".plus").addEventListener("click", () => {
                agregarAlCarrito(produktua);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });
        });
    } else {
        saskiEdukiontzia.innerHTML = "<p class='mezua-hutsik'>Saskia hutsik daukazu</p>";
    }
    actualizarTotales();
    actualizarNumeroCarrito();
}

function actualizarTotales() {
    const produktuak = JSON.parse(localStorage.getItem("saskia")) || [];
    let kopurua = 0;
    let prezioa = 0;

    produktuak.forEach((p) => {
        kopurua += p.cantidad;
        prezioa += p.price * p.cantidad;
    });

    const guztizkoenEdukiontzia = document.getElementById("guztizkoak");
    if (guztizkoenEdukiontzia) {
        guztizkoenEdukiontzia.innerHTML = `
            <div>Produktuak guztira: ${kopurua}</div>
            <div>Guztira: ${prezioa.toFixed(2)}€</div>
            <div class="saski-botoiak">
                <button id="saskia-hustu">Saskia hustu</button>
                <button id="erosi-botoia">Erosi orain</button>
            </div>
        `;

        document.getElementById("saskia-hustu").addEventListener("click", () => {
            localStorage.removeItem("saskia");
            crearTarjetasProductosCarrito();
            actualizarTotales();
        });

        document.getElementById("erosi-botoia").addEventListener("click", () => {
            alert("Erosketa ondo burutu da! Eskerrik asko.");
            localStorage.removeItem("saskia");
            crearTarjetasProductosCarrito();
            actualizarTotales();
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    crearTarjetasProductosCarrito();
});
