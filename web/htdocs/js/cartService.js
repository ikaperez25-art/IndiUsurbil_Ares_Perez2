const gakoaLocalstorage = "saskia";

function agregarAlCarrito(producto) {
    let memoria = JSON.parse(localStorage.getItem(gakoaLocalstorage)) || [];
    let kopuruFinala;

    const produktuarenIndizea = memoria.findIndex(p => p.id === producto.id);

    if (produktuarenIndizea === -1) {
        const produktuBerria = { ...producto, cantidad: 1 };
        memoria.push(produktuBerria);
        kopuruFinala = 1;
    } else {
        memoria[produktuarenIndizea].cantidad++;
        kopuruFinala = memoria[produktuarenIndizea].cantidad;
    }

    localStorage.setItem(gakoaLocalstorage, JSON.stringify(memoria));
    actualizarNumeroCarrito();
    return kopuruFinala;
}

function restarAlCarrito(producto) {
    let memoria = JSON.parse(localStorage.getItem(gakoaLocalstorage)) || [];
    const produktuarenIndizea = memoria.findIndex(p => p.id === producto.id);

    if (produktuarenIndizea === -1) return 0;

    memoria[produktuarenIndizea].cantidad--;
    let kopuruFinala = memoria[produktuarenIndizea].cantidad;

    if (kopuruFinala === 0) {
        memoria.splice(produktuarenIndizea, 1);
    }

    localStorage.setItem(gakoaLocalstorage, JSON.stringify(memoria));
    actualizarNumeroCarrito();
    return kopuruFinala;
}

function actualizarNumeroCarrito() {
    const saskiKontagailua = document.getElementById("saski-kontagailua");
    if (saskiKontagailua) {
        const memoria = JSON.parse(localStorage.getItem(gakoaLocalstorage)) || [];
        const kontua = memoria.reduce((acc, curr) => acc + curr.cantidad, 0);
        saskiKontagailua.innerText = kontua;
    }
}
