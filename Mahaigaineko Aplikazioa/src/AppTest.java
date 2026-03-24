import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testAplikazioaInstantziak() {
        App aplikazioa = new App();
        assertNotNull(aplikazioa, "App klasea instantziatu ahal da");
    }

    @Test
    public void testFormatuaUlertzenDaBabesMekanismoarekin() {
        String datua = "15.99";
        Scanner sc = new Scanner(new ByteArrayInputStream(datua.getBytes()));
        sc.useLocale(Locale.US);

        double emaitza = sc.nextDouble(); // Ez du InputMismatchException botako
        assertEquals(15.99, emaitza, "Puntua erabilita ondo esleitu da zenbakia");
    }

    // --- PRODUKTUA GEHITU PROBAK (CP1 - CP8) ---

    private void exekutatuProba(String sarrera, String itxarondakoIrteeraZatia) {
        Scanner sc = new Scanner(new ByteArrayInputStream(sarrera.getBytes()));
        sc.useLocale(Locale.US);
        App.produktuaGehitu(sc);
        assertTrue(outContent.toString().contains(itxarondakoIrteeraZatia),
                "Emaitza okerra. Itxarondakoa: " + itxarondakoIrteeraZatia + "\nBaina lortu dena:\n"
                        + outContent.toString());
        outContent.reset(); // hurrengo probarako garbitu
    }

    @Test
    public void testCP1_BalioZuzenak() {
        String sarrera = "Ordenagailua\nDesk\n850\n10\nurl_irudia\n1\n";
        exekutatuProba(sarrera, "Produktua sortu da");
    }

    @Test
    public void testCP2_BalioZuzenakBesteKategoria() {
        String sarrera = "Kamiseta\nDesk\n20\n50\nurl_irudia\n2\n";
        exekutatuProba(sarrera, "Produktua sortu da");
    }

    @Test
    public void testCP3_IzenaHutsik() {
        String sarrera = "\nDesk\n850\n10\nurl_irudia\n1\n";
        exekutatuProba(sarrera, "ER1: Izenak ezin du hutsik egon");
    }

    @Test
    public void testCP4_PrezioaZeroEdoNegatiboa() {
        String sarrera = "Ordenagailua\nDesk\n0\n10\nurl_irudia\n1\n";
        exekutatuProba(sarrera, "ER2: Prezioak positiboa izan behar du");
    }

    @Test
    public void testCP5_PrezioaEzZenbakia() {
        String sarrera = "Kamiseta\nDesk\nhogei\n50\nurl_irudia\n2\n";
        exekutatuProba(sarrera, "ER2: Prezioak zenbakia izan behar du");
    }

    @Test
    public void testCP6_StockaNegatiboa() {
        String sarrera = "Sagua\nDesk\n15\n-5\nurl_irudia\n1\n";
        exekutatuProba(sarrera, "ER3: Stocka ezin da negatiboa izan");
    }

    @Test
    public void testCP7_KategoriaEzDaExistitzen() {
        String sarrera = "Liburua\nDesk\n15\n100\nurl_irudia\n99\n";
        exekutatuProba(sarrera, "ER4: Kategoria ez da existitzen");
    }

    @Test
    public void testCP8_StockaEzZenbakia() {
        String sarrera = "Teklatua\nDesk\n40\nhamar\nurl_irudia\n1\n";
        exekutatuProba(sarrera, "ER5: Stockak zenbakia izan behar du");
    }

    // --- MENU NAGUSIKO PROBAK (CPM1, CPM2, CPM3, CPM4) ---

    private void exekutatuMain(String sarrera, Runnable u) {
        java.io.InputStream sysInBackup = System.in; // Backup
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(sarrera.getBytes());
            System.setIn(in);
            u.run();
        } finally {
            System.setIn(sysInBackup); // Restore
        }
    }

    @Test
    public void testCPM1_Irten() {
        assertDoesNotThrow(() -> {
            exekutatuMain("0\n", () -> App.main(new String[0]));
        });
        assertTrue(outContent.toString().contains("Agur!"));
    }

    @Test
    public void testCPM2_AukeraErronkatikKanpo() {
        assertDoesNotThrow(() -> {
            exekutatuMain("9\n0\n", () -> App.main(new String[0]));
        });
        // Bucle normal
    }

    @Test
    public void testCPM3_LetraBatSartzea() {
        assertThrows(java.util.InputMismatchException.class, () -> {
            exekutatuMain("A\n0\n", () -> App.main(new String[0]));
        });
    }

    @Test
    public void testCPM4_AukeraOndo() {
        assertDoesNotThrow(() -> {
            exekutatuMain("1\n0\n", () -> App.main(new String[0]));
        });
        assertTrue(outContent.toString().contains("ID | Izena | Prezioa"));
    }
}
