const products = [
    {
        id: 1,
        title: "Fjallraven - Foldsack motxila, 15 hazbeteko ordenagailuentzat",
        price: 109.95,
        description: "Motxila paregabea eguneroko erabilerarako eta mendi bueltetarako.",
        category: "gizonezkoen arropa",
        image: "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_t.png",
    },
    {
        id: 2,
        title: "Gizonezkoen T-Shirt Premium-a",
        price: 22.30,
        description: "Estilo estua, mahuka luzeak eta kolore kontrastatuak.",
        category: "gizonezkoen arropa",
        image: "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
    },
    {
        id: 3,
        title: "Gizonezkoen Kotoizko Jaka",
        price: 55.99,
        description: "Kanpoko jaka bikaina udaberri, udazken eta negurako.",
        category: "gizonezkoen arropa",
        image: "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
    }
];

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('produktu-edukiontzia');
    if (container) {
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
            productCard.querySelector(".saskira-gehitu").addEventListener("click", () => agregarAlCarrito(product));

            container.appendChild(productCard);
        });
    }
    actualizarNumeroCarrito();
});
