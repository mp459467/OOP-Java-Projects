package ruchMiejski;

public abstract class ObiektZawierajacyOgraniczonaPojemnosc {
    private int ileZajetych;
    private final int pojemnosc;

    private Pasazer[] pasazerowie;

    public ObiektZawierajacyOgraniczonaPojemnosc(int p) {
        ileZajetych = 0;
        pojemnosc = p;
        pasazerowie = new Pasazer[p];
    }

    public final boolean czyJestWolneMiejsce() {
        return ileZajetych < pojemnosc;
    }

    public final void zajmijMiejsce(Pasazer pasazer) {
        assert(ileZajetych < pojemnosc);
        pasazerowie[ileZajetych++] = pasazer;
    }

    public final Pasazer zwolnijMiejsce(int i) {
        assert(ileZajetych > 0);
        Pasazer tenCoZwolnil = pasazerowie[i];
        pasazerowie[i] = pasazerowie[ileZajetych - 1];
        pasazerowie[ileZajetych - 1] = null;
        ileZajetych--;
        return tenCoZwolnil;
    }

    public final Pasazer zwolnijMiejsce() {
        return zwolnijMiejsce(ileZajetych - 1);
    }
    public Pasazer getPasazer(int i) { return pasazerowie[i];}

    public int getIleZajetych() {
        return ileZajetych;
    }

    public void uczynPustym() {
        int n = ileZajetych;
        for (int i = 0; i < n; i++) {
            zwolnijMiejsce();
        }
    }

    public int getPojemnosc() {
        return pojemnosc;
    }
}
