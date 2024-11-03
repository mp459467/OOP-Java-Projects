package ruchMiejski;

public abstract class Linia {
    private final Trasa trasa;

    public Linia(Trasa trasa) {
        this.trasa = trasa;
    }

    public Trasa getTrasa() {
        return trasa;
    }
}
