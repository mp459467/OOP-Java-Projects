package ruchMiejski;

public class Trasa {
    private final Przystanek[] przystanki;
    private final int[] czasyDojazdu;
    private final int czasPelnegoOkrazenia;

    private final int czasPostojuNaPetli;

    public Trasa(Przystanek[] przystanki, int[] czasyDojazdu, int czasPostojuNaPetli) {
        this.przystanki = przystanki;
        this.czasyDojazdu = czasyDojazdu;
        int n = 0;
        for (int j : czasyDojazdu) n += (j * 2);
        czasPelnegoOkrazenia = n + 2 * czasPostojuNaPetli;
        this.czasPostojuNaPetli = czasPostojuNaPetli;
    }

    public int getCzasPelnegoOkrazenia() {
        return czasPelnegoOkrazenia;
    }

    public Przystanek[] getPrzystanki() {
        return przystanki;
    }
    public Przystanek getPoczatkowyPrzystanek() {
        return przystanki[0];
    }
    public Przystanek getKoncowyPrzystanek() {
        return przystanki[przystanki.length - 1];
    }

    public int getCzasDojazdu(int k) {
        return czasyDojazdu[k];
    }

    public int getCzasPostojuNaPetli() {
        return czasPostojuNaPetli;
    }
}
