package ruchMiejski;

import java.util.Arrays;
import java.util.Scanner;

public class Czytnik {
    /*
      zostala utworzona, aby kod byl bardziej czytelny
    * oraz zeby atrybuty wejscia w Symulacji mozna bylo ustawic na final
    * */
    private String[] wczytaneParametry;
    private int rozmiarWczytanych;

    public Czytnik() {
        wczytaneParametry = new String[0];
        rozmiarWczytanych = 0;
    }

    public void dodajDane(String napis) {
        if (rozmiarWczytanych == wczytaneParametry.length) {
            wczytaneParametry = Arrays.copyOf(wczytaneParametry, wczytaneParametry.length * 2 + 1);
        }
        wczytaneParametry[rozmiarWczytanych++] = napis;
    }
    public Symulacja wczytaj() {
        Scanner sc = new Scanner(System.in);
        int licznik = 0;
        Przystanek[] przystanki;
        LiniaTramwajowa[] linie;

        int liczbaDni;
        int liczbaPasazerow;
        int pojemnoscPrzystanku;
        int pojemnoscTramwaju;

        for (int i = 0; i < 3; ++i) dodajDane(sc.nextLine());

        liczbaDni = Integer.parseInt(wczytaneParametry[licznik++]);
        pojemnoscPrzystanku = Integer.parseInt(wczytaneParametry[licznik++]);

        int liczbaPrzystankow = Integer.parseInt(wczytaneParametry[licznik++]);
        przystanki = new Przystanek[liczbaPrzystankow];

        for (int i = 0; i < liczbaPrzystankow; ++i) {
            dodajDane(sc.nextLine());
            Przystanek przystanek = new Przystanek(wczytaneParametry[licznik++], pojemnoscPrzystanku);
            przystanki[i] = przystanek;
        }

        for (int i = 0; i < 3; ++i) dodajDane(sc.nextLine());

        liczbaPasazerow = Integer.parseInt(wczytaneParametry[licznik++]);
        pojemnoscTramwaju = Integer.parseInt(wczytaneParametry[licznik++]);
        int liczbaLiniiTramwajowych = Integer.parseInt(wczytaneParametry[licznik++]);
        linie = new LiniaTramwajowa[liczbaLiniiTramwajowych];
        for (int i = 0; i < liczbaLiniiTramwajowych; ++i) {
            dodajDane(sc.nextLine());
            Scanner poSlowach = new Scanner(wczytaneParametry[licznik++]);
            int liczbaTramwajowLinii = Integer.parseInt(poSlowach.next());
            int dlugoscTrasy = Integer.parseInt(poSlowach.next());
            przystanki = new Przystanek[dlugoscTrasy];
            int[] czasyDojazdu = new int[dlugoscTrasy - 1];
            for (int j = 0; j < dlugoscTrasy - 1; j++) {
                przystanki[j] = new Przystanek(poSlowach.next(), pojemnoscPrzystanku);
                czasyDojazdu[j] = Integer.parseInt(poSlowach.next());
            }
            przystanki[dlugoscTrasy - 1] = new Przystanek(poSlowach.next(), pojemnoscPrzystanku);
            int czasPostojuNaPetli = Integer.parseInt(poSlowach.next());
            Trasa trasa = new Trasa(przystanki, czasyDojazdu, czasPostojuNaPetli);
            linie[i] = new LiniaTramwajowa(liczbaTramwajowLinii, trasa, i);
        }
        Symulacja symulacja = new Symulacja(liczbaDni, liczbaPasazerow, pojemnoscTramwaju, przystanki, linie, wczytaneParametry, rozmiarWczytanych);
        sc.close();
        return symulacja;
    }
}