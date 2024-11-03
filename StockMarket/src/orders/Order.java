package orders;

import investors.Investor;
import stock.Stock;

import static java.lang.Math.min;

public abstract class Order {

    private final Investor investor;
    private final boolean isPurchase; // false is Sell
    private final Stock stock;
    private int howMuch;
    private final int priceLimit;

    private final int nr;
    private final int turn;

    public Stock getStock() {
        return stock;
    }
    public int getHowMuch() {
        return howMuch;
    }
    public int getPriceLimit() {
        return priceLimit;
    }

    public boolean getIsPurchase() {
        return isPurchase;
    }

    public int getTurn() {
        return turn;
    }

    public int getNr() {
        return nr;
    }
    public abstract void deleteIfExpires();
    public Order(Investor investor, boolean isPurchase,
                 Stock stock, int howMuch, int priceLimit, int nr, int turn) {
        this.investor = investor;
        this.isPurchase = isPurchase;
        this.stock = stock;
        this.howMuch = howMuch;
        this.priceLimit = priceLimit;
        this.nr = nr;
        this.turn = turn;
    }


    public static Order createOrder(Investor investor,
                                    int type, boolean isPurchase,
                                    Stock stock,
                                    int howMuch, int priceLimit, int nr, int turn, int expiryDate) {
        switch (type) {
            case 0 -> {
                return new InstantOrder(investor,
                        isPurchase, stock, howMuch, priceLimit, nr, turn);
            }
            case 1 -> {
                return new ExpirationOrder(investor,
                        isPurchase, stock, howMuch, priceLimit, nr, turn, expiryDate);
            }
        }
        //case 2
        return new NoExpirationOrder(investor,
                isPurchase, stock, howMuch, priceLimit, nr, turn);
    }

    public boolean investorHasEnoughCash() {
        return investor.getCash() >= howMuch * priceLimit;

    }

    public boolean makeATransaction(Order order, Stock stock) {

        if (!this.investorHasEnoughCash()) {
            stock.removeOrder(this);
            return true;
        }

        int price;
        if (this.turn == order.turn) {
            if (this.nr < order.nr) {
                price = this.priceLimit;
            }
            else {
                price = order.priceLimit;
            }
        }
        else if (this.turn < order.turn) {
            price = this.priceLimit;
        }
        else {
            price = order.priceLimit;
        }

        int howMuchToTransact = min(this.howMuch, order.howMuch);

        this.investor.purchased(stock, price, howMuchToTransact);
        order.investor.sold(stock, price, howMuchToTransact);


        this.howMuch -= howMuchToTransact;
        order.howMuch -= howMuchToTransact;
        boolean minusOneIfDeleted = false;
        if (howMuch == 0) {
            stock.removeOrder(this);
            minusOneIfDeleted = true;
        }
        else stock.removeOrder(order);
        stock.setLastPrice(price);
        return minusOneIfDeleted;
    }
}

