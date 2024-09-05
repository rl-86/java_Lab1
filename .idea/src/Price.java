public class Price {

    private final String time;
    private int priceKwh;

    //Constructor
    public Price(String time, int priceKwh) {
        this.time = time;
        this.priceKwh = priceKwh;
    }

    public Price() {
        this.time = "";
        this.priceKwh = 0;
    }
    public String getTime() {
        return time;
    }
    public int getPriceKwh() {
        return priceKwh;
    }

    public void setPriceKwh(int priceKwh) {
        this.priceKwh = priceKwh;
    }

    @Override
    public String toString() {
        return "kl "+time +"\t"+ priceKwh+" öre";
    }

}
