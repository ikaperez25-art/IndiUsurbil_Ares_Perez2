document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('produktu-edukiontzia');
    if (container) {
        // Index-ean (Hasiera) lehenengo 3 produktuak erakutsiko ditugu API-tik hartuta
        fetch("https://fakestoreapi.com/products?limit=3")
            .then(res => res.json())
            .then(products => {
                products.forEach(product => {
                    const productCard = document.createElement('div');
                    productCard.classList.add('produktu-txartela');
                    productCard.innerHTML = `
                        <div class="produktu-irudia">
                            <img src="${product.image}" alt="${product.title}">
                        </div>
                        <div class="produktu-info">
                            <h3 class="produktu-izenburua">${product.title}</h3>
                            <div class="produktu-oina">
                                <span class="produktu-prezioa">${product.price.toFixed(2)}€</span>
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
