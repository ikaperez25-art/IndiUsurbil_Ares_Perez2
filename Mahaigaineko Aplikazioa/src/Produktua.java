public class Produktua {
    private int id;
    private String izena;
    private String deskribapena;
    private double prezioa;
    private int stocka;
    private int kategoriaId;
    private String irudiaUrl;

    public Produktua() {}

    public Produktua(String izena, String deskribapena, double prezioa, int stocka, int kategoriaId, String irudiaUrl) {
        this.izena = izena;
        this.deskribapena = deskribapena;
        this.prezioa = prezioa;
        this.stocka = stocka;
        this.kategoriaId = kategoriaId;
        this.irudiaUrl = irudiaUrl;
    }

    public String getIzena() { return izena; }
    public String getDeskribapena() { return deskribapena; }
    public double getPrezioa() { return prezioa; }
    public int getStocka() { return stocka; }
    public int getKategoriaId() { return kategoriaId; }
    public String getIrudiaUrl() { return irudiaUrl; }
}