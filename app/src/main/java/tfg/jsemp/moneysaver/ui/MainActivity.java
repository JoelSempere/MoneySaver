package tfg.jsemp.moneysaver.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.adapter.CategoryAdapter;
import tfg.jsemp.moneysaver.model.Category;
import tfg.jsemp.moneysaver.model.CtWrapper;
import tfg.jsemp.moneysaver.model.Transaction;
import tfg.jsemp.moneysaver.model.User;
import tfg.jsemp.moneysaver.utils.ConstantsUtil;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private ImageButton btnProfile;
    private ImageButton btnWallet;
    private FloatingActionButton fabAddTransaction;
    private TextView tvMonth;
    private TextView tvIngreso;
    private TextView tvGasto;
    private TextView tvSaldoDisponible;
    private TextView tvQttyIngreso;
    private TextView tvQttyGasto;
    private TextView tvQttySaldo;
    private RecyclerView rvCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        onChangeActivity();
        final CategoryAdapter categoryAdapter = new CategoryAdapter(this);
        setRecyclerView(categoryAdapter);
    }


    private void onChangeActivity() {
        btnProfile.setOnClickListener( c -> {
            FirestoreUtil.getUserinfo(firebaseAuth).observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER, user);
                        startActivity(intent);
                        finish();
                    }
            });
        });

        btnWallet.setOnClickListener( c -> {
            intent = new Intent(MainActivity.this, WalletActivity.class);
            startActivity(intent);
            finish();
        });

        fabAddTransaction.setOnClickListener( c -> {
            intent = new Intent(MainActivity.this, CreateTransaction.class);
            startActivityForResult(intent,
                    ConstantsUtil.ConstantsTransaction.OPTION_REQUEST_NEW_TRANSACTION);
        });
    }


    private void setRecyclerView(CategoryAdapter adapter) {
        Map<String, List<Transaction>> transactionsByCategoryId = new ArrayMap<>();

        FirestoreUtil.getTransactions().observe( this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                for (Transaction t : transactions) {
                    if (!transactionsByCategoryId.containsKey(t.getCategoryId())) {
                        transactionsByCategoryId.put(t.getCategoryId(), new ArrayList<>());
                    }
                    transactionsByCategoryId.get(t.getCategoryId()).add(t);
                }
                FirestoreUtil.getCategories().observe(MainActivity.this, new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                        System.out.println(transactionsByCategoryId);
                        List<CtWrapper> ctWrappers = new ArrayList<>();
                        for (Category c: categories) {
                            List<Transaction> transactions = transactionsByCategoryId.get(c.getCategoryId());
                            ctWrappers.add(new CtWrapper(c, transactions));
                        }
                        System.out.println(ctWrappers);
                        rvCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rvCategories.setAdapter(adapter);
                        adapter.setCategories(ctWrappers);
                    }
                });
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED){
            Transaction transaction = (Transaction) data.getParcelableExtra(ConstantsUtil.ConstantsTransaction.NEW_TRANSACTION);
            switch (requestCode){
                case ConstantsUtil.ConstantsTransaction.OPTION_REQUEST_NEW_TRANSACTION:
                   //TODO addTransaction(transaction);
                    break;
            }
        }
    }


    private void initViews() {
        btnProfile = findViewById(R.id.btnProfile);
        btnWallet = findViewById(R.id.btnWallet);
        fabAddTransaction = findViewById(R.id.fabAddTransaction);
        tvMonth = findViewById(R.id.tvMonth);
        tvIngreso = findViewById(R.id.tvIngreso);
        tvGasto = findViewById(R.id.tvGasto);
        tvSaldoDisponible = findViewById(R.id.tvSaldoDisponible);
        tvQttyIngreso = findViewById(R.id.tvqttyIngreso);
        tvQttyGasto = findViewById(R.id.tvqttyGasto);
        tvQttySaldo = findViewById(R.id.tvqttySaldo);
        rvCategories = findViewById(R.id.rvCategoriesMain);
    }


}