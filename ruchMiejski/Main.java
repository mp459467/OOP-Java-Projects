package ruchMiejski;

public class Main {
    public static void main(String[] args) {
        Czytnik czytnik = new Czytnik();
        Symulacja symulacja = czytnik.wczytaj();
        symulacja.przeprowadz();
    }
}
