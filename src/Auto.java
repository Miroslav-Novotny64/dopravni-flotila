public class Auto extends Vozidlo {
    int pocetMist;
    /**
     * Konstruktor pro Auto
     * @param znacka značka auta
     * @param rokVyroby rok výroby
     * @param stavKilometru celkový počet najetých km
     * @param stavPaliva aktuální stav palivové nádrže (0-100%)
     * @param pocetMist počet míst (max. 5)
     * @param velikostNadrze velikost palivové nádrž
     */
    public Auto(String znacka, int rokVyroby, int stavKilometru, int stavPaliva, int pocetMist, int velikostNadrze) {
        super(znacka, rokVyroby, stavKilometru, stavPaliva, velikostNadrze);
        if (pocetMist > 0 && pocetMist <= 5) {
            this.pocetMist = pocetMist;
        } else {
            throw new IllegalArgumentException("Počet míst musí být 1-5");
        }
    }

    @Override
    public void pohyb(int km) {
        double spotrebaLitru = (double) km / 10.0;
        double spotrebaProcenta = (spotrebaLitru / velikostNadrze) * 100;

        if (stavPaliva >= spotrebaProcenta) {
            stavPaliva -= spotrebaProcenta;
            stavKilometru += km;
        } else {
            throw new IllegalArgumentException("Nedostatečná kapacita palivové nádrže");
        }
    }

    public int getPocetMist() {
        return pocetMist;
    }

    @Override
    public String toString() {
        return String.format("Auto značky %s rok výroby %d se %d najetých kilometrů. Aktuální stav paliva %d. Počet míst: %d",
                znacka, rokVyroby, stavKilometru, stavPaliva, pocetMist);
    }

    public void setPocetMist(int pocetMist) {
        this.pocetMist = pocetMist;
    }
}
