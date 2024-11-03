package ruchMiejski;

import java.util.Arrays;

public class Symulacja {

    //Parametry Symulacji
    private int lacznaLiczbaPrzejazdowPasazerow;

    private int[] LiczbaPrzejazdowDlaKazdegoDnia;
    private int lacznyCzasCzekaniaNaPrzystanku;

    private int godzinaOstaniegoZdarzenia;

    private int[] lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia;


    private int lacznieIleRazyKtosCzekal;

    private KolejkaZdarzen kolejkaZdarzen;

    private Pasazer[] pasazerowie;

    //Parametry wejsciowe
    private final Przystanek[] przystanki;
    private final LiniaTramwajowa[] linie;

    private final int liczbaDni;
    private final int pojemnoscTramwaju;

    //dane wyjsciowe
    private final String[] wczytaneParametry;
    private final int rozmiarWczytanych;
    private String[] zapis;
    private int rozmiarZapisu;

    public Symulacja(int liczbaDni, int liczbaPasazerow, int pojemnoscTramwaju,
                     Przystanek[] przystanki, LiniaTramwajowa[] linie,
                     String[] wczytaneParametry, int rozmiarWczytanych) {
        this.liczbaDni = liczbaDni;
        pasazerowie = new Pasazer[liczbaPasazerow];
        this.pojemnoscTramwaju = pojemnoscTramwaju;
        this.przystanki = przystanki;
        this.linie = linie;
        lacznaLiczbaPrzejazdowPasazerow = 0;
        lacznyCzasCzekaniaNaPrzystanku = 0;
        godzinaOstaniegoZdarzenia = 0;
        lacznieIleRazyKtosCzekal = 0;
        kolejkaZdarzen = new KolejkaZdarzen();
        LiczbaPrzejazdowDlaKazdegoDnia = new int[liczbaDni]; //sa domyslnie wypelnione zerami
        lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia = new int[liczbaDni];
        this.wczytaneParametry = wczytaneParametry;
        zapis = new String[0];
        this.rozmiarWczytanych = rozmiarWczytanych;
    }
    private void dodajDoZapisu(Zdarzenie zdarzenie) {
        if (rozmiarZapisu == zapis.length) {
            zapis = Arrays.copyOf(zapis, rozmiarZapisu * 2 + 2);
        }
        zapis[rozmiarZapisu++] = zdarzenie.toString();
    }
    private void wygenerujPasazerow() {
        for (int i = 0; i < pasazerowie.length; i++) {
            int n = Losowanie.losuj(0, przystanki.length - 1);
            pasazerowie[i] = new Pasazer(przystanki[n], i);
        }
    }

    private void wygenerujTramwaje() {
        int licznik = 0;
        for (LiniaTramwajowa liniaTramwajowa : linie) {
            int liczbaTramwajow = liniaTramwajowa.getLiczbaTramwajow();
            Tramwaj[] tramwaje = new Tramwaj[liczbaTramwajow];
            for (int j = 0; j < liczbaTramwajow; j++) {
                tramwaje[j] = new Tramwaj(licznik++, liniaTramwajowa, pojemnoscTramwaju);
            }
            liniaTramwajowa.setTramwaje(tramwaje);
        }
    }

