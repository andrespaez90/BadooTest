package test.badoo.andres.badootest.api.models;


public class Rate {

    private String from;
    private String rate;
    private String to;

    public Rate(String from, String rate, String to) {
        this.from = from;
        this.rate = rate;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getRate() {
        return rate;
    }

    public String getTo() {
        return to;
    }
}
