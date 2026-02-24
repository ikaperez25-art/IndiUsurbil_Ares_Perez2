let produktuak = [];

// API-tik datuak hartu
fetch("https://fakestoreapi.com/products")
    .then(res => res.json())
    .then(data => {
        produktuak = data;
        erakutsi(produktuak);
    });

// Filtro funtzioa (Hitz gako sinpleak erabiliz akatsak ekiditeko)
function filtratu(mota) {
    if (mota === 'all') {
        erakutsi(produktuak);
    } else {
        // API-ko kategoria konplikatuak hitz sinpleekin lotu
        let kategoriaMapatua = "";
        if (mota === 'man') kategoriaMapatua = "men's clothing";
        if (mota === 'woman') kategoriaMapatua = "women's clothing";
        if (mota === 'elec') kategoriaMapatua = "electronics";
        if (mota === 'jewel') kategoriaMapatua = "jewelery";

        const filtratuak = produktuak.filter(p => p.category === kategoriaMapatua);
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
                <img src="${p.image}" width="100">
                <h3>${p.title}</h3>
                <p><b>${p.price}€</b></p>
                <button onclick="saskira(${p.id})">Saskira</button>
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
