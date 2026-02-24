import java.sql.*;
import java.io.*;
import java.util.ArrayList;

// JSON fitxategiak sortzeko klasea (weberako)
public class JsonEsportatzailea {

    static String karpeta = "../web/htdocs/json/";

    // Metodo nagusia: dena esportatu
    public static void esportatu() {
        new File(karpeta).mkdirs();
        esportatuProduktuak();
        esportatuEstadistikak();
    }

    // produktuak.json fitxategia sortu
    static void esportatuProduktuak() {
        ArrayList<Produktua> produktuak = DatuBasea.produktuGuztiak();

        try {
            StringBuilder json = new StringBuilder("[\n");
            boolean lehena = true;

            for (Produktua p : produktuak) {
                if (!lehena) {
                    json.append(",\n");
                }
                lehena = false;
                json.append("  {");
                json.append("\"id\": " + p.id + ", ");
                json.append("\"izena\": \"" + p.izena + "\", ");
                json.append("\"deskribapena\": \"" + p.deskribapena + "\", ");
                json.append("\"prezioa\": " + p.prezioa + ", ");
                json.append("\"stocka\": " + p.stocka + ", ");
                json.append("\"irudia\": \"" + p.irudia + "\", ");
                json.append("\"kategoria\": \"" + p.kategoriaIzena + "\"");
                json.append("}");
            }
            json.append("\n]");

            FileWriter fw = new FileWriter(karpeta + "produktuak.json");
            fw.write(json.toString());
            fw.close();
            System.out.println("  > produktuak.json sortuta");
        } catch (Exception e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // estadistikak.json fitxategia sortu
    static void esportatuEstadistikak() {
        try {
            Connection con = Konexioa.getKonexioa();
            StringBuilder json = new StringBuilder("{\n");

            // 1. Irabazi totala
            ResultSet rs1 = con.createStatement()
                    .executeQuery("SELECT COALESCE(SUM(guztira), 0) AS totala FROM eskariak");
            rs1.next();
            json.append("  \"irabazi_totala\": " + rs1.getDouble("totala") + ",\n");
            rs1.close();

            // 2. Gehien saldutako produktuak
            ResultSet rs2 = con.createStatement().executeQuery(
                    "SELECT p.izena, SUM(el.kopurua) AS saldua "
                            + "FROM eskari_lerroak el "
                            + "JOIN produktuak p ON el.produktu_id = p.id "
                            + "GROUP BY p.id ORDER BY saldua DESC LIMIT 5");
            json.append("  \"gehien_salduak\": [");
            boolean lehena = true;
            while (rs2.next()) {
                if (!lehena) {
                    json.append(", ");
                }
                lehena = false;
                json.append("{\"izena\": \"" + rs2.getString("izena")
                        + "\", \"saldua\": " + rs2.getInt("saldua") + "}");
            }
            json.append("],\n");
            rs2.close();

            // 3. Bezero onenak
            ResultSet rs3 = con.createStatement().executeQuery(
                    "SELECT e.izena, e.abizena, COUNT(es.id) AS kop "
                            + "FROM erabiltzaileak e "
                            + "JOIN eskariak es ON e.id = es.erabiltzaile_id "
                            + "GROUP BY e.id ORDER BY kop DESC LIMIT 5");
            json.append("  \"bezero_onenak\": [");
            lehena = true;
            while (rs3.next()) {
                if (!lehena) {
                    json.append(", ");
                }
                lehena = false;
                String izenOsoa = rs3.getString("izena") + " " + rs3.getString("abizena");
                json.append("{\"izena\": \"" + izenOsoa
                        + "\", \"eskaera_kop\": " + rs3.getInt("kop") + "}");
            }
            json.append("],\n");
            rs3.close();

            // 4. Stock baxuko produktuak (10 baino gutxiago)
            ResultSet rs4 = con.createStatement()
                    .executeQuery("SELECT izena, stocka FROM produktuak WHERE stocka < 10");
            json.append("  \"stock_baxua\": [");
            lehena = true;
            while (rs4.next()) {
                if (!lehena) {
                    json.append(", ");
                }
                lehena = false;
                json.append("{\"izena\": \"" + rs4.getString("izena")
                        + "\", \"stocka\": " + rs4.getInt("stocka") + "}");
            }
            json.append("]\n}");
            rs4.close();

            // Fitxategian idatzi
            FileWriter fw = new FileWriter(karpeta + "estadistikak.json");
            fw.write(json.toString());
            fw.close();
            System.out.println("  > estadistikak.json sortuta");

            con.close();
        } catch (Exception e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }
}
