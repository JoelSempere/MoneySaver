package tfg.jsemp.moneysaver.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import tfg.jsemp.moneysaver.model.Account;
import tfg.jsemp.moneysaver.utils.FirestoreUtil;

public class WalletViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;

    public WalletViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    //***Recuperamos las cuentas del usuario***//
    public MutableLiveData<List<Account>> getAccounts() {
        return FirestoreUtil.getAccounts(firebaseAuth.getCurrentUser().getUid());
    }
}
