package tfg.jsemp.moneysaver.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import tfg.jsemp.moneysaver.R;
import tfg.jsemp.moneysaver.adapter.CategoryAdapter;
import tfg.jsemp.moneysaver.model.Category;
import tfg.jsemp.moneysaver.model.CtWrapper;
import tfg.jsemp.moneysaver.model.Transaction;
import tfg.jsemp.moneysaver.model.User;
import tfg.jsemp.moneysaver.utils.AppUtils;
import tfg.jsemp.moneysaver.utils.ConstantsUtil;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;

public class MainActivity extends AppCompatActivity {
    private int MONTH_CONT = 1;
    private FirebaseAuth firebaseAuth;
    private Intent intent;
    private ImageButton ibProfile;
    private ImageButton ibWallet;
    private ImageButton ibBackDate;
    private ImageButton ibNextDate;
    private FloatingActionButton fabAddTransaction;
    private TextView tvMonth;
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
        setRecyclerView(categoryAdapter, new Date(), new Date());
        onClickPanelButtons(categoryAdapter);
    }


    private void onChangeActivity() {
        ibProfile.setOnClickListener(c -> FirestoreUtil.getUserinfo(firebaseAuth).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER, user);
                    startActivity(intent);
                    finish();
                }
        }));

        ibWallet.setOnClickListener(c -> {
            intent = new Intent(MainActivity.this, WalletActivity.class);
            startActivity(intent);
            finish();
        });

        fabAddTransaction.setOnClickListener( c -> {
            intent = new Intent(MainActivity.this, CreateTransaction.class);
            startActivity(intent);
            finish();
        });
    }


    /**Carga el panel superior**/
    private void loadPanel(CategoryAdapter adapter) {
        loadMoney(adapter);
        loadDate();
    }


    /**Recibe el contador del mes para devolver el mes de las transacciones devueltas*/
    private void loadDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, (MONTH_CONT - 1));
        Date date = cal.getTime();
        DateFormat formatter = new SimpleDateFormat(getString(R.string.format_pattern_1));
        tvMonth.setText(formatter.format(date));
    }


    /**Recupera el valor economico de las transacciones*/
    private void loadMoney(CategoryAdapter adapter) {
        float ingreso = 0;
        float gasto = 0;
        List<CtWrapper> categoriesWrapper = adapter.getCategoryWrapperList();
        for (CtWrapper cw: categoriesWrapper) {
            if (cw.getTransactions() != null) {
                for (Transaction t: cw.getTransactions()) {
                    System.out.println(t);
                    if(t.isInCome()) {
                        ingreso += t.getQuantity();
                    }
                    else {
                        gasto += t.getQuantity();
                    }
                }
            }
        }
        tvQttyGasto.setText(gasto + ConstantsUtil.ConstantsSimbols.EURO);
        tvQttyIngreso.setText(ingreso + ConstantsUtil.ConstantsSimbols.EURO);
        tvQttySaldo.setText( (ingreso - gasto) + ConstantsUtil.ConstantsSimbols.EURO);
    }


    /**Establece los objectos hijo en el Wrapper del padre e inicia el recycler view principal**/
    private void setRecyclerView(CategoryAdapter adapter, Date start, Date end) {
        FirestoreUtil.getTransactions().observe( this, transactions -> {
            List<Transaction> filteredTransactions = AppUtils.getCurrentTransactions(transactions, start, end, MONTH_CONT); //Filtra las transacciones del usuario por rango de fecha
            Map<String, List<Transaction>> transactionsByCategoryId = AppUtils.fillTransactions(filteredTransactions); //Rellena el mapa con las transacciones
            FirestoreUtil.getCategories().observe(MainActivity.this, categories -> {
                List<CtWrapper> ctWrappers = new ArrayList<>();
                for (Category c: categories) {
                    List<Transaction> transactions1 = transactionsByCategoryId.get(c.getCategoryId());
                    ctWrappers.add(new CtWrapper(c, transactions1));
                }
                rvCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                rvCategories.setAdapter(adapter);
                adapter.setCategories(ctWrappers);
                loadPanel(adapter);
            });
        });
    }


    /**Recupera las transacciones de los meses anteriores o siguientes**/
    private void onClickPanelButtons(CategoryAdapter adapter) {
        Date start = new Date();
        Date end = new Date();
        ibNextDate.setOnClickListener( c-> {
            ++ MONTH_CONT ;
            setRecyclerView(adapter,start,end);
        });
        ibBackDate.setOnClickListener( c-> {
            -- MONTH_CONT ;
            setRecyclerView(adapter,start,end);
        });
    }


    private void initViews() {
        ibProfile = findViewById(R.id.btnProfile);
        ibWallet = findViewById(R.id.btnWallet);
        fabAddTransaction = findViewById(R.id.fabAddTransaction);
        tvMonth = findViewById(R.id.tvMonth);
        tvQttyIngreso = findViewById(R.id.tvqttyIngreso);
        tvQttyGasto = findViewById(R.id.tvqttyGasto);
        tvQttySaldo = findViewById(R.id.tvqttySaldo);
        rvCategories = findViewById(R.id.rvCategoriesMain);
        ibBackDate = findViewById(R.id.ibBackDate);
        ibNextDate = findViewById(R.id.ibNextDate);
    }

}