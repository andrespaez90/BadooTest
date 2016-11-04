package test.badoo.andres.badootest.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import test.badoo.andres.badootest.DetailTrasactionActivity;
import test.badoo.andres.badootest.R;
import test.badoo.andres.badootest.api.models.Transaction;
import test.badoo.andres.badootest.databinding.ItemMainListBinding;


public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.TransactionViewHolder> {

    private Map<String, ArrayList<Transaction>> dataItems;

    private Context mContext;

    private ItemMainListBinding binding;

    public MainListAdapter(Map<String, ArrayList<Transaction>> visibleItems, Context mContext) {
        this.dataItems = visibleItems;
        this.mContext = mContext;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        binding = ItemMainListBinding.inflate(inflater, parent, true);

        TransactionViewHolder tvh = new TransactionViewHolder(binding, mContext);

        return tvh;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {

        final Iterator<String> it = dataItems.keySet().iterator();

        String sku = "";

        while (position >= 0) {
            sku = it.next();
            position--;
        }

        ArrayList item = dataItems.get(sku);

        holder.bindItem(sku, item.size());
    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }

    public void setData(Map<String, ArrayList<Transaction>> data) {
        this.dataItems = data;
    }


    public static class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemMainListBinding binding;

        private Context mContext;

        private String skuTransation;

        public TransactionViewHolder(ItemMainListBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.mContext = context;
        }

        public void bindItem(String sku, int count) {
            skuTransation = sku;
            binding.getRoot().setOnClickListener(this);
            binding.textViewTransactionTitle.setText(sku);
            binding.textViewSubtitle.setText(mContext.getString(R.string.format_count_transactions, count));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, DetailTrasactionActivity.class);
            intent.putExtra(DetailTrasactionActivity.DATA_SKU, skuTransation);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

}
