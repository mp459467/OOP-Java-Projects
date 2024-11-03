package simulation;

import investors.Investor;
import investors.InvestorRANDOM;
import investors.InvestorSMA;
import stock.Stock;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Reader {

    public static Simulation read(String[] args) throws InvalidArgumentsException {

        if (args.length != 2) {
            throw new InvalidArgumentsException("Invalid Number Of Arguments");
        }

        ArrayList<Investor> investors = new ArrayList<>();
        HashMap<String, Integer> investorsStocks = new HashMap<>();
        ArrayList<Stock> stocks = new ArrayList<>();
        int startingCash = 0;
        ArrayList<String> allStocksTypes = new ArrayList<>();
        ArrayList<Integer> allStocksLastPrices = new ArrayList<>();
        ArrayList<String> investorStocksTypes = new ArrayList<>();
        ArrayList<Integer> startingStocksForInvestors = new ArrayList<>();
        String pathname = "src/" + args[0];
        int numberOfTurns = 0;
        try {
            numberOfTurns = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("Inappropriate second argument");
        }
        int numberOfInvestorsSMA = 0;
        int numberOfInvestorsRANDOM = 0;

        try {
            File file = new File(pathname);
            Scanner scanner = new Scanner(file);
            boolean stocksRead = false;
            boolean porfolioRead = false;
            boolean investorsRead = false;
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                Scanner byWords = new Scanner(data);
                String firstWord = byWords.next();
                if (firstWord.equals("#")) {
                    continue;
                }
                else if (!investorsRead) {
                    investorsRead = true;
                    if (firstWord.equals("R")) numberOfInvestorsRANDOM++;
                    else if (firstWord.equals("S")) numberOfInvestorsSMA++;
                    else throw new InvalidArgumentsException("Wrong input file structure");
                    while (byWords.hasNext()) {
                        String word = byWords.next();
                        if (word.equals("R")) numberOfInvestorsRANDOM++;
                        else if(word.equals("S")) numberOfInvestorsSMA++;
                        else throw new InvalidArgumentsException("Wrong input file structure");
                    }
                }
                else if (!stocksRead) {
                    stocksRead = true;
                    String[] word = firstWord.split(":");
                    if (word[0].length() > 5) {
                        throw new InvalidArgumentsException(("Wrong input file structure"));
                    }
                    if(word.length != 2)
                        throw new InvalidArgumentsException(("Wrong input file structure"));
                    allStocksTypes.add(word[0]);
                    allStocksLastPrices.add(Integer.parseInt(word[1]));
                    while (byWords.hasNext()) {
                        word = byWords.next().split(":");
                        if (word[0].length() > 5) {
                            throw new InvalidArgumentsException(("Wrong input file structure"));
                        }
                        allStocksTypes.add(word[0]);
                        allStocksLastPrices.add(Integer.parseInt(word[1]));
                    }
                }
                else if (!porfolioRead) {
                    porfolioRead = true;
                    try {
                        startingCash = Integer.parseInt(firstWord);
                    } catch (NumberFormatException e) {
                        throw new InvalidArgumentsException(("Wrong input file structure"));
                    }
                    while (byWords.hasNext()) {
                        String[] word = byWords.next().split(":");
                        if (word.length != 2)
                            throw new InvalidArgumentsException(("Wrong input file structure"));
                        if (word[0].length() > 5) {
                            throw new InvalidArgumentsException(("Wrong input file structure"));
                        }
                        investorStocksTypes.add(word[0]);
                        startingStocksForInvestors.add(Integer.parseInt(word[1]));
                    }
                }
                else {
                    throw new InvalidArgumentsException(("Wrong input file structure"));
                }
                byWords.close();
            }
            if (!stocksRead || !porfolioRead) throw new InvalidArgumentsException(("Wrong input file structure"));
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new InvalidArgumentsException("File not found");
        }


        //check if portfolio has stocks that actually exists
        for (String i : investorStocksTypes) {
            boolean it_exists = false;
            for (String j : allStocksTypes) {
                if (i.equals(j)) {
                    it_exists = true;
                    break;
                }
            }
            if(!it_exists) throw new InvalidArgumentsException("Wrong input file structure");
        }

        //data is correct

        //hashmap for all investors
        for (int i = 0; i < investorStocksTypes.size(); i++) {
            investorsStocks.put(investorStocksTypes.get(i), startingStocksForInvestors.get(i));
        }

        //hashmap for stocks
        for (int i = 0; i < allStocksTypes.size(); i++) {
            Stock stock = new Stock(allStocksTypes.get(i), allStocksLastPrices.get(i));
            stocks.add(stock);
        }

        //creating RANDOM investors
        for (int i = 0; i < numberOfInvestorsRANDOM; i++) {
            HashMap<String, Integer> map = new HashMap<>(investorsStocks);
            InvestorRANDOM investor = new InvestorRANDOM(startingCash, map);
            investors.add(investor);
        }

        for (int i = 0; i < numberOfInvestorsSMA; i++) {
            HashMap<String, Integer> map = new HashMap<>(investorsStocks);
            InvestorSMA investor = new InvestorSMA(startingCash, map);
            investors.add(investor);
        }

        return new Simulation(numberOfTurns ,investors, stocks);
    }
}
