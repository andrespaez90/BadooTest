package test.badoo.andres.badootest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import test.badoo.andres.badootest.api.controllers.RateController;
import test.badoo.andres.badootest.api.models.Transaction;
import test.badoo.andres.badootest.databinding.ActivityDeatilTrasactionBinding;
import test.badoo.andres.badootest.di.ApiModule;
import test.badoo.andres.badootest.di.AppComponent;
import test.badoo.andres.badootest.di.DaggerAppComponent;
import test.badoo.andres.badootest.ui.adapters.DetailListAdapter;

public class DetailTrasactionActivity extends AppCompatActivity {

    public static String DATA_SKU = "transaction_sku";

    private ActivityDeatilTrasactionBinding activityBinding;

    private DetailListAdapter adapter;

    private String skuTransaction;

    @Inject
    RateController rateController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_deatil_trasaction);
        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();
        daggerComponent.inject(this);
        init();
        initViews();
        gatData();
    }

    private void init() {

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            skuTransaction = extras.getString(DATA_SKU);

            activityBinding.textViewSkuTransaction.setText(skuTransaction);
        }
    }

    private void initViews() {
        RecyclerView recyclerView = activityBinding.listTransactionHistory;

        adapter = new DetailListAdapter(new ArrayList<>(), getBaseContext(), rateController);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void gatData() {

        rateController.getTransaction(new RateController.CallbackTransaction() {
            @Override
            public void success(Map<String, ArrayList<Transaction>> transaction) {
                adapter.setData(transaction.get(skuTransaction));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void fail(String message) {

            }
        });

    }


}
