package tfg.jsemp.moneysaver.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.adapter.WalletAdapter;
import tfg.jsemp.moneysaver.model.Account;
import tfg.jsemp.moneysaver.model.User;
import tfg.jsemp.moneysaver.utils.ConstantsUtil;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;
import tfg.jsemp.moneysaver.vm.WalletViewModel;

public class WalletActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private RecyclerView rvWallets;
    private WalletViewModel walletViewModel;
    private ImageButton btnProfile;
    private ImageButton btnMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        onChangeActivity();
        final WalletAdapter adapter = new WalletAdapter(this);
        setRecyclerViewOnActivity(adapter);
        onClickItem(adapter);

    }


    private void setRecyclerViewOnActivity(WalletAdapter adapter) {
        rvWallets.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvWallets.setAdapter(adapter);
        walletViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        walletViewModel.getAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                adapter.setAccounts(accounts);
            }
        });
    }



    private void onClickItem(WalletAdapter adapter) {
        adapter.setOnClickListener(new WalletAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                //TODO podría darsele alguna funcionalidad
            }
        });
    }


    private void onChangeActivity() {
        btnProfile.setOnClickListener(c-> {
            FirestoreUtil.getUserinfo(firebaseAuth).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    intent = new Intent(WalletActivity.this, ProfileActivity.class);
                    intent.putExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER, user);
                    startActivity(intent);
                    finish();
                }
            });
        });
        btnMain.setOnClickListener(c-> {
            intent = new Intent(WalletActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void initViews() {
        btnProfile = findViewById(R.id.btnProfile);
        btnMain = findViewById(R.id.btnMain);
        rvWallets = findViewById(R.id.rvWallets);
    }

}