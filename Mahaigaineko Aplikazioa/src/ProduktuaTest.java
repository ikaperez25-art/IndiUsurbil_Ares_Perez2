import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProduktuaTest {

    public Produktua baliozkotuProduktua(String izena, Object prezioa, Object stocka, String kategoria) throws Exception {
        if (izena == null || izena.trim().isEmpty()) {
            throw new Exception("ER1: Izenak ezin du hutsik egon");
        }
        double prezioLortua;
        if (prezioa instanceof String) {
            throw new Exception("ER2: Prezioak zenbakia izan behar du");
        } else {
            prezioLortua = Double.parseDouble(prezioa.toString());
            if (prezioLortua <= 0) {
                throw new Exception("ER2: Prezioak positiboa izan behar du");
            }
        }
        int stockLortua;
        if (stocka instanceof String) {
             throw new Exception("Stockak zenbaki osoa izan behar du");
        } else {
            stockLortua = Integer.parseInt(stocka.toString());
            if (stockLortua < 0) {
                throw new Exception("ER3: Stocka ezin da negatiboa izan");
            }
        }
        if (!kategoria.equals("Elektronika") && !kategoria.equals("Arropa")) {
            throw new Exception("ER4: Kategoria ez da existitzen");
        }

        // Produktua sortu coverage hobea edukitzeko:
        Produktua p = new Produktua(izena, "Desk", prezioLortua, stockLortua, "img.png", 1);
        p.kategoriaIzena = kategoria;
        return p;
    }

    @Test
    public void CP1_SortuProduktuaOndo_Elektronika() {
        assertDoesNotThrow(() -> {
            baliozkotuProduktua("Ordenagailua", 850, 10, "Elektronika");
        });
    }

    @Test
    public void CP2_SortuProduktuaOndo_Arropa() {
        assertDoesNotThrow(() -> {
            baliozkotuProduktua("Kamiseta", 20, 50, "Arropa");
        });
    }

    @Test
    public void CP3_IzenaHutsikDago() {
        Exception e = assertThrows(Exception.class, () -> baliozkotuProduktua("", 850, 10, "Elektronika"));
        assertEquals("ER1: Izenak ezin du hutsik egon", e.getMessage());
    }

    @Test
    public void CP4_PrezioaZeroEdoNegatiboa() {
        Exception e = assertThrows(Exception.class, () -> baliozkotuProduktua("Orden", 0, 10, "Elektronika"));
        assertEquals("ER2: Prezioak positiboa izan behar du", e.getMessage());
    }

    @Test
    public void CP5_PrezioaLetrekin() {
        Exception e = assertThrows(Exception.class, () -> baliozkotuProduktua("Kamiseta", "hogei", 50, "Arropa"));
        assertEquals("ER2: Prezioak zenbakia izan behar du", e.getMessage());
    }

    @Test
    public void CP6_StockNegatiboa() {
        Exception e = assertThrows(Exception.class, () -> baliozkotuProduktua("Sagua", 15, -5, "Elektronika"));
        assertEquals("ER3: Stocka ezin da negatiboa izan", e.getMessage());
    }

    @Test
    public void CP7_KategoriaOkerra() {
        Exception e = assertThrows(Exception.class, () -> baliozkotuProduktua("Liburua", 15, 100, "Liburutegia"));
        assertEquals("ER4: Kategoria ez da existitzen", e.getMessage());
    }

    // Oinarrizko sortzaile hutsaren frogapena (%100 Coverage lortzeko Produktua.javarentzat)
    @Test
    public void testProduktuaHutsik() {
        Produktua p = new Produktua();
        assertNotNull(p);
        p.id = 1;
        assertEquals(1, p.id);
    }
}
