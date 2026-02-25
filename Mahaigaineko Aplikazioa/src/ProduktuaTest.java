import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProduktuaTest {

    // Simulatzen dugu Produktu baten validazio prozesua zure baldintzen arabera.
    // Metodo hau zure benetako klasean (adibidez Produktua edo Kudeatzailea) sar
    // dezakezu gerora.
    public void baliozkotuProduktua(String izena, Object prezioa, Object stocka, String kategoria) throws Exception {

        // B1 / EB1 (Izena)
        if (izena == null || izena.trim().isEmpty()) {
            throw new Exception("ER1: Izenak ezin du hutsik egon");
        }

        // B2 / EB2 / EB3 (Prezioa)
        double prezioLortua;
        if (prezioa instanceof String) {
            throw new Exception("ER2: Prezioak zenbakia izan behar du"); // EB3
        } else {
            prezioLortua = Double.parseDouble(prezioa.toString());
            if (prezioLortua <= 0) {
                throw new Exception("ER2: Prezioak positiboa izan behar du"); // EB2
            }
        }

        // B3 / EB4 / EB5 (Stocka)
        int stockLortua;
        if (stocka instanceof String) {
            throw new Exception("Stockak zenbaki osoa izan behar du"); // EB5
        } else {
            stockLortua = Integer.parseInt(stocka.toString());
            if (stockLortua < 0) {
                throw new Exception("ER3: Stocka ezin da negatiboa izan"); // EB4
            }
        }

        // B4 / B5 / EB6 (Kategoria)
        if (!kategoria.equals("Elektronika") && !kategoria.equals("Arropa")) {
            throw new Exception("ER4: Kategoria ez da existitzen"); // EB6
        }
    }

    /*
     * -------------------------------------------------------------
     * PROBA KASUAK (Baliokidetasun tablekoak CP1 - CP7)
     * -------------------------------------------------------------
     */

    @Test
    public void CP1_SortuProduktuaOndo_Elektronika() {
        // B1, B2, B3, B4 -> Valid
        assertDoesNotThrow(() -> {
            baliozkotuProduktua("Ordenagailua", 850, 10, "Elektronika");
        }, "Produktua arazorik gabe sortu beharko litzateke");
    }

    @Test
    public void CP2_SortuProduktuaOndo_Arropa() {
        // B1, B2, B3, B5 -> Valid
        assertDoesNotThrow(() -> {
            baliozkotuProduktua("Kamiseta", 20, 50, "Arropa");
        }, "Produktua arazorik gabe sortu beharko litzateke");
    }

    @Test
    public void CP3_IzenaHutsikDago() {
        // EB1, B2, B3, B4 -> Invalid (Izena hutsik)
        Exception error = assertThrows(Exception.class, () -> {
            baliozkotuProduktua("", 850, 10, "Elektronika");
        });
        assertEquals("ER1: Izenak ezin du hutsik egon", error.getMessage());
    }

    @Test
    public void CP4_PrezioaZeroEdoNegatiboa() {
        // B1, EB2, B3, B4 -> Invalid (Prezioa ez da positiboa)
        Exception error = assertThrows(Exception.class, () -> {
            baliozkotuProduktua("Ordenagailua", 0, 10, "Elektronika");
        });
        assertEquals("ER2: Prezioak positiboa izan behar du", error.getMessage());
    }

    @Test
    public void CP5_PrezioaLetrekin() {
        // B1, EB3, B3, B5 -> Invalid (Prezioa hitzez)
        Exception error = assertThrows(Exception.class, () -> {
            baliozkotuProduktua("Kamiseta", "hogei", 50, "Arropa");
        });
        assertEquals("ER2: Prezioak zenbakia izan behar du", error.getMessage());
    }

    @Test
    public void CP6_StockNegatiboa() {
        // B1, B2, EB4, B4 -> Invalid (Stock negatiboa)
        Exception error = assertThrows(Exception.class, () -> {
            baliozkotuProduktua("Sagua", 15, -5, "Elektronika");
        });
        assertEquals("ER3: Stocka ezin da negatiboa izan", error.getMessage());
    }

    @Test
    public void CP7_KategoriaOkerra() {
        // B1, B2, B3, EB6 -> Invalid (Kategoria ezezaguna)
        Exception error = assertThrows(Exception.class, () -> {
            baliozkotuProduktua("Liburua", 15, 100, "Liburutegia");
        });
        assertEquals("ER4: Kategoria ez da existitzen", error.getMessage());
    }
}
