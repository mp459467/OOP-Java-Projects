package orders;

import investors.Investor;
import stock.Stock;

public class InstantOrder extends Order {

    public InstantOrder(Investor investor, boolean isPurchase, Stock stock, int howMuch, int priceLimit, int nr, int turn) {
        super(investor, isPurchase, stock, howMuch, priceLimit, nr, turn);
    }

    @Override
    public void deleteIfExpires() {
        getStock().removeOrder(this);
    }
}
