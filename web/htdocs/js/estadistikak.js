// Estadistikak kargatzen direnean
document.addEventListener('DOMContentLoaded', function () {
    kargatuEstadistikak();
});

function kargatuEstadistikak() {
    fetch('json/estadistikak.json')
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            // 1. Irabazi totala
            document.getElementById('irabazi-totala').textContent = data.irabazi_totala.toFixed(2) + '€';

            // 2. Stock baxua
            var stockDiv = document.getElementById('stock-baxua');
            if (data.stock_baxua.length > 0) {
                for (var i = 0; i < data.stock_baxua.length; i++) {
                    stockDiv.innerHTML += '<li><span>' + data.stock_baxua[i].izena + '</span><span class="gorria">' + data.stock_baxua[i].stocka + ' unitate</span></li>';
                }
            } else {
                stockDiv.innerHTML = '<li>Produktu guztiak stock onarekin</li>';
            }

            // 3. Gehien salduak
            var salduakDiv = document.getElementById('gehien-salduak');
            if (data.gehien_salduak.length > 0) {
                for (var i = 0; i < data.gehien_salduak.length; i++) {
                    salduakDiv.innerHTML += '<li><span>' + data.gehien_salduak[i].izena + '</span><span>' + data.gehien_salduak[i].saldua + ' unit.</span></li>';
                }
            } else {
                salduakDiv.innerHTML = '<li>Daturik ez</li>';
            }

            // 4. Bezero onenak
            var bezeroDiv = document.getElementById('bezero-onenak');
            if (data.bezero_onenak.length > 0) {
                for (var i = 0; i < data.bezero_onenak.length; i++) {
                    bezeroDiv.innerHTML += '<li><span>' + data.bezero_onenak[i].izena + '</span><span>' + data.bezero_onenak[i].eskaera_kop + ' eskari</span></li>';
                }
            } else {
                bezeroDiv.innerHTML = '<li>Daturik ez</li>';
            }

            // 5. Hileko irabaziak
            var hilekoDiv = document.getElementById('hileko-irabaziak');
            if (data.hileko_irabaziak.length > 0) {
                for (var i = 0; i < data.hileko_irabaziak.length; i++) {
                    hilekoDiv.innerHTML += '<li><span>' + data.hileko_irabaziak[i].data + '</span><span class="berdea">' + data.hileko_irabaziak[i].irabazia.toFixed(2) + '€</span></li>';
                }
            } else {
                hilekoDiv.innerHTML = '<li>Daturik ez</li>';
            }

            // 6. Inoiz erosi ez direnak
            var inoizDiv = document.getElementById('inoiz-erosiak');
            if (data.inoiz_erosi_gabeak.length > 0) {
                for (var i = 0; i < data.inoiz_erosi_gabeak.length; i++) {
                    inoizDiv.innerHTML += '<li>' + data.inoiz_erosi_gabeak[i] + '</li>';
                }
            } else {
                inoizDiv.innerHTML = '<li>Produktu guztiak saldu dira</li>';
            }

            // 7. 500€ baino gehiago
            var altukoakDiv = document.getElementById('salmenta-altukoak');
            if (data.salmenta_altukoak.length > 0) {
                for (var i = 0; i < data.salmenta_altukoak.length; i++) {
                    altukoakDiv.innerHTML += '<li><span>' + data.salmenta_altukoak[i].izena + '</span><span class="berdea">' + data.salmenta_altukoak[i].totala + '€</span></li>';
                }
            } else {
                altukoakDiv.innerHTML = '<li>Ez dago 500€ gainditu duen produkturik</li>';
            }
        });
}
