package tfg.jsemp.moneysaver.ui;

import androidx.appcompat.app.AppCompatActivity;

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
    private User currentUser;
    private ImageButton btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        initViews();
        onClickButtons();
    }


    private void initViews() {
        btnProfile = findViewById(R.id.btnProfile);
    }


    private void onClickButtons() {
        btnProfile.setOnClickListener(c -> {
            currentUser = FirestoreUtil.getUserinfo(firebaseAuth);
            if(currentUser != null){
                intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(ConstantsUtil.ConstantsLogin.CURRENT_USER, currentUser);
                startActivity(intent);
            }
        });
    }
}