package orders;

import investors.Investor;
import stock.Stock;

public class ExecuteOrCancelOrder extends Order {

    //simulation is withouot these
    public ExecuteOrCancelOrder(Investor investor, boolean isPurchase, Stock stock, int howMuch, int priceLimit, int nr, int turn) {
        super(investor, isPurchase, stock, howMuch, priceLimit, nr, turn);
    }

    public void deleteIfExpires() {
    }
}
