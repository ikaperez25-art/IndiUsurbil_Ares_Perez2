import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class DatuBaseaTest {

    @Test
    public void testProduktuGuztiakEzDaNull() {
        // Hau beti pasako da programak ondo bueltatzen badu array hutsa bada ere
        ArrayList<Produktua> lista = DatuBasea.produktuGuztiak();
        assertNotNull(lista, "Lista ez da null izan behar");
    }

    @Test
    public void testBilatuStringHutsarekin() {
        // String huts batekin bilatzen badugu, elementuak etorri beharko lirateke edo
        // gutxienez lista bat
        ArrayList<Produktua> lista = DatuBasea.bilatu("");
        assertNotNull(lista);
    }

    @Test
    public void testBilatuStringEzezagunarekin() {
        // Inoiz existituko ez den izen batekin bilatzean zerrenda hutsa ekarri beharko
        // luke
        ArrayList<Produktua> lista = DatuBasea.bilatu("XYZEZINDAEXISTITU12345");
        assertNotNull(lista);
        assertEquals(0, lista.size(), "Ezezaguna bilatzean 0 emaitza ekarri behar ditu");
    }
}
