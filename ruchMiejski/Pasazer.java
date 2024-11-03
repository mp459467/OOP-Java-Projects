package ruchMiejski;

public class Pasazer {
    private final Przystanek najblizszy;
    private final int nr;
    private Przystanek docelowy;
    public Pasazer(Przystanek najblizszy, int nr) {
        this.najblizszy = najblizszy;
        docelowy = null;
        this.nr = nr;
    }


    public Przystanek getDocelowy() {
        return docelowy;
    }
    public void setDocelowy(Przystanek docelowy) {
        this.docelowy = docelowy;
    }

    public int getNr() {
        return nr;
    }

    public Przystanek getNajblizszy() {
        return najblizszy;
    }
}
