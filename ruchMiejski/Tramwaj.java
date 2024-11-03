package ruchMiejski;

public class Tramwaj extends Pojazd {
    private Przystanek kierunek;

    public Tramwaj(int nr, LiniaTramwajowa liniaTramwajowa, int pojemnosc) {
        super(liniaTramwajowa, nr, pojemnosc);
        kierunek = liniaTramwajowa.getTrasa().getKoncowyPrzystanek();
    }
    @Override
    public LiniaTramwajowa getLinia() {
        return (LiniaTramwajowa) super.getLinia(); //TODO moga byc problemy!!! Jak nie to wyjebane essa
    }

    public Przystanek getKierunek() {
        return kierunek;
    }
    public void zmienKierunek() {
        Przystanek pocz = super.getLinia().getTrasa().getPoczatkowyPrzystanek();
        Przystanek kon = super.getLinia().getTrasa().getKoncowyPrzystanek() ;
        if(kierunek == pocz) {
            kierunek = kon;
        }
        else {
            kierunek = pocz;
        }
    }
}
