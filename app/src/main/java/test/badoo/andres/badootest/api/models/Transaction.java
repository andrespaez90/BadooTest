package test.badoo.andres.badootest.api.models;


public class Transaction {

    private String amount;
    private String sku;
    private String currency;

    public Transaction(String amount, String sku, String currency) {
        this.amount = amount;
        this.sku = sku;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public String getSku() {
        return sku;
    }

    public String getCurrency() {
        return currency;
    }
}