    private int wJednaStrone(int odkad, int dokad, int godzina, LiniaTramwajowa liniaTramwajowa, int dzien, Tramwaj tramwaj) {
        //w pierwsza strone
        if (odkad == 0) {
            for (int d = odkad; d < dokad; d++) {
                Przystanek przystanek = liniaTramwajowa.getTrasa().getPrzystanki()[d];
                Zdarzenie przyjazd = new Zdarzenie(dzien, godzina,
                        przystanek, tramwaj, null, "PRZYJAZD", "TRAMWAJOWE");
                godzina += liniaTramwajowa.getTrasa().getCzasDojazdu(d);
                kolejkaZdarzen.wstawNoweZdarzenie(przyjazd);
            }

            Przystanek przystanek = liniaTramwajowa.getTrasa().getPrzystanki()[dokad];
            kolejkaZdarzen.wstawNoweZdarzenie(new Zdarzenie(dzien, godzina,
                    przystanek, tramwaj, null,"KONIECTRASY", "TRAMWAJOWE"));
        }
        else {
            for (int d = odkad; d > dokad; d--) {
                Przystanek przystanek = liniaTramwajowa.getTrasa().getPrzystanki()[d];
                Zdarzenie przyjazd = new Zdarzenie(dzien, godzina,
                        przystanek, tramwaj, null, "PRZYJAZD", "TRAMWAJOWE");
                godzina += liniaTramwajowa.getTrasa().getCzasDojazdu(d - 1);
                kolejkaZdarzen.wstawNoweZdarzenie(przyjazd);
            }

            Przystanek przystanek = liniaTramwajowa.getTrasa().getPrzystanki()[0];
            kolejkaZdarzen.wstawNoweZdarzenie(new Zdarzenie(dzien, godzina,
                    przystanek, tramwaj, null,"KONIECTRASY", "TRAMWAJOWE"));

        }
        godzina += liniaTramwajowa.getTrasa().getCzasPostojuNaPetli();
        return godzina;
    }
    private void dodajPrzyjazdy(int dzien) {
        for (LiniaTramwajowa liniaTramwajowa : linie) {

            int liczbaTramwajow = liniaTramwajowa.getLiczbaTramwajow();
            int ilePrzystankowNaTrasie = liniaTramwajowa.getTrasa().getPrzystanki().length;
            //pierwsza wieksza polowa z jednego konca
            for (int b = 0; b < (liczbaTramwajow + 1) / 2; b++) {

                int godzinaWMinutach = 60 * 6 + liniaTramwajowa.getOdstepMiedzyTramwajami() * b; // 6:00 + odstep
                int ilePetliZrobi = 0;

                while (godzinaWMinutach + ilePetliZrobi * liniaTramwajowa.getTrasa().getCzasPelnegoOkrazenia() <= 60 * 23) {
                    ilePetliZrobi++;
                }

                for (int c = 0; c < ilePetliZrobi; c++) {

                    godzinaWMinutach = wJednaStrone(0, ilePrzystankowNaTrasie - 1,
                            godzinaWMinutach, liniaTramwajowa, dzien, liniaTramwajowa.getTramwaj(b));

                    godzinaWMinutach = wJednaStrone(ilePrzystankowNaTrasie - 1, 0,
                            godzinaWMinutach, liniaTramwajowa, dzien, liniaTramwajowa.getTramwaj(b));

                }
            }

            for (int b = (liczbaTramwajow + 1) / 2; b < liczbaTramwajow; b++) {

                int godzinaWMinutach = 60 * 6 + liniaTramwajowa.getOdstepMiedzyTramwajami() * (b - (liczbaTramwajow + 1) / 2); // 6:00 + odstep
                int ilePetliZrobi = 0;
                if(dzien == 0) liniaTramwajowa.getTramwaj(b).zmienKierunek(); //ustawiamy wlasciwy kierunek tramwajom

                while (godzinaWMinutach + ilePetliZrobi * liniaTramwajowa.getTrasa().getCzasPelnegoOkrazenia() <= 60 * 23) {
                    ilePetliZrobi++;
                }

                for (int c = 0; c < ilePetliZrobi; c++) {

                    godzinaWMinutach = wJednaStrone(ilePrzystankowNaTrasie - 1, 0,
                            godzinaWMinutach, liniaTramwajowa, dzien, liniaTramwajowa.getTramwaj(b));

                    godzinaWMinutach = wJednaStrone(0, ilePrzystankowNaTrasie - 1,
                            godzinaWMinutach, liniaTramwajowa, dzien, liniaTramwajowa.getTramwaj(b));
                }
            }
        }
    }

    private void pasazerPrzybywa(int dzien, Pasazer pasazer) {
        int godzinaWMinutach = Losowanie.losuj(6*60, 12*60);
        Zdarzenie przybycie = new Zdarzenie(dzien,
                godzinaWMinutach, pasazer.getNajblizszy(),
                null, pasazer,"PRZYBYCIE", "PODROZNEGO");
        kolejkaZdarzen.wstawNoweZdarzenie(przybycie);
    }

    private Przystanek wygenerujPrzystanekDocelowy(Przystanek p, Trasa t, Tramwaj tramwaj) {
        int ktory;
        for (ktory = 0; ktory < t.getPrzystanki().length; ktory++) {
            if(p == t.getPrzystanki()[ktory]) break;
        }

        int wynik;
        if (tramwaj.getKierunek() == t.getKoncowyPrzystanek()) {
            wynik = Losowanie.losuj(ktory + 1, t.getPrzystanki().length - 1);
        }
        else {
            wynik = Losowanie.losuj(0, ktory - 1);
        }
        return t.getPrzystanki()[wynik];
    }

