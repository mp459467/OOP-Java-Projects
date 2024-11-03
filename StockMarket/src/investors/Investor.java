package investors;

import orders.Order;
import simulation.Simulation;
import stock.Stock;

import java.util.HashMap;
import java.util.Random;

import static orders.Order.createOrder;

public abstract class Investor {

    private int cash;
    private final HashMap<String, Integer> stocks;

    public Investor(int cash, HashMap<String, Integer> stocks) {
        this.cash = cash;
        this.stocks = stocks;
    }

    public int getHowMuch(String id) {
        return stocks.get(id);
    }
    public int getCash() {
        return cash;
    }

    public abstract void makeDecision(int numberOfStocks,
                                      Simulation simulation, int nr, int currentTurn);

    public void makeOrder(int whichOrder, int whichStock,
                          int offset, boolean isPurchase,
                          Simulation simulation,
                          int nr, int currentTurn, int expiryDate) {
        Stock stock = simulation.getStock(whichStock);
        int price = stock.getLastPrice() + offset;
        //we dont want the price to be negative
        if (price < 0) price -= offset;
        int howMuch = generateNumberOfStocksForOrder(stock.getId(), isPurchase, price);
        if (howMuch == 0) return;
        Order order = createOrder(this, whichOrder,
                isPurchase, stock, howMuch, price, nr, currentTurn, expiryDate);
        if (isPurchase) {
            stock.addToPurchaseList(order);
        }
        else {
            stock.addToSellList(order);
        }
    }


    //generating howMuchStocks to buy/sell
    private int generateNumberOfStocksForOrder(String id, boolean isPurchase, int price) {
        Random random = new Random();
        if (isPurchase) {
            int howMuchCanHeBuy = cash;
            if (price != 0) howMuchCanHeBuy /= price;
            howMuchCanHeBuy /= 2;
            if (howMuchCanHeBuy < 2) return howMuchCanHeBuy;
            return random.nextInt(1, howMuchCanHeBuy);
        }
        int howMuchCanHeSell = stocks.get(id);
        if (howMuchCanHeSell < 2) return howMuchCanHeSell;
        return random.nextInt(1, howMuchCanHeSell);
    }

    public void sold(Stock stock, int price, int howMuch) {
        stocks.put(stock.getId(), stocks.get(stock.getId()) - howMuch);
        cash += howMuch * price;
    }

    public void purchased(Stock stock, int price, int howMuch) {
        stocks.put(stock.getId(), stocks.get(stock.getId()) + howMuch);
        cash -= howMuch * price;
    }
}
