package stock;

import orders.Order;

import java.util.ArrayList;
import java.util.Comparator;

public class Stock {

    private String id;

    private int lastPrice;

    private ArrayList<Order> purchaseList;

    private ArrayList<Order> sellList;

    private int[] last10Prices;

    public void addToSellList(Order order) {
        sellList.add(order);
    }

    public void addToPurchaseList(Order order) {
        purchaseList.add(order);
    }

    public void setLastPrice(int lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Stock(String id, int lastPrice) {
        this.id = id;
        this.lastPrice = lastPrice;
        purchaseList = new ArrayList<>();
        sellList = new ArrayList<>();
        last10Prices = new int[10];
        last10Prices[0] = lastPrice;
    }

    public String getId() {
        return id;
    }

    public int getLastPrice() {
        return lastPrice;
    }

    public void sort() {
        class SortPurchasesByPriceLimit implements Comparator<Order> {

            public int compare(Order a, Order b) {
                //sort in descending order of priceLimit for purchase
                return b.getPriceLimit() - a.getPriceLimit();
            }
        }

        class SortSalesByPriceLimit implements Comparator<Order> {

            public int compare(Order a, Order b) {
                //sort in ascending order of priceLimit for purchase
                return a.getPriceLimit() - b.getPriceLimit();
            }
        }

        class SortByTime implements Comparator<Order> {
            public int compare(Order a, Order b) {
                //sort in ascending order of turns, then nr
                if (a.getTurn() == b.getTurn()) {
                    return a.getNr() - b.getNr();
                }
                else return a.getTurn() - b.getTurn();
            }
        }

        //now we have everything sorted in order of price and if the price is the same,
        //orders are sorted by turn, if turn is the same, they are sorted by nr in turn
        purchaseList.sort(new SortByTime());
        sellList.sort(new SortByTime());
        purchaseList.sort(new SortPurchasesByPriceLimit());
        sellList.sort(new SortSalesByPriceLimit());
    }


    public void removeOrder(Order order) {
        if (order.getIsPurchase()) {
            purchaseList.remove(order);
        }
        else {
            sellList.remove(order);
        }
    }

    public void realizeTransactions() {
        sort();

        //go through all purchases, make transactions
        ArrayList<Order> tempPurchase = new ArrayList<>(purchaseList);
        for (Order purchase : tempPurchase) {
            //we need a copy for loop to not reach null pointers
            ArrayList<Order> temp = new ArrayList<>(sellList);
            for (Order sell : temp) {
                boolean isDeleted = purchase.makeATransaction(sell, this);
                //if the order got realized then we go to the next one
                if (isDeleted) break;
            }
        }

        tempPurchase = new ArrayList<>(purchaseList);
        for (Order purchase : tempPurchase) {
            purchase.deleteIfExpires();
        }

        ArrayList<Order> tempSell = new ArrayList<>(sellList);
        for (Order sell : tempSell) {
            sell.deleteIfExpires();
        }
    }

    public void actualizeLast10Prices(int turn) {
        last10Prices[turn % 10] = lastPrice;
    }

    public int getOneOf10LastPrices(int i) {
        return last10Prices[i];
    }
}
