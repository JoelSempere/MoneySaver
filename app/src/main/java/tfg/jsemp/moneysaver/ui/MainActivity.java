package tfg.jsemp.moneysaver.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        onChangeActivity();
    }


    private void initViews() {
        btnProfile = findViewById(R.id.btnProfile);
        btnWallet = findViewById(R.id.btnWallet);
    }


    private void onChangeActivity() {
        btnProfile.setOnClickListener(c -> {
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
        btnWallet.setOnClickListener(c -> {
            //TODO
            intent = new Intent(MainActivity.this, WalletActivity.class);
            startActivity(intent);
            finish();
        });
    }
}