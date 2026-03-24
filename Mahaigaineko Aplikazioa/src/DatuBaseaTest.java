import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DatuBaseaTest {

    @Test
    public void testInsertBatzukBabestuak() {
        // Produktu bat sortzeko saioa, datu-basea itzalita badago ere ez da kraskatu
        // behar
        assertDoesNotThrow(() -> {
            Produktua p = new Produktua("TEST", "TEST", 1.0, 1, "test.png", 1);
            DatuBasea.gehitu(p);
        });
    }

    @Test
    public void testCPDB1_ProduktuGuztiakNullEzDira() {
        assertDoesNotThrow(() -> {
            ArrayList<Produktua> lista = DatuBasea.produktuGuztiak();
            assertNotNull(lista, "Ziklo osoak berdin eskatzen du (itxarondako emaitza: ez da null)");
        });
    }

    @Test
    public void testCPDB2_BilaketaEmaitzaHutsarekin() {
        assertDoesNotThrow(() -> {
            ArrayList<Produktua> lista = DatuBasea.bilatu("TestKasuEzDuEzerBilatuko");
            assertNotNull(lista);
        });
    }

    @Test
    public void testCPDB3_EzabatuInoizExistitukoEzDenID() {
        assertDoesNotThrow(() -> {
            // ID ezezagun bat ezabatu ezkero ez du `Exception` bat eskainiko
            DatuBasea.ezabatu(-999);
        });
    }
}
