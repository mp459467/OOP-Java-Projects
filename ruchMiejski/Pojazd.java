package ruchMiejski;

public abstract class Pojazd extends ObiektZawierajacyOgraniczonaPojemnosc {
    private final int nrBoczny;
    private final Linia linia;

    public Pojazd(Linia linia, int nrBoczny, int pojemnosc) {
        super(pojemnosc);
        this.nrBoczny = nrBoczny;
        this.linia = linia;
    }

    public Linia getLinia() {
        return linia;
    }

    public int getNrBoczny() {
        return nrBoczny;
    }
}
