import java.sql.*;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    // Web-eko JSON fitxategien karpeta
    private static final String JSON_KARPETA = "../web/htdocs/json/";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int aukera;

        do {
            System.out.println("\n===== INDIUSURBIL KUDEAKETA =====");
            System.out.println("1. Produktu guztiak ikusi");
            System.out.println("2. Produktu berria gehitu");
            System.out.println("3. Produktua eguneratu");
            System.out.println("4. Produktua ezabatu");
            System.out.println("5. Produktua bilatu");
            System.out.println("6. JSON esportatu (weberako)");
            System.out.println("0. Irten");
            System.out.print("Aukeratu: ");
            aukera = sc.nextInt();
            sc.nextLine();

            switch (aukera) {
                case 1:
                    produktuakIkusi();
                    break;
                case 2:
                    produktuaGehitu(sc);
                    break;
                case 3:
                    produktuaEguneratu(sc);
                    break;
                case 4:
                    produktuaEzabatu(sc);
                    break;
                case 5:
                    produktuaBilatu(sc);
                    break;
                case 6:
                    jsonEsportatu();
                    break;
            }
        } while (aukera != 0);

        System.out.println("Agur!");
        sc.close();
    }

    // 1. Produktu guztiak ikusi
    static void produktuakIkusi() {
        String sql = "SELECT p.id, p.izena, p.prezioa, p.stocka, k.izena AS kategoria FROM produktuak p JOIN kategoriak k ON p.kategoria_id = k.id";
        try (Connection con = Konexioa.getKonexioa();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\nID | Izena | Prezioa | Stocka | Kategoria");
            System.out.println("-------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("izena") + " | " +
                        rs.getDouble("prezioa") + "€ | " +
                        rs.getInt("stocka") + " | " +
                        rs.getString("kategoria"));
            }
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // 2. Produktu berria gehitu
    static void produktuaGehitu(Scanner sc) {
        String sql = "INSERT INTO produktuak (izena, deskribapena, prezioa, stocka, irudia, kategoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Konexioa.getKonexioa();
                PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Izena: ");
            String izena = sc.nextLine();
            System.out.print("Deskribapena: ");
            String desk = sc.nextLine();
            System.out.print("Prezioa: ");
            double prezioa = sc.nextDouble();
            System.out.print("Stocka: ");
            int stocka = sc.nextInt();
            sc.nextLine();
            System.out.print("Irudia URL: ");
            String irudia = sc.nextLine();
            System.out.print("Kategoria ID (1-Kamisetak, 2-Prakak, 3-Alkandorak, 4-Sudaderak, 5-Jakak): ");
            int katId = sc.nextInt();

            ps.setString(1, izena);
            ps.setString(2, desk);
            ps.setDouble(3, prezioa);
            ps.setInt(4, stocka);
            ps.setString(5, irudia);
            ps.setInt(6, katId);
            ps.executeUpdate();

            System.out.println("Produktua gehituta!");
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // 3. Produktua eguneratu
    static void produktuaEguneratu(Scanner sc) {
        produktuakIkusi();
        System.out.print("\nEguneratu nahi duzun produktuaren ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "UPDATE produktuak SET izena=?, deskribapena=?, prezioa=?, stocka=?, irudia=? WHERE id=?";
        try (Connection con = Konexioa.getKonexioa();
                PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Izen berria: ");
            ps.setString(1, sc.nextLine());
            System.out.print("Deskribapen berria: ");
            ps.setString(2, sc.nextLine());
            System.out.print("Prezio berria: ");
            ps.setDouble(3, sc.nextDouble());
            System.out.print("Stock berria: ");
            ps.setInt(4, sc.nextInt());
            sc.nextLine();
            System.out.print("Irudi berria: ");
            ps.setString(5, sc.nextLine());
            ps.setInt(6, id);
            ps.executeUpdate();

            System.out.println("Produktua eguneratuta!");
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // 4. Produktua ezabatu
    static void produktuaEzabatu(Scanner sc) {
        produktuakIkusi();
        System.out.print("\nEzabatu nahi duzun produktuaren ID: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM produktuak WHERE id=?";
        try (Connection con = Konexioa.getKonexioa();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Produktua ezabatuta!");
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // 5. Produktua bilatu
    static void produktuaBilatu(Scanner sc) {
        System.out.print("Bilatu (izena edo deskribapena): ");
        String bilaketa = sc.nextLine();

        String sql = "SELECT p.id, p.izena, p.prezioa, p.stocka, k.izena AS kategoria FROM produktuak p JOIN kategoriak k ON p.kategoria_id = k.id WHERE p.izena LIKE ? OR p.deskribapena LIKE ?";
        try (Connection con = Konexioa.getKonexioa();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + bilaketa + "%");
            ps.setString(2, "%" + bilaketa + "%");
            ResultSet rs = ps.executeQuery();

            System.out.println("\nID | Izena | Prezioa | Stocka | Kategoria");
            System.out.println("-------------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " +
                        rs.getString("izena") + " | " +
                        rs.getDouble("prezioa") + "€ | " +
                        rs.getInt("stocka") + " | " +
                        rs.getString("kategoria"));
            }
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    // 6. JSON esportatu weberako
    static void jsonEsportatu() {
        // Karpeta sortu ez badago
        new java.io.File(JSON_KARPETA).mkdirs();

        esportatuProduktuak();
        esportatuEstadistikak();
        System.out.println("JSON fitxategiak esportatuta!");
    }

    // Produktu guztiak JSON-era
    static void esportatuProduktuak() {
        String sql = "SELECT p.id, p.izena, p.deskribapena, p.prezioa, p.stocka, p.irudia, k.izena AS kategoria FROM produktuak p JOIN kategoriak k ON p.kategoria_id = k.id";
        try (Connection con = Konexioa.getKonexioa();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                FileWriter fw = new FileWriter(JSON_KARPETA + "produktuak.json")) {

            StringBuilder sb = new StringBuilder("[\n");
            boolean lehen = true;

            while (rs.next()) {
                if (!lehen)
                    sb.append(",\n");
                lehen = false;
                sb.append("  {");
                sb.append("\"id\": " + rs.getInt("id") + ", ");
                sb.append("\"izena\": \"" + rs.getString("izena") + "\", ");
                sb.append("\"deskribapena\": \"" + rs.getString("deskribapena") + "\", ");
                sb.append("\"prezioa\": " + rs.getDouble("prezioa") + ", ");
                sb.append("\"stocka\": " + rs.getInt("stocka") + ", ");
                sb.append("\"irudia\": \"" + rs.getString("irudia") + "\", ");
                sb.append("\"kategoria\": \"" + rs.getString("kategoria") + "\"");
                sb.append("}");
            }
            sb.append("\n]");
            fw.write(sb.toString());
            System.out.println("  > produktuak.json sortuta");

        } catch (SQLException | IOException e) {
            System.out.println("Errorea produktuak esportatzerakoan: " + e.getMessage());
        }
    }

    // Estadistikak JSON-era
    static void esportatuEstadistikak() {
        try (Connection con = Konexioa.getKonexioa();
                FileWriter fw = new FileWriter(JSON_KARPETA + "estadistikak.json")) {

            StringBuilder sb = new StringBuilder("{\n");

            // Irabazi guztizkoak
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT COALESCE(SUM(guztira), 0) AS totala FROM eskariak");
            rs1.next();
            sb.append("  \"irabazi_totala\": " + rs1.getDouble("totala") + ",\n");

            // Gehien saldutako produktuak
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(
                    "SELECT p.izena, SUM(el.kopurua) AS saldua FROM eskari_lerroak el JOIN produktuak p ON el.produktu_id = p.id GROUP BY p.id ORDER BY saldua DESC LIMIT 5");
            sb.append("  \"gehien_salduak\": [\n");
            boolean lehen = true;
            while (rs2.next()) {
                if (!lehen)
                    sb.append(",\n");
                lehen = false;
                sb.append("    {\"izena\": \"" + rs2.getString("izena") + "\", \"saldua\": " + rs2.getInt("saldua")
                        + "}");
            }
            sb.append("\n  ],\n");

            // Eskaera gehien egin dituzten bezeroak
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(
                    "SELECT e.izena, e.abizena, COUNT(es.id) AS eskaera_kop FROM erabiltzaileak e JOIN eskariak es ON e.id = es.erabiltzaile_id GROUP BY e.id ORDER BY eskaera_kop DESC LIMIT 5");
            sb.append("  \"bezero_onenak\": [\n");
            lehen = true;
            while (rs3.next()) {
                if (!lehen)
                    sb.append(",\n");
                lehen = false;
                sb.append("    {\"izena\": \"" + rs3.getString("izena") + " " + rs3.getString("abizena")
                        + "\", \"eskaera_kop\": " + rs3.getInt("eskaera_kop") + "}");
            }
            sb.append("\n  ],\n");

            // Stock baxuko produktuak (< 10)
            Statement st4 = con.createStatement();
            ResultSet rs4 = st4.executeQuery("SELECT izena, stocka FROM produktuak WHERE stocka < 10");
            sb.append("  \"stock_baxua\": [\n");
            lehen = true;
            while (rs4.next()) {
                if (!lehen)
                    sb.append(",\n");
                lehen = false;
                sb.append("    {\"izena\": \"" + rs4.getString("izena") + "\", \"stocka\": " + rs4.getInt("stocka")
                        + "}");
            }
            sb.append("\n  ]\n");

            sb.append("}");
            fw.write(sb.toString());
            System.out.println("  > estadistikak.json sortuta");

        } catch (SQLException | IOException e) {
            System.out.println("Errorea estadistikak esportatzerakoan: " + e.getMessage());
        }
    }
}
