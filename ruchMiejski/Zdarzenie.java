package ruchMiejski;

public class Zdarzenie {


    /* wsiadl do tramwaju
    *  nie udalo mu sie wsiasc do tramwaju
    *  wysiadl z tramwaju
    *  nie udalo mu sie wysiasc z tramwaju
    *  zrezygnowal z podrozy lub przybyl na przystanek
    * */
    private final int godzinaWMinutach;
    private final Przystanek przystanek;
    private String wstep;
    private String nazwa;
    private final Tramwaj tramwaj;
    private final Pasazer pasazer;

    public Pasazer getPasazer() {
        return pasazer;
    }
    public Tramwaj getTramwaj() {
        return tramwaj;
    }

    public Zdarzenie(int dzien, int minuty, Przystanek przystanek, Tramwaj tramwaj, Pasazer pasazer, String nazwa, String rodzaj) {
        this.godzinaWMinutach = minuty;
        this.przystanek = przystanek;
        String zero = "";
        if (minuty % 60 < 10) zero = "0";
        this.wstep = dzien + ", " + minuty / 60 + ":" + zero + minuty % 60 + ": ";
        this.nazwa = nazwa;
        this.tramwaj = tramwaj;
        this.pasazer = pasazer;
        if (rodzaj.equals("TRAMWAJOWE")) {
            String przedluzenieWstepu = "Tramwaj linii " +
                    tramwaj.getLinia().getNr() + " (nr bocz. " +
                    tramwaj.getNrBoczny() + ")";
            wstep += przedluzenieWstepu;
        }
        else if (rodzaj.equals("PODROZNEGO")) {
            String przedluzenieWstepu = "Pasażer " + this.pasazer.getNr();
            wstep += przedluzenieWstepu;
        }
        else {
            System.out.println("podano niewlasciwy rodzaj zdarzenia");
        }
    }
    public Przystanek getPrzystanek() {
        assert(przystanek != null);
        return przystanek;
    }
    public int getGodzinaWMinutach() {
        return godzinaWMinutach;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String toString() {
        return switch (nazwa) {
            case "WSIADA" ->
                    wstep + " wsiadł do tramwaju linii " +
                    tramwaj.getLinia().getNr() + " (nr bocz. " +
                    tramwaj.getNrBoczny() + ") z zamiarem dojechania" +
                            " do przystanku " + przystanek.getNazwa() + ".";
            case "NIEWSIADA" ->
                    wstep + " nie wsiadł do tramwaju linii " +
                    tramwaj.getLinia().getNr() + " (nr bocz. " +
                    tramwaj.getNrBoczny() +
                    ") z powodu braku wolnych miejsc (pozostaje na przystanku " +
                    przystanek.getNazwa() + ").";
            case "WYSIADA" ->
                    wstep + " wysiadł z tramwaju linii " +
                    tramwaj.getLinia().getNr() + " (nr bocz. " +
                    tramwaj.getNrBoczny() +
                    ") teraz oczekuje na przystanku " +
                    przystanek.getNazwa() + ".";
            case "NIEWYSIADA" ->
                    wstep + " nie wysiadł na przystanku docelowym " + przystanek.getNazwa() +
                    " z powodu braku wolnych miejsc (pozostaje w tramwaju " +
                    tramwaj.getLinia().getNr() + " (nr bocz. " +
                    tramwaj.getNrBoczny() + ").";
            case "REZYGNACJA" ->
                    wstep + " zrezygnował z podróży z powodu braku " +
                    "dostępnych miejsc na przystanku " + przystanek.getNazwa() + ".";
            case "PRZYBYCIE" ->
                    wstep + " przybył na przystanek " + przystanek.getNazwa() + ".";
            case "PRZYJAZD" ->
                    wstep + " przyjechał na przystanek " + przystanek.getNazwa() + ".";
            case "ODJAZD" ->
                    wstep + " odjechał z przystanku " + przystanek.getNazwa() +
                            " (w kierunku " + tramwaj.getKierunek().getNazwa() + ").";
            case "KONIECTRASY" ->
                    wstep + " dojechał do ostatniego przystanku na Trasie: " + przystanek.getNazwa() +
                            ".";
            default -> "nie podano prawidlowego rodzaju: " + nazwa;
        };
    }

    public String getNazwa() {
        return nazwa;
    }
}
