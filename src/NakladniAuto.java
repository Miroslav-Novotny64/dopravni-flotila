public class NakladniAuto extends Vozidlo {

    boolean nalozeno;
    int nosnost;

    /**
     * Konstruktor pro NakladniAuto
     *
     * @param znacka        značka auta
     * @param rokVyroby     rok výroby
     * @param stavKilometru celkový počet najetých km
     * @param stavPaliva    aktuální stav palivové nádrže (0-100%)
     * @param nalozeno      Jestli je auto naloženo nebo ne
     */
    public NakladniAuto(String znacka, int rokVyroby, int stavKilometru, int stavPaliva, int velikostNadrze, boolean nalozeno, int nosnost) {
        super(znacka, rokVyroby, stavKilometru, stavPaliva, velikostNadrze);
        this.nalozeno = nalozeno;
        this.nosnost = nosnost;
    }

    public boolean isNalozeno() {
        return nalozeno;
    }

    public void setNalozeno(boolean nalozeno) {
        this.nalozeno = nalozeno;
    }

    @Override
    public void pohyb(int km) {
        double zakladniSpotreba = (double) km / 5.0;
        double spotrebaLitru = nalozeno ? zakladniSpotreba * 1.5 : zakladniSpotreba;

        double spotrebaProcenta = (spotrebaLitru / velikostNadrze) * 100;

        if (stavPaliva >= spotrebaProcenta) {
            stavPaliva -= spotrebaProcenta;
            stavKilometru += km;
        } else {
            throw new IllegalArgumentException("Nedostatečná kapacita palivové nádrže");
        }
    }


    @Override
    public String toString() {
        return String.format("Auto značky %s rok výroby %d se %d najetých kilometrů. Aktuální stav paliva %f. a " + (nalozeno ? "je aktuálně naloženo" : "není aktuálně naloženo"),
                znacka, rokVyroby, stavKilometru, stavPaliva);
    }
}
