package ruchMiejski;

import java.util.Random;

public class Losowanie {
    public static int losuj(int dolna, int gorna) {
        Random generator = new Random();
        return generator.nextInt(gorna - dolna + 1) + dolna;
    }
}
