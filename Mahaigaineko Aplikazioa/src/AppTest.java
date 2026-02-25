import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Locale;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAplikazioaInstantziak() {
        App aplikazioa = new App();
        assertNotNull(aplikazioa, "App klasea instantziatu ahal da");
    }

    @Test
    public void testFormatuaUlertzenDaBabesMekanismoarekin() {
        // Hemen frogatzen dugu EAko letrazko Prezioak zergatik kraskatzen zuen.
        // Konponbide segurua Locale.US ematea izango zen edota letrak harrapatzea
        String datua = "15.99";
        Scanner sc = new Scanner(new ByteArrayInputStream(datua.getBytes()));
        sc.useLocale(Locale.US);

        double emaitza = sc.nextDouble(); // Ez du InputMismatchException botako
        assertEquals(15.99, emaitza, "Puntua erabilita ondo esleitu da zenbakia");
    }
}
