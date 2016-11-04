package test.badoo.andres.badootest.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import test.badoo.andres.badootest.api.controllers.RateController;
import test.badoo.andres.badootest.api.models.Transaction;
import test.badoo.andres.badootest.databinding.ItemMainListBinding;
import test.badoo.andres.badootest.utils.DecimalUtil;


public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.TransactionViewHolder> {

    private static final String defaultCurrency = "GBP";

    private ArrayList<Transaction> dataItems;

    private Context mContext;

    private ItemMainListBinding binding;

    private RateController rateController;

    public DetailListAdapter(ArrayList<Transaction> visibleItems, Context mContext, RateController rateController) {
        this.dataItems = visibleItems;
        this.mContext = mContext;
        this.rateController = rateController;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        binding = ItemMainListBinding.inflate(inflater, parent, true);

        TransactionViewHolder tvh = new TransactionViewHolder(binding, rateController);

        return tvh;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {

        Transaction transaction = dataItems.get(position);

        holder.bindItem(transaction);
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void setData(ArrayList<Transaction> data) {
        this.dataItems = data;
    }


    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private ItemMainListBinding binding;
        private RateController rateController;

        public TransactionViewHolder(ItemMainListBinding binding, RateController rateController) {
            super(binding.getRoot());
            this.binding = binding;
            this.rateController = rateController;
        }

        public void bindItem(Transaction transaction) {
            binding.textViewTransactionTitle.setText(transaction.getAmount() + " " + transaction.getCurrency());

            double value = 0;
            String currency = defaultCurrency;

            if (transaction.getCurrency().equals(defaultCurrency)) {
                value = Double.valueOf(transaction.getAmount());

            } else {
                value = rateController.transformValue(transaction.getCurrency(), defaultCurrency, transaction.getAmount());
                if (value == -1) {
                    value = Double.valueOf(transaction.getAmount());
                    currency = transaction.getCurrency();
                }
            }

            DecimalUtil.round(value, 2);

            binding.textViewSubtitle.setText(String.valueOf(value) + " " + currency);
        }

    }

}
