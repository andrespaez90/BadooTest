package test.badoo.andres.badootest.api.controllers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.badoo.andres.badootest.api.models.Rate;
import test.badoo.andres.badootest.api.models.Transaction;
import test.badoo.andres.badootest.api.models.TransactionRate;
import test.badoo.andres.badootest.api.services.DataApi;

public class RateController {

    private Map<String, TransactionRate> rateGraf;

    private Map<String, ArrayList<Transaction>> transactions;

    private DataApi dataApi;

    public RateController(DataApi dataApi) {
        this.dataApi = dataApi;
        getRates();
    }

    public double transformValue(String from, String to, String valueTransaction) {
        double value = Double.valueOf(valueTransaction);
        if(rateGraf.get(from) != null){
            double rate =  rateGraf.get(from).getValue(from, to,rateGraf);
            return value * rate;
        }
        return -1;
    }

    public void getRates() {

        if (rateGraf == null) {

            Call<ArrayList<Rate>> ratesCall = dataApi.getRates();

            ratesCall.enqueue(new Callback<ArrayList<Rate>>() {
                @Override
                public void onResponse(Call<ArrayList<Rate>> call, Response<ArrayList<Rate>> response) {
                    if (response.isSuccessful()) {
                        saveRates(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Rate>> call, Throwable t) {

                }
            });
        }
    }

    private void saveRates(ArrayList<Rate> serviceRates) {
        Rate rate;
        rateGraf = new HashMap<>();
        for (int i = 0, size = serviceRates.size(); i < size; i++) {
            rate = serviceRates.get(i);
            if (rateGraf.get(rate.getFrom()) == null) {
                rateGraf.put(rate.getFrom(), new TransactionRate());
            }
            rateGraf.get(rate.getFrom()).addTo(rate.getTo(), rate.getRate());
        }

    }

    public void getTransaction(CallbackTransaction callbackTransaction) {

        if (this.transactions != null) {
            callbackTransaction.success(this.transactions);
        } else {
            getRemoteTransactions(callbackTransaction);
        }
    }

    private void getRemoteTransactions(final CallbackTransaction callbackTransaction) {
        Call<ArrayList<Transaction>> ratesCall = dataApi.getTransaction();

        ratesCall.enqueue(new Callback<ArrayList<Transaction>>() {
            @Override
            public void onResponse(Call<ArrayList<Transaction>> call, Response<ArrayList<Transaction>> response) {
                if (response.isSuccessful()) {
                    transformData(response.body());
                    callbackTransaction.success(transactions);
                }
                callbackTransaction.fail("Error");
            }

            @Override
            public void onFailure(Call<ArrayList<Transaction>> call, Throwable t) {
                callbackTransaction.fail("Error");
            }
        });
    }

    private void transformData(ArrayList<Transaction> response) {
        if (this.transactions == null) {
            this.transactions = new HashMap<>();
        }

        for (int i = 0, size = response.size(); i < size; i++) {

            Transaction transaction = response.get(i);

            ArrayList<Transaction> transactionSaved = this.transactions.get(transaction.getSku());

            if (transactionSaved != null) {

                transactionSaved.add(transaction);

            } else {
                transactionSaved = new ArrayList<>();

                transactionSaved.add(transaction);

                transactions.put(transaction.getSku(), transactionSaved);
            }
        }
    }


    public interface CallbackTransaction {

        void success(Map<String, ArrayList<Transaction>> transaction);

        void fail(String message);
    }

}
