const keyLocalstorage = "saskia";

function agregarAlCarrito(producto) {
    let memoria = JSON.parse(localStorage.getItem(keyLocalstorage)) || [];
    let cantidadProductoFinal;

    const indiceProducto = memoria.findIndex(p => p.id === producto.id);

    if (indiceProducto === -1) {
        const nuevoProducto = { ...producto, cantidad: 1 };
        memoria.push(nuevoProducto);
        cantidadProductoFinal = 1;
    } else {
        memoria[indiceProducto].cantidad++;
        cantidadProductoFinal = memoria[indiceProducto].cantidad;
    }

    localStorage.setItem(keyLocalstorage, JSON.stringify(memoria));
    actualizarNumeroCarrito();
    return cantidadProductoFinal;
}

function restarAlCarrito(producto) {
    let memoria = JSON.parse(localStorage.getItem(keyLocalstorage)) || [];
    const indiceProducto = memoria.findIndex(p => p.id === producto.id);

    if (indiceProducto === -1) return 0;

    memoria[indiceProducto].cantidad--;
    let cantidadFinal = memoria[indiceProducto].cantidad;

    if (cantidadFinal === 0) {
        memoria.splice(indiceProducto, 1);
    }

    localStorage.setItem(keyLocalstorage, JSON.stringify(memoria));
    actualizarNumeroCarrito();
    return cantidadFinal;
}

function getNuevoProductoParaMemoria(producto) {
    const nuevoProducto = producto;
    nuevoProducto.cantidad = 1;
    return nuevoProducto;
}

function actualizarNumeroCarrito() {
    const cuentaElement = document.getElementById("cuenta-carrito");
    if (cuentaElement) {
        const memoria = JSON.parse(localStorage.getItem(keyLocalstorage)) || [];
        const cuenta = memoria.reduce((acc, curr) => acc + curr.cantidad, 0);
        cuentaElement.innerText = cuenta;
    }
}
