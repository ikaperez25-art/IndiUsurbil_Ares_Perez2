import java.sql.*;
import java.util.ArrayList;
import java.io.*;

// Datu basearekin lan egiteko funtzioak
public class DatuBasea {

    // Produktu guztiak lortu
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

    // Produktu bat gehitu
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

    // Produktu bat eguneratu
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

    // Produktu bat ezabatu
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

    // Produktuak bilatu
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

    // CSV fitxategitik kargatu
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
}
