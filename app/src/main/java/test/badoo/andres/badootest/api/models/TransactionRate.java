package test.badoo.andres.badootest.api.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionRate {

    private Map<String, String> to;

    public TransactionRate() {
        this.to = new HashMap<>();
    }

    public void addTo(String id, String value) {
        to.put(id, value);
    }

    public double getValue(String from, String convert, Map<String, TransactionRate> rateGraf) {

        if (to.get(convert) == null) {

            for (Map.Entry<String, String> entry : to.entrySet()) {

                double auxValue = getValue(from, entry.getKey(),rateGraf);

                TransactionRate transactionRate = rateGraf.get(entry.getKey());

                if (transactionRate != null) {

                    double value = transactionRate.getValue(from,convert,rateGraf);

                    if(value != -1){
                        return auxValue * value;
                    }
                }
            }
            return -1;
        }
        return Double.valueOf(to.get(convert));
    }

}
