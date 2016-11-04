package test.badoo.andres.badootest.api.services;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import test.badoo.andres.badootest.api.models.Rate;
import test.badoo.andres.badootest.api.models.Transaction;

public interface DataApi {

    @GET("/rates")
    Call<ArrayList<Rate>> getRates();

    @GET("/transaction")
    Call<ArrayList<Transaction>> getTransaction();
}
