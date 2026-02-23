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
                        <div>
                            <button>-</button>
                            <span class="kopurua-testua">${produktua.cantidad}</span>
                            <button>+</button>
                        </div>
                    </div>
                </div>
            `;
            saskiEdukiontzia.appendChild(txartelBerria);

            txartelBerria.getElementsByTagName("button")[0].addEventListener("click", (e) => {
                restarAlCarrito(produktua);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });

            txartelBerria.getElementsByTagName("button")[1].addEventListener("click", (e) => {
                agregarAlCarrito(produktua);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });
        });
    } else {
        saskiEdukiontzia.innerHTML = "<p>Saskia hutsik</p>";
    }
    actualizarTotales();
    actualizarNumeroCarrito();
}

function actualizarTotales() {
    const produktuak = JSON.parse(localStorage.getItem("saskia")) || [];
    let kopurua = 0;
    let prezioa = 0;

    if (produktuak && produktuak.length > 0) {
        produktuak.forEach((produktua) => {
            kopurua += produktua.cantidad;
            prezioa += produktua.price * produktua.cantidad;
        });
    }

    const guztizkoenEdukiontzia = document.getElementById("guztizkoak");
    if (guztizkoenEdukiontzia) {
        guztizkoenEdukiontzia.innerHTML = `
            <div>Unitateak guztira: ${kopurua}</div>
            <div>Prezio totala: ${prezioa.toFixed(2)}€</div>
            <button id="saskia-hustu">Saskia hustu</button>
            <button id="erosi-botoia">Erosi</button>
        `;

        const btnHustu = document.getElementById("saskia-hustu");
        if (btnHustu) {
            btnHustu.addEventListener("click", () => {
                localStorage.removeItem("saskia");
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });
        }
        const btnErosi = document.getElementById("erosi-botoia");
        if (btnErosi) {
            btnErosi.onclick = function () {
                alert("Erosketa eginda!");
                localStorage.removeItem("saskia");
                crearTarjetasProductosCarrito();
                actualizarTotales();
            }
        }
    }
}

document.addEventListener('DOMContentLoaded', () => {
    crearTarjetasProductosCarrito();
});
