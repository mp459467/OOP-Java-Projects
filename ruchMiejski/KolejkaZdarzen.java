package ruchMiejski;

import java.util.Arrays;

public class KolejkaZdarzen {


    // Kolejka zdarzen zaczyna sie od zdarzen ktore wydarza sie najpozniej
    // pierwszy element, ktory wyciagamy znajdzie sie pod indeksem ileZdarzen - 1
    private int ileZdarzen;

    private Zdarzenie[] listaZdarzen;

    public KolejkaZdarzen() {
        listaZdarzen = new Zdarzenie[0];
        ileZdarzen = 0;
    }


    public void wstawNoweZdarzenie(Zdarzenie zdarzenie) {
        if (ileZdarzen == listaZdarzen.length) {
            listaZdarzen = Arrays.copyOf(listaZdarzen, ileZdarzen * 2 + 2);
        }
        int czas = zdarzenie.getGodzinaWMinutach();
        listaZdarzen[ileZdarzen] = zdarzenie;
        int indeks = ileZdarzen - 1;

        if (indeks != -1) {
            while (indeks >= 0 && czas >= listaZdarzen[indeks].getGodzinaWMinutach()) {
                listaZdarzen[indeks + 1] = listaZdarzen[indeks];
                indeks--;
            } // element trafia na swoje wlasciwe miejsce babelkowo, aby tablica pozostala posortowana
            listaZdarzen[indeks + 1] = zdarzenie;
        }
        ileZdarzen++;
    }

    public Zdarzenie pobraniePierwszego() {
        assert !czyPusta();
        ileZdarzen--;
        Zdarzenie zdarzenie = listaZdarzen[ileZdarzen];
        listaZdarzen[ileZdarzen] = null;
        return zdarzenie;
    }
    public boolean czyPusta() {
        return ileZdarzen == 0;
    }


}
