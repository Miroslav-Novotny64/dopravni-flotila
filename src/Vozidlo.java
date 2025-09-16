public abstract class Vozidlo {
    protected String znacka;
    protected int rokVyroby;
    protected int stavKilometru;
    protected double stavPaliva;
    protected int velikostNadrze;


    public void setStavKilometru(int stavKilometru) {
        if (stavKilometru >= 0) {
            this.stavKilometru = stavKilometru;
        } else {
            throw new IllegalArgumentException("Stav kilometrů nemůže být záporný");
        }
    }

    public int getRokVyroby() {
        return rokVyroby;
    }

    public void setRokVyroby(int rokVyroby) {
        this.rokVyroby = rokVyroby;
    }

    public int getStavKilometru() {
        return stavKilometru;
    }

    public double getStavPaliva() {
        return stavPaliva;
    }

    public void setStavPaliva(double stavPaliva) {
        this.stavPaliva = stavPaliva;
    }

    public int getVelikostNadrze() {
        return velikostNadrze;
    }

    public void setVelikostNadrze(int velikostNadrze) {
        this.velikostNadrze = velikostNadrze;
    }

    public String getZnacka() {
        return znacka;
    }

    public void setZnacka(String znacka) {
        this.znacka = znacka;
    }

    /**
     * Konstruktor pro vytvoření vozidla
     * @param znacka značka nebo typ vozidla
     * @param rokVyroby rok výroby
     * @param stavKilometru celkový počet najetých km
     * @param stavPaliva aktuální stav palivové nádrže (0-100%)
     * @param velikostNadrze velikost palivové nádrže
     */
    public Vozidlo(String znacka, int rokVyroby, int stavKilometru, int stavPaliva, int velikostNadrze) {
        this.znacka = znacka;
        this.rokVyroby = rokVyroby;
        this.stavKilometru = stavKilometru;
        this.velikostNadrze = velikostNadrze;
        if (stavPaliva >= 0 && stavPaliva <= 100) {
            this.stavPaliva = stavPaliva;
        } else {
            throw new IllegalArgumentException("Stav paliva musí být v rozmezí 0-100%");
        }
    }

    /**
     * Abstraktní metoda pro pohyb vozidla
     * Každý typ vozidla implementuje spotřebu paliva jinak
     * @param km počet kilometrů k ujetí
     */
    public abstract void pohyb(int km);

    /**
     * Metoda pro doplnění paliva
     * @param litry počet litrů paliva k doplnění
     */
    public void natankuj(int litry) {
        double aktualniPocetLitru = stavPaliva * velikostNadrze / 100.0;
        double novePocetLitru = Math.min(aktualniPocetLitru + litry, velikostNadrze);
        stavPaliva = novePocetLitru / velikostNadrze * 100;
    }

    @Override
    public String toString() {
        return String.format("Vozidlo značky %s rok výroby %d se %d najetých kilometrů. Aktuální stav paliva %d", znacka, rokVyroby, stavKilometru, stavPaliva);
    }


    public int getDojezd() {
        return stavKilometru * velikostNadrze / 100;
    }

}
