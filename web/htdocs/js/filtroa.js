const products = [
    {
        id: 1,
        title: "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
        price: 109.95,
        description: "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
        category: "men's clothing",
        image: "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_t.png",
        rating: { rate: 3.9, count: 120 }
    }
];

document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('products-container');
    if (container) {
        products.forEach(product => {
            const productCard = document.createElement('div');
            productCard.innerHTML = `
                <img src="${product.image}" width="100"><br>
                <span>${product.title}</span><br>
                <span>${product.price}€</span><br>
                <button>Saskira gehitu</button>
            `;
            productCard.getElementsByTagName("button")[0].addEventListener("click", () => agregarAlCarrito(product));

            container.appendChild(productCard);
        });
    }
    actualizarNumeroCarrito();
});
