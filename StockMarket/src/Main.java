import simulation.InvalidArgumentsException;
import simulation.Simulation;

import static simulation.Reader.read;

public class Main {
    public static void main(String[] args) {
        Simulation simulation;
        try {
            simulation = read(args);
            simulation.start();
        } catch (InvalidArgumentsException e) {
            System.out.println(e.getMessage());
        }
    }
}
