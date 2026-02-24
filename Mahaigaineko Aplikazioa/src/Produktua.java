// Produktu baten datuak gordetzen dituen klasea
public class Produktua {
    int id;
    String izena;
    String deskribapena;
    double prezioa;
    int stocka;
    String irudia;
    int kategoriaId;
    String kategoriaIzena;

    // Konstruktore hutsa
    public Produktua() {
    }

    // Konstruktore osoa (produktu berria sortzeko)
    public Produktua(String izena, String deskribapena, double prezioa, int stocka, String irudia, int kategoriaId) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.prezioa = prezioa;
        this.stocka = stocka;
        this.irudia = irudia;
        this.kategoriaId = kategoriaId;
    }
}