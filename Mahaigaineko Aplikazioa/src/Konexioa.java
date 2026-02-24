import java.sql.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

public class Konexioa {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/indiusurbil?serverTimezone=UTC";
    private static final String ERABILTZAILEA = "root";
    private static final String PASAHITZA = "gabrielito10";
    private static Driver mysqlDriver = null;

    public static Connection getKonexioa() throws SQLException {
        // Classpath-etik probatu lehenik
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, ERABILTZAILEA, PASAHITZA);
        } catch (ClassNotFoundException e) {
            // Classpath-ean ez dago, JAR-a eskuz kargatuko dugu
        }

        // JAR-a eskuz kargatu eta Driver zuzenean erabili
        if (mysqlDriver == null) {
            String[] bideak = {
                    "lib/mysql-connector-j-9.6.0.jar",
                    "../lib/mysql-connector-j-9.6.0.jar",
                    "Mahaigaineko Aplikazioa/lib/mysql-connector-j-9.6.0.jar"
            };

            for (String bidea : bideak) {
                File jarFile = new File(bidea);
                if (jarFile.exists()) {
                    try {
                        URL jarUrl = jarFile.toURI().toURL();
                        URLClassLoader loader = new URLClassLoader(new URL[] { jarUrl });
                        Class<?> driverClass = Class.forName("com.mysql.cj.jdbc.Driver", true, loader);
                        mysqlDriver = (Driver) driverClass.getDeclaredConstructor().newInstance();
                        break;
                    } catch (Exception ex) {
                        System.out.println("Errorea JAR kargatzerakoan: " + ex.getMessage());
                    }
                }
            }
        }

        if (mysqlDriver == null) {
            throw new SQLException(
                    "MySQL driver ez da aurkitu! Ziurtatu 'lib/mysql-connector-j-9.6.0.jar' existitzen dela.");
        }

        Properties props = new Properties();
        props.setProperty("user", ERABILTZAILEA);
        props.setProperty("password", PASAHITZA);
        return mysqlDriver.connect(DB_URL, props);
    }
}
