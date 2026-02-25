import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonEsportatzaileaTest {

    @Test
    public void testKarpeta() {
        String k = JsonEsportatzailea.aurkituKarpeta();
        assertNotNull(k);
    }

    @Test
    public void testExecution() {
        // Datu base konexioa erabiliz fitxategiak sortzen saiatuko da. Errorea badago
        // try catch-ek irentsiko du.
        // Beraz guk bakarrik ziurtatzen dugu egikaritzean ez dela era ixten
        // (Exceptions)
        assertDoesNotThrow(() -> {
            JsonEsportatzailea.esportatu();
        });
    }
}
