package investors;

import orders.Order;
import simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InvestorSMA extends Investor {

    private final HashMap<String, Integer> sma5;
    private final HashMap<String, Integer> sma10;
    public InvestorSMA(int cash, HashMap<String, Integer> stocks) {
        super(cash, stocks);
        sma5 = new HashMap<>();
        sma10 = new HashMap<>();
    }
    @Override
    public void makeDecision(int numberOfStocks, Simulation simulation, int nr, int turn) {

        if (turn > 10) {
            Random random = new Random();
            int whichOrder = random.nextInt(0, 3);
            //we don't want too much of noExpiration orders
            if (whichOrder == 2) {
                whichOrder = random.nextInt(0, 3);
            }
            int offset = random.nextInt(0, 11);
            boolean sign = random.nextBoolean();
            int expiryDate = turn +
                    random.nextInt(0, 100);
            if (sign) {
                offset *= -1;
            }

            for (int j = 0; j < numberOfStocks; j++) {
                int avg5 = 0;
                int avg10 = 0;

                for (int i = 0; i < 5; i++) {
                    avg5 += simulation.getStock(j).getOneOf10LastPrices(i);
                }
                avg10 += avg5;

                for (int i = 5; i < 10; i++) {
                    avg10 += simulation.getStock(j).getOneOf10LastPrices(i);
                }

                avg10 /= 10;
                avg5 /= 5;
                if(turn > 11) {
                    if (sma5.get(simulation.getStock(j).getId())
                        < sma10.get(simulation.getStock(j).getId()) && avg5 > avg10) {
                        //buys
                        makeOrder(whichOrder, j, offset,
                                true, simulation, turn, nr, expiryDate);
                        break;
                    }
                    else if(sma5.get(simulation.getStock(j).getId())
                            < sma10.get(simulation.getStock(j).getId()) && avg5 < avg10) {
                        //sells
                        makeOrder(whichOrder, j, offset,
                                false, simulation, turn, nr, expiryDate);
                        break;
                    }
                }
                sma5.put(simulation.getStock(j).getId(), avg5);
                sma10.put(simulation.getStock(j).getId(), avg10);
            }
        }


    }
}
