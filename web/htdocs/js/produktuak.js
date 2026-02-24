let produktuak = [];

// JSON lokaletik datuak hartu (datu basetik esportatutakoak)
fetch("json/produktuak.json")
    .then(res => res.json())
    .then(data => {
        produktuak = data;
        erakutsi(produktuak);
    });

// Filtro funtzioa
function filtratu(mota) {
    if (mota === 'all') {
        erakutsi(produktuak);
    } else {
        const filtratuak = produktuak.filter(p => p.kategoria.toLowerCase() === mota.toLowerCase());
        erakutsi(filtratuak);
    }
}

// Pantailaratu
function erakutsi(lista) {
    const div = document.getElementById('produktu-edukiontzia');
    div.innerHTML = '';
    lista.forEach(p => {
        div.innerHTML += `
            <div class="produktu-txartela">
                <div class="produktu-irudia">
                    <img src="${p.irudia}" alt="${p.izena}">
                </div>
                <div class="produktu-info">
                    <h3 class="produktu-izenburua">${p.izena}</h3>
                    <div class="produktu-oina">
                        <span class="produktu-prezioa">${p.prezioa.toFixed(2)}€</span>
                        <button class="saskira-gehitu" onclick="saskira(${p.id})">+ Gehitu</button>
                    </div>
                </div>
            </div>
        `;
    });
}

// Gehitu eta mugitu
function saskira(id) {
    const p = produktuak.find(prod => prod.id === id);
    agregarAlCarrito(p);
    window.location.href = "saskia.html";
}
