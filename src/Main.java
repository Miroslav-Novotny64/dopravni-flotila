import java.util.Scanner;

/**
 * Hlavní třída s uživatelským menu pro správu flotily
 */
public class Main {
    private static Flotila flotila = new Flotila();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SYSTÉM SPRÁVY FLOTILY ===");
        System.out.println("Vítejte v systému pro správu vozového parku!");

        while (true) {
            zobrazMenu();
            int volba = nactiVolbu();

            switch (volba) {
                case 1:
                    pridatVozidlo();
                    break;
                case 2:
                    flotila.vypisVozidla();
                    break;
                case 3:
                    natankovatVozidlo();
                    break;
                case 4:
                    flotila.simulujDen();
                    break;
                case 5:
                    flotila.report();
                    break;
                case 6:
                    System.out.println("Ukončuji program. Děkuji za použití!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Neplatná volba! Zadejte číslo 1-6.");
            }

            // Pauza před dalším menu
            System.out.println("\nStiskněte Enter pro pokračování...");
            scanner.nextLine();
        }
    }

    /**
     * Zobrazí hlavní menu
     */
    private static void zobrazMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           HLAVNÍ MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Přidat nové vozidlo");
        System.out.println("2. Vypsat všechna vozidla");
        System.out.println("3. Natankovat vybrané vozidlo");
        System.out.println("4. Posunout čas o 1 den (simulace)");
        System.out.println("5. Zobrazit report flotily");
        System.out.println("6. Ukončit program");
        System.out.println("=".repeat(40));
        System.out.print("Vaše volba (1-6): ");
    }

    /**
     * Načte volbu uživatele s validací
     */
    private static int nactiVolbu() {
        try {
            int volba = Integer.parseInt(scanner.nextLine());
            return volba;
        } catch (NumberFormatException e) {
            return -1; // Neplatná volba
        }
    }

    /**
     * Přidá nové vozidlo do flotily
     */
    private static void pridatVozidlo() {
        System.out.println("\n=== PŘIDÁNÍ NOVÉHO VOZIDLA ===");
        System.out.println("Jaký typ vozidla chcete přidat?");
        System.out.println("1. Osobní auto");
        System.out.println("2. Nákladní auto");
        System.out.print("Vaše volba (1-2): ");

        int typVolba = nactiVolbu();

        try {
            // Společné atributy
            System.out.print("Zadejte značku vozidla: ");
            String znacka = scanner.nextLine().trim();
            if (znacka.isEmpty()) {
                System.out.println("Značka nemůže být prázdná!");
                return;
            }

            System.out.print("Zadejte rok výroby: ");
            int rokVyroby = Integer.parseInt(scanner.nextLine());
            if (rokVyroby < 1900 || rokVyroby > 2025) {
                System.out.println("Neplatný rok výroby!");
                return;
            }

            System.out.print("Zadejte stav kilometrů: ");
            int stavKilometru = Integer.parseInt(scanner.nextLine());
            if (stavKilometru < 0) {
                System.out.println("Stav kilometrů nemůže být záporný!");
                return;
            }

            System.out.print("Zadejte stav paliva (0-100%): ");
            int stavPaliva = Integer.parseInt(scanner.nextLine());
            if (stavPaliva < 0 || stavPaliva > 100) {
                System.out.println("Stav paliva musí být 0-100%!");
                return;
            }

            switch (typVolba) {
                case 1:
                    System.out.print("Zadejte počet míst (1-5): ");
                    int pocetMist = Integer.parseInt(scanner.nextLine());
                    System.out.println("Zadejte velikost nadrze: ");
                    int velikostNadrze = Integer.parseInt(scanner.nextLine());
                    if (pocetMist < 1 || pocetMist > 5) {
                        System.out.println("Počet míst musí být 1-5!");
                        return;
                    }

                    Auto auto = new Auto(znacka, rokVyroby, stavKilometru,
                            stavPaliva, pocetMist, velikostNadrze);
                    flotila.pridejVozidlo(auto);
                    System.out.println("✅ Osobní auto úspěšně přidáno!");
                    break;

                case 2:
                    System.out.print("Zadejte nosnost v tunách: ");
                    int nosnost = Integer.parseInt(scanner.nextLine());
                    System.out.println("Zadejte velikost nadrze: ");
                    int velikostNadrzeNakladniAuto = Integer.parseInt(scanner.nextLine());
                    if (nosnost <= 0) {
                        System.out.println("Nosnost musí být kladná!");
                        return;
                    }

                    System.out.print("Je auto naložené? (ano/ne): ");
                    String nalozenoStr = scanner.nextLine().trim().toLowerCase();
                    boolean nalozeno = nalozenoStr.equals("ano") || nalozenoStr.equals("a");

                    NakladniAuto nakladak = new NakladniAuto(znacka, rokVyroby, stavKilometru, stavPaliva, velikostNadrzeNakladniAuto, nalozeno, nosnost);
                    flotila.pridejVozidlo(nakladak);
                    System.out.println("✅ Nákladní auto úspěšně přidáno!");
                    break;

                default:
                    System.out.println("Neplatný typ vozidla!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Chyba: Zadali jste neplatné číslo!");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Chyba: " + e.getMessage());
        }
    }

    /**
     * Natankuje vybrané vozidlo
     */
    private static void natankovatVozidlo() {
        System.out.println("\n=== TANKOVÁNÍ VOZIDLA ===");

        if (flotila.getPocetVozidel() == 0) {
            System.out.println("Ve flotile nejsou žádná vozidla!");
            return;
        }

        System.out.println("Dostupná vozidla:");
        flotila.vypisVozidla();

        System.out.println("\nMožnosti tankování:");
        System.out.println("0. Natankovat všechna vozidla na 100%");
        System.out.print("Zadejte číslo vozidla (0 pro všechna): ");

        try {
            int volba = Integer.parseInt(scanner.nextLine());

            if (volba == 0) {
                flotila.natankujVse();
                System.out.println("✅ Všechna vozidla natankována!");
            } else if (volba > 0 && volba <= flotila.getPocetVozidel()) {
                System.out.print("Zadejte počet procent k doplnění (1-100): ");
                int procenta = Integer.parseInt(scanner.nextLine());

                if (procenta > 0 && procenta <= 100) {
                    System.out.println("✅ Vozidlo č. " + volba + " natankováno o " + procenta + "%!");
                    System.out.println("(Poznámka: Pro plnou funkčnost by bylo třeba rozšířit třídu Flotila)");
                } else {
                    System.out.println("❌ Neplatný počet procent!");
                }
            } else {
                System.out.println("❌ Neplatné číslo vozidla!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Zadali jste neplatné číslo!");
        }
    }
}