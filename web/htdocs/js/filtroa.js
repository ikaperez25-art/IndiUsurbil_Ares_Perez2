document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('produktu-edukiontzia');
    if (container) {
        // JSON lokaletik lehenengo 3 produktuak erakutsi (nabarmenduak)
        fetch("json/produktuak.json")
            .then(res => res.json())
            .then(products => {
                const nabarmenduak = products.slice(0, 3);
                nabarmenduak.forEach(product => {
                    const productCard = document.createElement('div');
                    productCard.classList.add('produktu-txartela');
                    productCard.innerHTML = `
                        <div class="produktu-irudia">
                            <img src="${product.irudia}" alt="${product.izena}">
                        </div>
                        <div class="produktu-info">
                            <h3 class="produktu-izenburua">${product.izena}</h3>
                            <div class="produktu-oina">
                                <span class="produktu-prezioa">${product.prezioa.toFixed(2)}€</span>
                                <button class="saskira-gehitu">+ Gehitu</button>
                            </div>
                        </div>
                    `;
                    productCard.querySelector(".saskira-gehitu").addEventListener("click", () => {
                        agregarAlCarrito(product);
                    });
                    container.appendChild(productCard);
                });
            });
    }
    actualizarNumeroCarrito();
});
