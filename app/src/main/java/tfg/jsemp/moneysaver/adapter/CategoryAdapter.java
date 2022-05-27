package tfg.jsemp.moneysaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.model.CtWrapper;
import tfg.jsemp.moneysaver.model.Transaction;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView
            .RecycledViewPool();
    private List<CtWrapper> mCategoriesWrapper;
    private final LayoutInflater mInflater;


    public CategoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_transaction, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        if(mCategoriesWrapper != null) {
            CtWrapper categoryWrapper = mCategoriesWrapper.get(position);
            //TODO set image
            holder.tvSaldo.setText(
                    (categoryWrapper.getCategory().getIncome() - categoryWrapper.getCategory().getExpense()) + " €"
            );
            holder.tvCategory.setText(categoryWrapper.getCategory().getName());
            //****NESTED RECYCLER VIEW****//
           LinearLayoutManager layoutManager = new LinearLayoutManager(
                    holder.rvTransaction.getContext(),
                   LinearLayoutManager.VERTICAL, false
            );
           layoutManager.setInitialPrefetchItemCount(
                   categoryWrapper.getTransactions().size()
           );

           TransactionAdapter transactionAdapter = new TransactionAdapter(
                   categoryWrapper.getTransactions()
           );
           //****ASIGNACION DEL HIJO EN EL PADRE****//
           holder.rvTransaction.setLayoutManager(layoutManager);
           holder.rvTransaction.setAdapter(transactionAdapter);
           holder.rvTransaction.setRecycledViewPool(viewPool);
        }
    }

    @Override
    public int getItemCount() {
        if(mCategoriesWrapper != null) {
            return mCategoriesWrapper.size();
        }
        else {
            return 0;
        }
    }

    public void setCategories(List<CtWrapper> categories) {
        mCategoriesWrapper = categories;
        notifyDataSetChanged();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvTransaction;
        private ImageView ivCategory;
        private TextView tvCategory;
        private TextView tvSaldo;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iniciaViews();
        }

        private void iniciaViews() {
            this.rvTransaction = itemView.findViewById(R.id.rvTransactions);
            this.ivCategory = itemView.findViewById(R.id.ivCategory);
            this.tvCategory = itemView.findViewById(R.id.tvCategory);
            this.tvSaldo = itemView.findViewById(R.id.tvSaldo);
        }


    }
}
