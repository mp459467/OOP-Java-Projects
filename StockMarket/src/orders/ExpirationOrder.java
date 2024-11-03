package orders;

import investors.Investor;
import stock.Stock;

public class ExpirationOrder extends Order {

    int expiryDate;
    public ExpirationOrder(Investor investor,
                           boolean isPurchase,
                           Stock stock, int howMuch,
                           int priceLimit,
                           int nr, int turn, int expiryDate) {
        super(investor, isPurchase, stock, howMuch, priceLimit, nr, turn);
        this.expiryDate = expiryDate;
    }

    @Override
    public void deleteIfExpires() {
        if (getTurn() == expiryDate) getStock().removeOrder(this);
    }
}
