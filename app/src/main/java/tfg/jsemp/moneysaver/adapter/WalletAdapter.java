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

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder>{
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
            System.out.println(acc);
            holder.tvWalletName.setText(acc.getName());
            holder.tvQuantity.setText(acc.getTotal() + " €");
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
        }

        public Account getDia(){ //nos devuelve el dia que muestra
            return mAccounts.get(WalletViewHolder.this
                    .getAdapterPosition());
        }

        private void iniciaViews() {
            this.tvWalletName = itemView.findViewById(R.id.tvWalletName);
            this.tvQuantity = itemView.findViewById(R.id.tvQuantity);
        }
    }
}
