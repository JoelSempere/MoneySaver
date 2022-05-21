package tfg.jsemp.moneysaver.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import tfg.jsemp.moneysaver.model.Account;
import tfg.jsemp.moneysaver.model.Economy;
import tfg.jsemp.moneysaver.model.User;

public class FirestoreUtil {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentReference docReference;
    public FirestoreUtil() {
    }

    //********Pasado como mutable, esperando una respuesta del metodo asincrono*************//
    public static MutableLiveData<User> getUserinfo(@NonNull FirebaseAuth firebaseAuth) {
        MutableLiveData<User> currentUserAsync = new MutableLiveData<>();
        docReference = db.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
        if (docReference != null) {
            docReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if(document.exists()) {
                        currentUserAsync.setValue(new User(
                                firebaseAuth.getCurrentUser().getUid(),
                                firebaseAuth.getCurrentUser().getEmail(),
                                document.getString("name")
                        ));
                    }
                    else {
                        currentUserAsync.setValue(new User(
                                firebaseAuth.getCurrentUser().getUid(),
                                firebaseAuth.getCurrentUser().getEmail()

                        ));
                    }
                }
            });
        }
        System.out.println("@Joel: valor del usuario - 21/05/22 - " + currentUserAsync.getValue());
        return currentUserAsync;
    }


    //*****FirstTimeSignIn*****//
    public static void initUserInCollection(User currentUser){
        if(currentUser != null){
            docReference = db.collection("Users").document(currentUser.getUserId());
            docReference.set(currentUser);
            db.collection("Users").document(currentUser.getUserId())
                    .collection("Economy").document()
                    .set(new Economy(currentUser.getUserId(), 0));
            db.collection("Users/" + currentUser.getUserId() + "/Economy/").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc : value.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            Log.d("SubBrands Name: ", doc.getDocument().getId());
                            db.collection("Users").document(currentUser.getUserId())
                                    .collection("Economy").document(doc.getDocument().getId())
                                    .collection("Accounts").document()
                                    .set(new Account(currentUser.getUserId(),"Principal" , 0));
                        }
                    }
                }
            });

        }
    }


    public static void updateUserName(User currentUser){
        if(currentUser != null){
            db.collection("Users")
                    .document(currentUser.getUserId())
                    .update("name", currentUser.getName());
        }
    }


    public interface FirestoreCallback {
        User onCallback(User currentUser);
    }

}