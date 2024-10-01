import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ElprisGuiden {

    //Scanner to read user inputs from keyboard
    public static Scanner sc = new Scanner(System.in);
    public static String goBack = ("\nTryck \"Enter\" för att gå till huvudmenyn");

    public static void main(String[] args) {
        Price[] price = new Price[24];

        price[0] = new Price("00-01", 29);
        price[1] = new Price("01-02", 33);
        price[2] = new Price("02-03", 46);
        price[3] = new Price("03-04", 57);
        price[4] = new Price("04-05", 64);
        price[5] = new Price("05-06", 75);
        price[6] = new Price("06-07", 89);
        price[7] = new Price("07-08", 150);
        price[8] = new Price("08-09", 175);
        price[9] = new Price("09-10", 86);
        price[10] = new Price("10-11", 57);
        price[11] = new Price("11-12", 78);
        price[12] = new Price("12-13", 68);
        price[13] = new Price("13-14", 45);
        price[14] = new Price("14-15", 41);
        price[15] = new Price("15-16", 31);
        price[16] = new Price("16-17", 46);
        price[17] = new Price("17-18", 58);
        price[18] = new Price("18-19", 53);
        price[19] = new Price("19-20", 51);
        price[20] = new Price("20-21", 43);
        price[21] = new Price("21-22", 37);
        price[22] = new Price("22-23", 35);
        price[23] = new Price("23-00", 31);


        String choice;
        do {
            //Main menu
            System.out.println("\nElpriser");
            System.out.println("========");
            System.out.println("1. Inmatning");
            System.out.println("2. Min, Max och Medel");
            System.out.println("3. Sortera");
            System.out.println("4. Bästa Laddningstid (4h)");
            System.out.println("e. Avsluta");
            System.out.println("\nAnge menyval + enter:");


            choice = sc.nextLine();

            switch (choice) {

                case "1":
                    inputPrices(price);
                    break;

                case "2":
                    displayMinMaxAvg(price);
                    break;

                case "3":
                    sortPriceList(price);
                    break;

                case "4":
                    bestChargingTime(price);
                    break;

                case "5":
                    displayAllPrices(price);
                    break;
            }

        } while (!choice.equalsIgnoreCase("e"));

        System.out.println("Avslutar...");

    }

    //Input prices for each hour of the day
    private static void inputPrices(Price[] price) {
        System.out.println("Inmatning av elpriser");
        System.out.println("Ange elpris i hela ören per kWh(Tex: 50, 80 eller 450) för följande klockslag:\n");

        for (int i = 0; i < price.length; i++) {
            boolean validInput = false;

            while (!validInput) {
                System.out.printf("kl "+ price[i].getTime()+": ");

                try{
                    price[i].setPriceKwh(sc.nextInt());
                    validInput = true;

                } catch(InputMismatchException e){
                    System.out.println("Ogiltig inmatning! Ange endast siffror i hela ören.");
                    sc.nextLine();
                }
            }
        }

        System.out.println("\nAlla priser har matats in.");
        System.out.println(goBack);
        sc.nextLine();
    }

    //Display the lowest, highest and average price
    private static void displayMinMaxAvg(Price[] price) {
        //Lowest price
        int minPrice = price[0].getPriceKwh();
        String minTime = price[0].getTime();

        for (int i = 1; i < price.length; i++) {
            if (price[i].getPriceKwh() < minPrice) {
                minPrice = price[i].getPriceKwh();
                minTime = price[i].getTime();
            }
        }

        //Highest price
        int maxPrice = price[0].getPriceKwh();
        String maxTime = price[0].getTime();

        for (int i = 1; i < price.length; i++) {
            if (price[i].getPriceKwh() > maxPrice) {
                maxPrice = price[i].getPriceKwh();
                maxTime = price[i].getTime();
            }
        }

        //Average price
        int sum = 0;
        int average;
        for (int i = 0; i < price.length; i++) {
            sum += price[i].getPriceKwh();
        }
        average = sum / price.length;

        //Print out
        System.out.println("Min, Max och Medelvärde:\n");
        System.out.println("Lägsta pris: kl " + minTime + " " + minPrice + " öre");
        System.out.println("Högsta pris: kl " + maxTime + " " + maxPrice + " öre");
        System.out.print("Medelvärde över dygnet: ");
        System.out.println(average + " öre");
        System.out.println(goBack);
        sc.nextLine();
    }

    //Sorted price list
    private static void sortPriceList(Price[] price) {
        Price[] clonedPrices = new Price[price.length];

        for (int i = 0; i < price.length; i++) {
            clonedPrices[i] = new Price(price[i].getTime(), price[i].getPriceKwh());
        }

        Arrays.sort(clonedPrices, new Comparator<Price>() {
            @Override
            public int compare(Price p1, Price p2) {
                return Integer.compare(p1.getPriceKwh(), p2.getPriceKwh());
            }
        });

        System.out.println("Sorterat på lägsta pris först:");

        //Print out a sorted price list
        for (Price sortedPrices : clonedPrices) {
            System.out.println("kl "+sortedPrices.getTime() + ": " + sortedPrices.getPriceKwh() + " öre");
        }
        System.out.println(goBack);
        sc.nextLine();
    }

    // Find the cheapest 4-hour period of the day
    private static void bestChargingTime(Price[] price) {
        int window = 4;
        double bestWindowSum = Double.MAX_VALUE;
        int currentWindowSum;

        int bestStartIndex = 0;
        int index = 0;

        for (int i = 0; i < price.length; i++) {
            currentWindowSum = 0;

            for (int j = 0; j < window; j++) {
                index = (i + j) % price.length;
                currentWindowSum += price[index].getPriceKwh();
            }

            if (currentWindowSum < bestWindowSum) {
                bestWindowSum = currentWindowSum;
                bestStartIndex = i;
            }
        }

        int bestEndIndex = (bestStartIndex + window - 1) % price.length;

        String startTime = price[bestStartIndex].getTime().substring(0, price[bestStartIndex].getTime().length() - 3);
        String endTime = price[bestEndIndex].getTime().substring(3);

        System.out.println("Bästa 4-timmars fönstret att ladda(ex. bilen) på är:\n");
        System.out.println("kl "+startTime + "-" + endTime + " med ett snittpris på: " + bestWindowSum / window + " öre per timme");
        System.out.println("\nSumma vid 11kW laddning i 4h: " + bestWindowSum * 11 / 100 + " Kronor");

        System.out.println(goBack);
        sc.nextLine();
    }

    //Display all prices
    private static void displayAllPrices(Price[] price) {
        System.out.println("Följande priser är sparade: ");
        for (Price value : price) {
            System.out.println(value);
        }
        System.out.println(goBack);
        sc.nextLine();
    }
}