    private void wydarzSie(Zdarzenie zdarzenie, int dzienZ) {

        /*mamy tylko 2 wydarzenia, ktore maja wplyw na dalszy przebieg dnia,
        przyjazd oraz przybycie, pozostalych nie musimy wrzucac na kolejke zdarzen
        * */
        dodajDoZapisu(zdarzenie);
        int godzinaWMinutachZ = zdarzenie.getGodzinaWMinutach();
        Przystanek przystanekZ = zdarzenie.getPrzystanek();
        Tramwaj tramwajZ = zdarzenie.getTramwaj();
        Pasazer pasazerZ = zdarzenie.getPasazer();
        godzinaOstaniegoZdarzenia = godzinaWMinutachZ;


        switch (zdarzenie.getNazwa()) {
            case "PRZYJAZD" -> {

                Trasa trasaZ = tramwajZ.getLinia().getTrasa();

                Zdarzenie odjazd = new Zdarzenie(dzienZ, godzinaWMinutachZ,
                        przystanekZ, tramwajZ, pasazerZ, "ODJAZD", "TRAMWAJOWE");


                Pasazer[] wysiadajacy = new Pasazer[tramwajZ.getIleZajetych()];
                int ileWysiada = 0;

                //proces wysiadania
                for (int i = 0; i < tramwajZ.getIleZajetych(); i++) {

                    Pasazer pasazer = tramwajZ.getPasazer(i);
                    boolean jestMiejsceNaPrzystanku = przystanekZ.getPojemnosc()
                            - ileWysiada > przystanekZ.getIleZajetych();

                    if (pasazer.getDocelowy() == przystanekZ) {
                        if (jestMiejsceNaPrzystanku) {
                            wysiadajacy[ileWysiada] = pasazer;
                            ileWysiada++;
                            tramwajZ.zwolnijMiejsce(i);
                            i--; // ile zajetych sie zmienilo i na i-tym miejscu jest teraz inny pasazer
                            dodajDoZapisu(new Zdarzenie(dzienZ, godzinaWMinutachZ,
                                    przystanekZ, tramwajZ, pasazer, "WYSIADA", "PODROZNEGO"));

                            lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[dzienZ] -= godzinaWMinutachZ;
                            lacznyCzasCzekaniaNaPrzystanku -= godzinaWMinutachZ;
                            lacznieIleRazyKtosCzekal++;
                        } else {
                            dodajDoZapisu(new Zdarzenie(dzienZ, godzinaWMinutachZ,
                                    przystanekZ, tramwajZ, pasazer, "NIEWYSIADA", "PODROZNEGO"));
                        }
                    }
                }

                // pasazerowie wsiadaja do tramwaju
                while (tramwajZ.czyJestWolneMiejsce()
                        && przystanekZ.getIleZajetych() > 0) {

                    Pasazer pasazer = przystanekZ.zwolnijMiejsce();
                    Przystanek docelowy = wygenerujPrzystanekDocelowy(przystanekZ, trasaZ, tramwajZ);
                    pasazer.setDocelowy(docelowy);
                    tramwajZ.zajmijMiejsce(pasazer);
                    dodajDoZapisu(new Zdarzenie(dzienZ, godzinaWMinutachZ,
                            docelowy, tramwajZ, pasazer, "WSIADA", "PODROZNEGO"));

                    lacznaLiczbaPrzejazdowPasazerow++;
                    LiczbaPrzejazdowDlaKazdegoDnia[dzienZ]++;
                    lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[dzienZ] += godzinaWMinutachZ;
                    lacznyCzasCzekaniaNaPrzystanku += godzinaWMinutachZ;
                }

                // pasazerowie, ktorym nie udalo sie wsiasc do tramwaju
                for (int i = 0; i < przystanekZ.getIleZajetych(); i++) {
                    Zdarzenie niepowodzenie = new Zdarzenie(dzienZ, godzinaWMinutachZ,
                            przystanekZ, tramwajZ, przystanekZ.getPasazer(i), "NIEWSIADA", "PODROZNEGO");
                    dodajDoZapisu(niepowodzenie);
                }

                //pasazerzy ktorzy wysiedli zajmuja miejsca na przystanku, (aby nie wsiedli do tego samego tramwaju)
                for (int i = 0; i < ileWysiada; i++) {
                    przystanekZ.zajmijMiejsce(wysiadajacy[i]);
                }

                if (godzinaWMinutachZ <= 23 * 60) dodajDoZapisu(odjazd);
            }
            case "PRZYBYCIE" -> {
                if (!przystanekZ.czyJestWolneMiejsce()) {
                    zdarzenie.setNazwa("REZYGNACJA");
                } else {
                    przystanekZ.zajmijMiejsce(pasazerZ);
                }
                lacznieIleRazyKtosCzekal++;
                lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[dzienZ] += godzinaWMinutachZ;
                lacznyCzasCzekaniaNaPrzystanku += godzinaWMinutachZ;
            }
            case "KONIECTRASY" -> {
                tramwajZ.zmienKierunek();
                //pasazerowie wysiadaja
                for (int i = 0; i < tramwajZ.getIleZajetych(); i++) {

                    Pasazer pasazer = tramwajZ.getPasazer(i);

                    if (pasazer.getDocelowy() == przystanekZ) {
                        if (przystanekZ.czyJestWolneMiejsce()) {
                            tramwajZ.zwolnijMiejsce(i);
                            przystanekZ.zajmijMiejsce(pasazer);
                            i--; // ile zajetych sie zmienilo i na i-tym miejscu jest teraz inny pasazer
                            dodajDoZapisu(new Zdarzenie(dzienZ, godzinaWMinutachZ,
                                    przystanekZ, tramwajZ, pasazer, "WYSIADA", "PODROZNEGO"));

                            lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[dzienZ] -= godzinaWMinutachZ;
                            lacznyCzasCzekaniaNaPrzystanku -= godzinaWMinutachZ;
                            lacznieIleRazyKtosCzekal++;
                        } else {
                            dodajDoZapisu(new Zdarzenie(dzienZ, godzinaWMinutachZ,
                                    przystanekZ, tramwajZ, pasazer, "NIEWYSIADA", "PODROZNEGO"));
                        }
                    }
                }
            }
        }
    }

