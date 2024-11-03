package simulation;

import investors.Investor;
import orders.Order;
import stock.Stock;
import java.util.ArrayList;
import java.util.Comparator;

public class Simulation {

    private final int numberOfTurns;
    private final ArrayList<Investor> investors;
    private final ArrayList<Stock> stocks;

    private final int numberOfStocks;

    public Stock getStock(int i) {
        return stocks.get(i);
    }
    public Simulation(int numberOfTurns, ArrayList<Investor> investors, ArrayList<Stock> stocks) {
        this.investors = investors;
        this.stocks = stocks;
        this.numberOfTurns = numberOfTurns;
        this.numberOfStocks = stocks.size();
    }
    private void realizeATurn(int currentTurn, int nr) {
        for (Investor investor : investors) {
            investor.makeDecision(numberOfStocks, this, nr, currentTurn);
            nr++;
        }
        for (Stock stock : stocks) {
            stock.realizeTransactions();
            stock.actualizeLast10Prices(currentTurn);
        }
    }

    private int printFinalPorfolio() {
        int invariant = 0;
        StringBuilder finalData = new StringBuilder();
        for (Investor investor : investors) {
            finalData.append(investor.getCash());
            invariant += investor.getCash();
            finalData.append(" ");
            for (Stock stock : stocks) {
                finalData.append(stock.getId());
                finalData.append(" ");
                finalData.append(investor.getHowMuch(stock.getId()));
                finalData.append(" ");
            }
            finalData.append("\n");
        }
        finalData.append("invariant = ");
        invariant /= investors.size();
        finalData.append(invariant);
        System.out.println(finalData);
        return invariant;
    }

    public int start() {
        int nr = 0;
        for (int i = 0; i < numberOfTurns; i++) {
            realizeATurn(i, nr);
        }
        return printFinalPorfolio();
    }
}