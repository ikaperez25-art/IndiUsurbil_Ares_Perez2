import java.sql.*;

public class Konexioa {
    private static final String URL = "jdbc:mysql://localhost:3306/indiusurbil?serverTimezone=UTC";
    private static final String ERABILTZAILEA = "root";
    private static final String PASAHITZA = "gabrielito10";

    public static Connection getKonexioa() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver ez da aurkitu! Jar fitxategia falta da.");
        }
        return DriverManager.getConnection(URL, ERABILTZAILEA, PASAHITZA);
    }
}
