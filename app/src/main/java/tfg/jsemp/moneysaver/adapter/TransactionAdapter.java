package tfg.jsemp.moneysaver.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.model.Transaction;
import tfg.jsemp.moneysaver.utils.ConstantsUtil;

public class TransactionAdapter extends RecyclerView.Adapter<tfg.jsemp.moneysaver.adapter.TransactionAdapter.TransactionViewHolder> {

    private List<Transaction> mTransactions;


    public TransactionAdapter(List<Transaction> transactions) {
        this.mTransactions = transactions;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {
        if(mTransactions != null) {
            final Transaction transaction = mTransactions.get(position);
            setTransactionStyle(transaction, holder);
            holder.tvTransactionQuantity.setText(transaction.getQuantity() + ConstantsUtil.ConstantsSimbols.EURO);
            holder.tvCont.setText(String.valueOf(position + 1));
        }
    }


    /**Establece el estilo de la transaccion según una condición**/
    private void setTransactionStyle(Transaction transaction, TransactionViewHolder holder) {
        if (!transaction.isInCome()) {
            holder.ivIsInCome.setImageResource(R.mipmap.minus);
            holder.itemView.setBackgroundColor( holder.itemView.getContext().getResources().getColor(R.color.transaction_negative));
        }
        else {
            holder.ivIsInCome.setImageResource(R.mipmap.check);
        }
    }


    @Override
    public int getItemCount() {
        if(mTransactions != null) {
            return mTransactions.size();
        }
        else {
            return 0;
        }
    }


    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private TextView tvCont;
        private TextView tvTransactionQuantity;
        private ImageView ivIsInCome;


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            iniciaViews();
        }


        private void iniciaViews() {
            this.tvCont = itemView.findViewById(R.id.tvCont);
            this.tvTransactionQuantity = itemView.findViewById(R.id.tvTransactionQuantity);
            this.ivIsInCome = itemView.findViewById(R.id.ivIsInCome);
        }

    }

}
