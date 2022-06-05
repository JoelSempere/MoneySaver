package tfg.jsemp.moneysaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.model.Category;
import tfg.jsemp.moneysaver.model.CtWrapper;
import tfg.jsemp.moneysaver.model.Transaction;
import tfg.jsemp.moneysaver.ui.MainActivity;
import tfg.jsemp.moneysaver.utils.AppUtils;
import tfg.jsemp.moneysaver.utils.ConstantsUtil;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<CtWrapper> mCategoriesWrapper;
    private final LayoutInflater mInflater;


    public CategoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        if(mCategoriesWrapper != null) {
            CtWrapper categoryWrapper = mCategoriesWrapper.get(position);
            holder.tvCategory.setText(categoryWrapper.getCategory().getName());
            /**NESTED RECYCLER VIEW**/
            if (categoryWrapper.getTransactions() != null) {
                holder.tvSaldo.setText(
                    calculateEarning(mCategoriesWrapper.get(position).getTransactions())
                );
                setNestedRecycler(holder, categoryWrapper);
            }
        }
    }


    /**Establece las propiedades del recycler view hijo**/
    private void setNestedRecycler(CategoryViewHolder holder, CtWrapper categoryWrapper) {
        setLayoutManager(holder.rvTransaction, categoryWrapper.getTransactions().size());
        holder.rvTransaction.setAdapter(new TransactionAdapter(
                categoryWrapper.getTransactions())
        );
        holder.rvTransaction.setRecycledViewPool(viewPool);
    }


    /**Determina el aspecto del layout y lo asigna**/
    private void setLayoutManager(RecyclerView rvTransaction, int transactionsSize) {
        if(transactionsSize >= 10 ) {
            rvTransaction.setLayoutManager(
                new GridLayoutManager(rvTransaction.getContext(), 2)
            );
        }
        else {
            rvTransaction.setLayoutManager(
                new LinearLayoutManager(rvTransaction.getContext(),
                LinearLayoutManager.VERTICAL, false)
            );
        }
    }


    /**Calcula el saldo y le da formato en forma de cadena**/
    private String calculateEarning(List<Transaction> transactions) {
        float value = 0;
        for (Transaction t : transactions) {
            if(t.isInCome()) {
                value += t.getQuantity();
            }
            else {
                value -= t.getQuantity();
            }
        }
        return AppUtils.formatMoney(value, ConstantsUtil.ConstantsSimbols.EURO);
    }


    /**Acceso externo al objeto que almacena categoria + lista de transacciones relacionadas*/
    public List<CtWrapper> getCategoryWrapperList() {
        List<CtWrapper> categoryList = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            categoryList.add(getItem(i));
        }
        return categoryList;
    }


    private CtWrapper getItem(int position) {
        CtWrapper category = new CtWrapper();
        if(mCategoriesWrapper != null) {
            category = mCategoriesWrapper.get(position);
        }
        return category;
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
            this.tvCategory = (TextView) itemView.findViewById(R.id.tvCategoryRV);
            this.tvSaldo = (TextView) itemView.findViewById(R.id.tvSaldoCategory);
        }

    }

}
