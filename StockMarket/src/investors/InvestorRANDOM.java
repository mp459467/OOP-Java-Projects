package investors;

import simulation.Simulation;

import java.util.HashMap;
import java.util.Random;

public class InvestorRANDOM extends Investor {
    public InvestorRANDOM(int cash, HashMap<String,
            Integer> stocks) {
        super(cash, stocks);
    }


    @Override
    public void makeDecision(int numberOfStocks,
                             Simulation simulation, int currentTurn, int nr) {
        Random random = new Random();
        boolean isMakingAnOrder = random.nextBoolean();
        if (!isMakingAnOrder) return;
        int whichOrder = random.nextInt(0, 3);
        //we don't want too much of noExpiration orders
        if (whichOrder == 2) {
            whichOrder = random.nextInt(0, 3);
        }
        int whichStock = random.nextInt(0, numberOfStocks);
        int offset = random.nextInt(0, 11);
        boolean sign = random.nextBoolean();
        boolean isPurchase = random.nextBoolean();
        int expiryDate = currentTurn +
                random.nextInt(0, 100);
        if (sign) {
            offset *= -1;
        }
        makeOrder(whichOrder, whichStock, offset,
                isPurchase, simulation, currentTurn, nr, expiryDate);
    }
}
