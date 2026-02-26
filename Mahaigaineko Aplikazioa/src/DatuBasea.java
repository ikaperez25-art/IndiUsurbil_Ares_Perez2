import java.sql.*;
import java.util.ArrayList;
import java.io.*;

/**
 * Datu-basearekin komunikatzeko ardura duen klasea.
 * CRUD eragiketak (Sortu, Irakurri, Eguneratu, Ezabatu) eta bestelako funtzio
 * aurreratuak kudeatzen ditu.
 */
public class DatuBasea {

    /**
     * Datu-basean dauden produktu guztiak lortzen ditu.
     * Kategoriari buruzko informazioa ere gehitzen zaio produktu bakoitzari.
     * 
     * @return Produktu guztien ArrayList bat
     */
    public static ArrayList<Produktua> produktuGuztiak() {
        ArrayList<Produktua> lista = new ArrayList<>();
        try {
            Connection con = Konexioa.getKonexioa();
            String sql = "SELECT p.id, p.izena, p.deskribapena, p.prezioa, p.stocka, p.irudia, "
                    + "p.kategoria_id, k.izena AS kategoria "
                    + "FROM produktuak p "
                    + "JOIN kategoriak k ON p.kategoria_id = k.id";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Produktua p = new Produktua();
                p.id = rs.getInt("id");
                p.izena = rs.getString("izena");
                p.deskribapena = rs.getString("deskribapena");
                p.prezioa = rs.getDouble("prezioa");
                p.stocka = rs.getInt("stocka");
                p.irudia = rs.getString("irudia");
                p.kategoriaId = rs.getInt("kategoria_id");
                p.kategoriaIzena = rs.getString("kategoria");
                lista.add(p);
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Produktu berri bat gehitzen du datu-basean.
     * 
     * @param p Gehitu beharreko Produktua objektua
     */
    public static void gehitu(Produktua p) {
        try {
            Connection con = Konexioa.getKonexioa();
            String sql = "INSERT INTO produktuak (izena, deskribapena, prezioa, stocka, irudia, kategoria_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, p.izena);
            pst.setString(2, p.deskribapena);
            pst.setDouble(3, p.prezioa);
            pst.setInt(4, p.stocka);
            pst.setString(5, p.irudia);
            pst.setInt(6, p.kategoriaId);
            pst.executeUpdate();
            System.out.println("Produktua gehituta!");
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    /**
     * Produktu baten datuak eguneratzen ditu datu-basean bere ID-a erabiliz.
     * 
     * @param id Eguneratu nahi den produktuaren identifikatzailea
     * @param p  Eguneraketarako erabiliko den informazio berria duen Produktua
     *           objektua
     */
    public static void eguneratu(int id, Produktua p) {
        try {
            Connection con = Konexioa.getKonexioa();
            String sql = "UPDATE produktuak "
                    + "SET izena=?, deskribapena=?, prezioa=?, stocka=?, irudia=? "
                    + "WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, p.izena);
            pst.setString(2, p.deskribapena);
            pst.setDouble(3, p.prezioa);
            pst.setInt(4, p.stocka);
            pst.setString(5, p.irudia);
            pst.setInt(6, id);
            pst.executeUpdate();
            System.out.println("Produktua eguneratuta!");
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    /**
     * Produktu bat bere ID-aren arabera datu-basetik ezabatzen du.
     * 
     * @param id Ezabatu nahi den produktuaren identifikatzailea
     */
    public static void ezabatu(int id) {
        try {
            Connection con = Konexioa.getKonexioa();
            String sql = "DELETE FROM produktuak WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Produktua ezabatuta!");
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    /**
     * Izenaren edo deskribapenaren zati bat erabiliz bat datozen produktuak
     * bilatzen ditu.
     * 
     * @param bilaketa Bilaketa hitza edo esaldia
     * @return Bilaketarekin bat datozen produktuen ArrayList bat
     */
    public static ArrayList<Produktua> bilatu(String bilaketa) {
        ArrayList<Produktua> lista = new ArrayList<>();
        try {
            Connection con = Konexioa.getKonexioa();
            String sql = "SELECT p.id, p.izena, p.deskribapena, p.prezioa, p.stocka, p.irudia, "
                    + "p.kategoria_id, k.izena AS kategoria "
                    + "FROM produktuak p "
                    + "JOIN kategoriak k ON p.kategoria_id = k.id "
                    + "WHERE p.izena LIKE ? OR p.deskribapena LIKE ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, "%" + bilaketa + "%");
            pst.setString(2, "%" + bilaketa + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Produktua p = new Produktua();
                p.id = rs.getInt("id");
                p.izena = rs.getString("izena");
                p.deskribapena = rs.getString("deskribapena");
                p.prezioa = rs.getDouble("prezioa");
                p.stocka = rs.getInt("stocka");
                p.irudia = rs.getString("irudia");
                p.kategoriaId = rs.getInt("kategoria_id");
                p.kategoriaIzena = rs.getString("kategoria");
                lista.add(p);
            }
            rs.close();
            pst.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
        return lista;
    }

    /**
     * CSV formatuan dagoen fitxategi bateko produktuak ikusi eta datu-basean
     * gordetzen ditu.
     * 
     * @param bidea CSV fitxategiaren bidea kokapena adierazten duena
     */
    public static void csvKargatu(String bidea) {
        try {
            Connection con = Konexioa.getKonexioa();
            BufferedReader br = new BufferedReader(new FileReader(bidea));
            br.readLine(); // Goiburuak saltatu
            int kontadorea = 0;

            String lerroa;
            while ((lerroa = br.readLine()) != null) {
                String[] datuak = lerroa.split(";");
                String sql = "INSERT INTO produktuak (izena, deskribapena, prezioa, stocka, irudia, kategoria_id) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, datuak[0]);
                pst.setString(2, datuak[1]);
                pst.setDouble(3, Double.parseDouble(datuak[2]));
                pst.setInt(4, Integer.parseInt(datuak[3]));
                pst.setString(5, datuak[4]);
                pst.setInt(6, Integer.parseInt(datuak[5]));
                pst.executeUpdate();
                pst.close();
                kontadorea++;
            }
            System.out.println(kontadorea + " produktu kargatu dira!");
            br.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }

    /**
     * Produktuen irudiak eguneratzen ditu URL zehatz batzuetara (proba gisa).
     */
    public static void irudiakEguneratu() {
        String[][] irudiak = {
                { "1", "https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=400" },
                { "2", "https://images.unsplash.com/photo-1503342217505-b0a15ec3261c?w=400" },
                { "3", "https://images.unsplash.com/photo-1542272604-787c3835535d?w=400" },
                { "4", "https://images.unsplash.com/photo-1624378439575-d8705ad7ae80?w=400" },
                { "5", "https://images.unsplash.com/photo-1598032895397-b9472444bf93?w=400" },
                { "6", "https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=400" },
                { "7", "https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400" }
        };
        try {
            Connection con = Konexioa.getKonexioa();
            for (String[] irudi : irudiak) {
                PreparedStatement pst = con.prepareStatement("UPDATE produktuak SET irudia=? WHERE id=?");
                pst.setString(1, irudi[1]);
                pst.setInt(2, Integer.parseInt(irudi[0]));
                pst.executeUpdate();
                pst.close();
            }
            System.out.println("Irudiak eguneratuta!");
            con.close();
        } catch (SQLException e) {
            System.out.println("Errorea: " + e.getMessage());
        }
    }
}
