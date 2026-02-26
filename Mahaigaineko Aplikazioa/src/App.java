import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/*
 * BIRFAKTORIZAZIOA EGINDA:
 * "Atera metodo bat" teknika erabili da. `produktuakIkusi` eta `produktuaBilatu` 
 * metodoetan kode bikoiztua (kode usain txarra) zegoen produktuak pantailaratzeko. 
 * Funtzionalitate hori logikoki isolatu eta metodo berri batera atera da: 
 * `produktuListaBistaratu(ArrayList<Produktua> lista)`.
 * Honela, kode bikoiztua txikitzen da eta irakurketa/mantentzea errazagoa da.
 */

/**
 * Aplikazioaren menua kudeatzen duen klase nagusia.
 */
public class App {

    /**
     * Aplikazioaren metodo nagusia, menua bistaratu eta erabiltzailearen aukerak
     * kudeatzen ditu.
     * 
     * @param args Komando-lerroko argumentuak
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US);
        int aukera;

        do {
            System.out.println("\n===== INDIUSURBIL KUDEAKETA =====");
            System.out.println("1. Produktu guztiak ikusi");
            System.out.println("2. Produktu berria gehitu");
            System.out.println("3. Produktua eguneratu");
            System.out.println("4. Produktua ezabatu");
            System.out.println("5. Produktua bilatu");
            System.out.println("6. CSV-tik kargatu");
            System.out.println("7. JSON esportatu");
            System.out.println("8. Irudiak eguneratu (URL)");
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
                    csvKargatu(sc);
                    break;
                case 7:
                    JsonEsportatzailea.esportatu();
                    break;
                case 8:
                    DatuBasea.irudiakEguneratu();
                    break;
            }
        } while (aukera != 0);

        System.out.println("Agur!");
        sc.close();
    }

    /**
     * Produktu guztiak datu-basetik lortu eta pantailan erakusten ditu.
     */
    static void produktuakIkusi() {
        ArrayList<Produktua> lista = DatuBasea.produktuGuztiak();
        produktuListaBistaratu(lista);
    }

    /**
     * Erabiltzaileari datuak eskatzen dizkio eta produktu berri bat gehitzen du
     * datu-basean.
     * 
     * @param sc Scanner objektua teklatutik datuak irakurtzeko
     */
    static void produktuaGehitu(Scanner sc) {
        Produktua p = new Produktua();
        System.out.print("Izena: ");
        p.izena = sc.nextLine();
        System.out.print("Deskribapena: ");
        p.deskribapena = sc.nextLine();
        System.out.print("Prezioa: ");
        p.prezioa = sc.nextDouble();
        System.out.print("Stocka: ");
        p.stocka = sc.nextInt();
        sc.nextLine();
        System.out.print("Irudia URL: ");
        p.irudia = sc.nextLine();
        System.out.print("Kategoria ID (1-Kamisetak, 2-Prakak, 3-Alkandorak, 4-Sudaderak, 5-Jakak): ");
        p.kategoriaId = sc.nextInt();

        DatuBasea.gehitu(p);
    }

    /**
     * Produktu baten datuak eguneratzen ditu erabiltzaileak emandako
     * informazioarekin.
     * 
     * @param sc Scanner objektua teklatutik datuak irakurtzeko
     */
    static void produktuaEguneratu(Scanner sc) {
        produktuakIkusi();
        System.out.print("\nEguneratu nahi duzun produktuaren ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Produktua p = new Produktua();
        System.out.print("Izen berria: ");
        p.izena = sc.nextLine();
        System.out.print("Deskribapen berria: ");
        p.deskribapena = sc.nextLine();
        System.out.print("Prezio berria: ");
        p.prezioa = sc.nextDouble();
        System.out.print("Stock berria: ");
        p.stocka = sc.nextInt();
        sc.nextLine();
        System.out.print("Irudi berria: ");
        p.irudia = sc.nextLine();

        DatuBasea.eguneratu(id, p);
    }

    /**
     * Produktu bat bere ID-aren arabera datu-basetik ezabatzen du.
     * 
     * @param sc Scanner objektua teklatutik datuak irakurtzeko
     */
    static void produktuaEzabatu(Scanner sc) {
        produktuakIkusi();
        System.out.print("\nEzabatu nahi duzun produktuaren ID: ");
        int id = sc.nextInt();
        DatuBasea.ezabatu(id);
    }

    /**
     * Produktuak bilatzen ditu datu-basean izenaren edo deskribapenaren arabera,
     * eta emaitzak bistaratzen ditu.
     * 
     * @param sc Scanner objektua teklatutik datuak irakurtzeko
     */
    static void produktuaBilatu(Scanner sc) {
        System.out.print("Bilatu (izena edo deskribapena): ");
        String bilaketa = sc.nextLine();

        ArrayList<Produktua> lista = DatuBasea.bilatu(bilaketa);
        produktuListaBistaratu(lista);
    }

    /**
     * Produktuen zerrenda bat pantailan formatu egokian erakusten du.
     * Kode bikoiztua saihesteko atera den metodoa (Birfaktorizazioa - Atera metodo
     * bat).
     * 
     * @param lista Erakutsiko diren produktuen lista
     */
    static void produktuListaBistaratu(ArrayList<Produktua> lista) {
        System.out.println("\nID | Izena | Prezioa | Stocka | Kategoria");
        System.out.println("-------------------------------------------");
        for (Produktua p : lista) {
            System.out.println(
                    p.id + " | " + p.izena + " | " + p.prezioa + "€ | " + p.stocka + " | " + p.kategoriaIzena);
        }
    }

    /**
     * Produktuak CSV fitxategi batetik kargatzen ditu datu-basean.
     * 
     * @param sc Scanner objektua fitxategiaren bidea irakurtzeko
     */
    static void csvKargatu(Scanner sc) {
        System.out.print("CSV fitxategiaren bidea: ");
        String bidea = sc.nextLine();
        DatuBasea.csvKargatu(bidea);
    }
}
