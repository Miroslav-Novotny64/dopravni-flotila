public class Letadlo extends Vozidlo {
    private int dolet;

    public Letadlo(String znacka, int rokVyroby, int stavKilometru,
                   int stavPaliva, int velikostNadrze, int dolet) {
        super(znacka, rokVyroby, stavKilometru, stavPaliva, velikostNadrze);
        this.dolet = dolet;
    }

    public void pohyb(int km) {
        if (stavPaliva - km >= 0) {
            stavPaliva -= (int) (double) km;
        } else {
            throw new IllegalArgumentException("Nedostatečná kapacita palivové nádrž");
        }
        stavKilometru += km;
    }

    public void vzlet() {
        System.out.println("Letadlo vzlétlo");
    }

}
