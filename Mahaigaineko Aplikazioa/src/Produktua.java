/**
 * Produktu baten datuak gordetzen dituen klasea.
 * Datu-basetik lortutako edo datu-basera bidaliko den informazioa
 * errepresentatzen du.
 */
public class Produktua {
    int id;
    String izena;
    String deskribapena;
    double prezioa;
    int stocka;
    String irudia;
    int kategoriaId;
    String kategoriaIzena;

    /**
     * Produktuaren konstruktore hutsa.
     * Objektua sortu eta gero atributuak banan-banan ezartzeko erabiltzen da.
     */
    public Produktua() {
    }

    /**
     * Produktuaren konstruktorea datu nagusiekin.
     * Batez ere datu-basean produktu berri bat txertatu aurretik erabiltzen da.
     * 
     * @param izena        Produktuaren izena
     * @param deskribapena Produktuaren deskribapena
     * @param prezioa      Produktuaren prezioa eurotan
     * @param stocka       Hasierako stock kopurua
     * @param irudia       Irudiaren URL-a
     * @param kategoriaId  Kategoriaren identifikatzailea datu-basean
     */
    public Produktua(String izena, String deskribapena, double prezioa, int stocka, String irudia, int kategoriaId) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.prezioa = prezioa;
        this.stocka = stocka;
        this.irudia = irudia;
        this.kategoriaId = kategoriaId;
    }
}