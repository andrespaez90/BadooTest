package test.badoo.andres.badootest;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import test.badoo.andres.badootest.api.controllers.RateController;
import test.badoo.andres.badootest.api.models.Transaction;
import test.badoo.andres.badootest.databinding.ActivityMainBinding;
import test.badoo.andres.badootest.di.ApiModule;
import test.badoo.andres.badootest.di.AppComponent;
import test.badoo.andres.badootest.di.DaggerAppComponent;
import test.badoo.andres.badootest.ui.adapters.MainListAdapter;

public class MainActivity extends AppCompatActivity {

    private MainListAdapter adapter;

    private ActivityMainBinding mainBinding;

    @Inject
    RateController rateController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        AppComponent daggerComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();
        daggerComponent.inject(this);
        initViews();
        gateData();
    }

    private void initViews() {
        RecyclerView recyclerView = mainBinding.mainList;

        adapter = new MainListAdapter(new HashMap<>(), getBaseContext());

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void gateData() {

        rateController.getTransaction(new RateController.CallbackTransaction() {
            @Override
            public void success(Map<String, ArrayList<Transaction>> transaction) {
                adapter.setData(transaction);
                adapter.notifyDataSetChanged();
                mainBinding.mainProgressBar.setVisibility(View.GONE);
                mainBinding.mainList.setVisibility(View.VISIBLE);
            }

            @Override
            public void fail(String message) {

            }
        });

    }

}