    private void zakonczdzien(int dzien) {
        // pasazerowie opuszczaja tramwaje i przystanki
        for (LiniaTramwajowa liniaTramwajowa : linie) {
            int n = liniaTramwajowa.getLiczbaTramwajow();
            for (int j = 0; j < n; j++) {
                liniaTramwajowa.getTramwaj(j).uczynPustym();
            }
        }

        for (Przystanek przystanek : przystanki) {
                przystanek.uczynPustym();
            lacznyCzasCzekaniaNaPrzystanku += godzinaOstaniegoZdarzenia;
            lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[dzien] += godzinaOstaniegoZdarzenia;
        }
    }

    public void przeprowadz() {
        wygenerujPasazerow();
        wygenerujTramwaje();

        for (int dzien = 0; dzien < liczbaDni; dzien++) {
            dodajPrzyjazdy(dzien);

            for (Pasazer pasazer : pasazerowie) {
                pasazerPrzybywa(dzien, pasazer);
            }

            while(!kolejkaZdarzen.czyPusta()) {
                wydarzSie(kolejkaZdarzen.pobraniePierwszego(), dzien);
            }
            zakonczdzien(dzien);
        }
        wypisz();
    }

    private void wypisz() {
        System.out.println("Wczytane Dane:\n");
        for (int i = 0; i < rozmiarWczytanych; ++i) {
            System.out.println(wczytaneParametry[i]);
        }

        System.out.println("\nZapis symulacji:");
        for (int i = 0; i < rozmiarZapisu; ++i) {
            System.out.println(zapis[i]);
        }

        System.out.println("\nStatystyki symulacji:");
        System.out.println("\nLaczna liczba przejazdow pasazerow: "
                + lacznaLiczbaPrzejazdowPasazerow);
        System.out.println("Sredni czas czekania na przystanku: "
                + lacznyCzasCzekaniaNaPrzystanku / lacznieIleRazyKtosCzekal);

        System.out.println("\nDla kazdego dnia:\n");
        for (int i = 0; i < liczbaDni; i++) {
            System.out.println("dzien " + i + ":");
            System.out.println("Laczna liczba przejazdow pasazerow: "
                    + LiczbaPrzejazdowDlaKazdegoDnia[i]);
            System.out.println("Laczny czas czekania na przystanku: "
                    + lacznyCzasCzekaniaNaPrzystankuDlaKazdegoDnia[i]);
        }
    }
}
