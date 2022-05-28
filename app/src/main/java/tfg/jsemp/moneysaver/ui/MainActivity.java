package tfg.jsemp.moneysaver.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import tfg.jsemp.moneysaver.R;
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
            startActivity(intent);
        });
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