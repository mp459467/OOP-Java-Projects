import investors.Investor;
import investors.InvestorRANDOM;
import orders.NoExpirationOrder;
import orders.Order;
import org.junit.Test;
import simulation.InvalidArgumentsException;
import simulation.Simulation;
import stock.Stock;

import java.io.FileNotFoundException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static simulation.Reader.read;

public class TestsJunit {

    @Test
    public void readTests() {
        String[] args = new String[0];
        assertThrows(InvalidArgumentsException.class, () -> read(args));
        String[] args1 = new String[2];
        assertThrows(InvalidArgumentsException.class, () -> read(args1));
        String[] args2 = new String[2];
        args2[0] = "not//avalid//file/path//**";
        args2[1] = "1";
        assertThrows(InvalidArgumentsException.class, () -> read(args2));
        String[] args3 = new String[2];
        args3[0] = "input.txt";
        args3[1] = "1";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (false);
        }
        args3[0] = "wrongfile.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile1.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile2.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile3.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile4.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile5.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile6.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile7.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile8.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
        args3[0] = "wrongfile9.txt";
        try {
            read(args3);
        } catch (InvalidArgumentsException e) {
            assert (true);
        }
    }

    @Test
    public void transaction() {
        Stock stock = new Stock("test", 0);
        HashMap<String, Integer> map1 = new HashMap<>();
        HashMap<String, Integer> map2 = new HashMap<>();
        map1.put("test", 10);
        map2.put("test", 10);
        Investor investor1 = new InvestorRANDOM(10000, map1);
        Investor investor2 = new InvestorRANDOM(10000, map2);
        Order purchase = new NoExpirationOrder(investor1,
                true, stock, 5, 100, 0, 0);
        Order sell = new NoExpirationOrder(investor2,
                true, stock, 10, 110, 1, 0);

        //there should be a transaction of 5 stocks for 100
        purchase.makeATransaction(sell, stock);
        assert (investor1.getHowMuch("test") == 15);
        assert (investor2.getHowMuch("test") == 5);
        assert (investor1.getCash() == 9500);
        assert (investor2.getCash() == 10500);
        assert (purchase.getHowMuch() == 0);
        assert (sell.getHowMuch() == 5);
        assert (stock.getLastPrice() == 100);
    }

    @Test
    public void SimulationTest() {
        Simulation simulation;
        try {
            simulation = read(new String[]{"input.txt", "10"});
            assertEquals(100000, simulation.start());
        } catch (InvalidArgumentsException e) {
            System.out.println(e.getMessage());
        }
        try {
            simulation = read(new String[]{"input2.txt", "100"});
            assertEquals(0, simulation.start());
        } catch (InvalidArgumentsException e) {
            System.out.println(e.getMessage());
        }
        try {
            simulation = read(new String[]{"input3.txt", "10000"});
            assertEquals(100000, simulation.start());
        } catch (InvalidArgumentsException e) {
            System.out.println(e.getMessage());
        }

    }
}
