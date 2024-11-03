package ruchMiejski;

public class Przystanek extends ObiektZawierajacyOgraniczonaPojemnosc {
    private final String nazwa;

    public Przystanek(String nazwa, int pojemnosc) {
        super(pojemnosc);
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }
}
