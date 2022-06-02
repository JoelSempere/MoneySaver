package tfg.jsemp.moneysaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.model.Account;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder>{

    public interface onItemClickListener {
        void onItemClick(Account account);
    }


    public void setOnClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    private onItemClickListener listener;
    private final LayoutInflater mInflater;
    private List<Account> mAccounts;

    public WalletAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public WalletAdapter.WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_wallet, parent, false);
        return new WalletViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.WalletViewHolder holder, int position) {
        if(mAccounts != null) {
            final Account acc = mAccounts.get(position);
            holder.tvWalletName.setText(acc.getName());
            holder.tvQuantity.setText(String.valueOf(acc.getTotal()) + " €");
        }
    }

    @Override
    public int getItemCount() {
        if(mAccounts != null) {
            return mAccounts.size();
        }
        else {
            return 0;
        }
    }

    public void setAccounts(List<Account> accounts) {
        mAccounts = accounts;
        notifyDataSetChanged();
    }


    public class WalletViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWalletName;
        private TextView tvQuantity;


        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            iniciaViews();
            clickItem(itemView);
        }


        public Account getDia(){ //nos devuelve el dia que muestra
            return mAccounts.get(WalletViewHolder.this
                    .getAdapterPosition());
        }


        private void iniciaViews() {
            this.tvWalletName = itemView.findViewById(R.id.tvWalletName);
            this.tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }


        private void clickItem(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClick(mAccounts.get(WalletViewHolder.this.getAdapterPosition()));
                    }
                }
            });
        }


    }

}
