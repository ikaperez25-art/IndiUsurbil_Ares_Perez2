import java.sql.*;

public class Konexioa {
    private static final String URL = "jdbc:mysql://localhost:3306/IndiUsurbil?serverTimezone=UTC";
    private static final String ERABILTZAILEA = "root";
    private static final String PASAHITZA = "MarkelOst05";

    public static Connection getKonexioa() throws SQLException {
        return DriverManager.getConnection(URL, ERABILTZAILEA, PASAHITZA);
    }
}
