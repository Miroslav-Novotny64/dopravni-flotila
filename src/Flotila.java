import java.util.ArrayList;
import java.util.Random;

/**
 * Třída Flotila spravuje kolekci všech vozidel
 */
public class Flotila {
    private ArrayList<Vozidlo> vozidla;
    private Random random;

    /**
     * Konstruktor pro vytvoření flotily
     */
    public Flotila() {
        this.vozidla = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Přidá nové vozidlo do flotily
     * @param v vozidlo k přidání
     */
    public void pridejVozidlo(Vozidlo v) {
        if (v != null && !vozidla.contains(v)) {
            vozidla.add(v);
            System.out.println("Vozidlo přidáno do flotily: " + v);
        } else {
            System.out.println("Vozidlo již ve flotile existuje nebo je null");
        }
    }

    /**
     * Odebere vozidlo z flotily
     * @param v vozidlo k odebrání
     */
    public void odeberVozidlo(Vozidlo v) {
        if (vozidla.remove(v)) {
            System.out.println("Vozidlo odebráno z flotily: " + v);
        } else {
            System.out.println("Vozidlo nebylo ve flotile nalezeno");
        }
    }

    /**
     * Vypíše seznam všech vozidel včetně jejich stavu
     */
    public void vypisVozidla() {
        System.out.println("=== SEZNAM VOZIDEL FLOTILY ===");
        if (vozidla.isEmpty()) {
            System.out.println("Flotila je prázdná");
            return;
        }

        for (int i = 0; i < vozidla.size(); i++) {
            System.out.println((i + 1) + ". " + vozidla.get(i).toString());
        }
        System.out.println("Celkem vozidel: " + vozidla.size());
        System.out.println("===============================");
    }

    /**
     * Simuluje jeden den provozu flotily
     */
    public void simulujDen() {
        System.out.println("=== SIMULACE DNE ===");
        if (vozidla.isEmpty()) {
            System.out.println("Flotila je prázdná, není co simulovat");
            return;
        }

        for (Vozidlo vozidlo : vozidla) {
            int trasa;
            if (vozidlo instanceof Auto) {
                trasa = random.nextInt(201);
            } else if (vozidlo instanceof NakladniAuto) {
                trasa = random.nextInt(1001);
            } else {
                trasa = random.nextInt(501);
            }

            System.out.println("\n" + vozidlo + " - plánovaná trasa: " + trasa + " km");

            boolean maDostatekPaliva = trasa < vozidlo.getStavPaliva();

            if (!maDostatekPaliva) {
                System.out.println("⚠️  VAROVÁNÍ: " + vozidlo +
                        " nemá dostatek paliva pro plánovanou trasu! Zůstává v garáži.");
                continue;
            }

            // Pokus o pohyb
            int stavPalivaPredem = (int) vozidlo.getStavPaliva();
            int kmPredem = vozidlo.getStavKilometru();

            vozidlo.pohyb(trasa);

            // Kontrola, zda se vozidlo skutečně pohnulo
            if (vozidlo.getStavKilometru() == kmPredem) {
                System.out.println("⚠️  " + vozidlo.getZnacka() + " nemohlo dokončit cestu kvůli nedostatku paliva!");
            }
        }
        System.out.println("===================");
    }

    /**
     * Souhrnné statistiky flotily
     */
    public void report() {
        System.out.println("=== REPORT FLOTILY ===");

        if (vozidla.isEmpty()) {
            System.out.println("Flotila je prázdná");
            return;
        }

        int pocetVozidel = vozidla.size();

        int celkemRoky = 0;
        for (Vozidlo v : vozidla) {
            celkemRoky += v.getRokVyroby();
        }
        double prumernyRokVyroby = (double) celkemRoky / pocetVozidel;

        int celkemKm = 0;
        for (Vozidlo v : vozidla) {
            celkemKm += v.getStavKilometru();
        }

        int celkemPalivo = 0;
        for (Vozidlo v : vozidla) {
            celkemPalivo += (int) v.getStavPaliva();
        }
        double prumernePalivo = (double) celkemPalivo / pocetVozidel;

        int pocetAut = 0;
        int pocetNakladnichAut = 0;
        for (Vozidlo v : vozidla) {
            if (v instanceof Auto) {
                pocetAut++;
            } else if (v instanceof NakladniAuto) {
                pocetNakladnichAut++;
            }
        }

        System.out.println("Počet vozidel celkem: " + pocetVozidel);
        System.out.println("  - Osobní auta: " + pocetAut);
        System.out.println("  - Nákladní auta: " + pocetNakladnichAut);
        System.out.printf("Průměrný rok výroby: %.1f%n", prumernyRokVyroby);
        System.out.printf("Celkový počet najetých km: %,d km%n", celkemKm);
        System.out.printf("Průměrná úroveň paliva: %.1f%%%n", prumernePalivo);

        Vozidlo nejvetsiKm = vozidla.getFirst();
        for (Vozidlo v : vozidla) {
            if (v.getStavKilometru() > nejvetsiKm.getStavKilometru()) {
                nejvetsiKm = v;
            }
        }
        System.out.println("Vozidlo s nejvíce km: " + nejvetsiKm.getZnacka() +
                " (" + nejvetsiKm.getStavKilometru() + " km)");

        System.out.println("======================");
    }

    /**
     * Vrátí počet vozidel ve flotile
     * @return počet vozidel
     */
    public int getPocetVozidel() {
        return vozidla.size();
    }

    /**
     * Natankuje všechna vozidla na maximum
     */
    public void natankujVse() {
        System.out.println("Tankování všech vozidel...");
        for (Vozidlo v : vozidla) {
            int potrebnePalivo = 100 - (int) v.getStavPaliva();
            v.natankuj(potrebnePalivo);
            System.out.println(v.getZnacka() + " natankováno na 100%");
        }
    }
}
