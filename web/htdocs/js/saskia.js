const contenedorTarjetas = document.getElementById("cart-container");

function crearTarjetasProductosCarrito() {
    contenedorTarjetas.innerHTML = "";
    const productos = JSON.parse(localStorage.getItem("saskia")) || [];

    if (productos && productos.length > 0) {
        productos.forEach((producto) => {
            const nuevaBicicleta = document.createElement("div");
            nuevaBicicleta.innerHTML = `
                <img src="${producto.image}" width="50">
                <span>${producto.title}</span>
                <span>${producto.price}€</span>
                <div>
                    <button>-</button>
                    <span class="cantidad">${producto.cantidad}</span>
                    <button>+</button>
                </div>
            `;
            contenedorTarjetas.appendChild(nuevaBicicleta);

            nuevaBicicleta.getElementsByTagName("button")[0].addEventListener("click", (e) => {
                restarAlCarrito(producto);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });

            nuevaBicicleta.getElementsByTagName("button")[1].addEventListener("click", (e) => {
                agregarAlCarrito(producto);
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });
        });
    } else {
        contenedorTarjetas.innerHTML = "<p>Saskia hutsik</p>";
    }
    actualizarTotales();
    actualizarNumeroCarrito();
}

function actualizarTotales() {
    const productos = JSON.parse(localStorage.getItem("saskia")) || [];
    let cantidad = 0;
    let precio = 0;

    if (productos && productos.length > 0) {
        productos.forEach((producto) => {
            cantidad += producto.cantidad;
            precio += producto.price * producto.cantidad;
        });
    }

    const totalesContainer = document.getElementById("totales");
    if (totalesContainer) {
        totalesContainer.innerHTML = `
            <div>Unitateak guztira: ${cantidad}</div>
            <div>Prezio totala: ${precio.toFixed(2)}€</div>
            <button id="reiniciar">Saskia hustu</button>
            <button id="comprar"> erosi</button>
        `;

        const btnReiniciar = document.getElementById("reiniciar");
        if (btnReiniciar) {
            btnReiniciar.addEventListener("click", () => {
                localStorage.removeItem("saskia");
                crearTarjetasProductosCarrito();
                actualizarTotales();
            });
        }
        const btnComprar = document.getElementById("comprar");
btnComprar.onclick = function(){
alert("erosketa eginda")
localStorage.removeItem("saskia");
                crearTarjetasProductosCarrito();
                actualizarTotales();
}
    }
}




document.addEventListener('DOMContentLoaded', () => {
    crearTarjetasProductosCarrito();
});
