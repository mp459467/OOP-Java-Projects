package ruchMiejski;

public class LiniaTramwajowa extends Linia {
    private Tramwaj[] tramwaje;
    private final int liczbaTramwajow;
    private final int odstepMiedzyTramwajami;

    private final int nr;

    public LiniaTramwajowa(int liczbaTramwajow, Trasa trasa, int nr) {
        super(trasa);
        odstepMiedzyTramwajami = trasa.getCzasPelnegoOkrazenia() / liczbaTramwajow;
        this.nr = nr;
        this.liczbaTramwajow = liczbaTramwajow;
    }

    public int getLiczbaTramwajow() {
        return liczbaTramwajow;
    }

    public void setTramwaje(Tramwaj[] tramwaje){
        this.tramwaje = tramwaje;
    }

    public int getNr() {
        return nr;
    }

    public int getOdstepMiedzyTramwajami() {
        return odstepMiedzyTramwajami;
    }

    public Tramwaj getTramwaj(int k) {
        return tramwaje[k];
    }
}
