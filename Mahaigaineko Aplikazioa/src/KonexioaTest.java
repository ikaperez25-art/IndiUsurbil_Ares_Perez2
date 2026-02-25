import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class KonexioaTest {
    @Test
    public void testKonexioaHartu() {
        assertDoesNotThrow(() -> {
            Connection con = Konexioa.getKonexioa();
            if (con != null) {
                con.close();
            }
        });
    }
}
