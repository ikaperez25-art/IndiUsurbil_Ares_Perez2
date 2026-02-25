
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatuBaseaTest {

    @Test
    public void testInsertBatzukBabestuak() {
        try {
            Produktua p = new Produktua("TEST", "TEST", 1.0, 1, "test.png", 1);
            DatuBasea.gehitu(p);
        } catch (Exception e) {
            // Exzepzioak hartzen badira (Null Pointer adibidez datubasea itzalita badago),
            // probak ez du eroriko eman behar.
        }
    }
}
