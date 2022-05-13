package tfg.jsemp.moneysaver.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import tfg.jsemp.moneysaver.model.User;

public class FirestoreUtil {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentReference docReference;


    public FirestoreUtil() {
    }


    public static User getUserinfo(FirebaseAuth firebaseAuth){
        User currentUser = new User(
                firebaseAuth.getCurrentUser().getUid(),
                firebaseAuth.getCurrentUser().getEmail()
                // TODO getUserField(firebaseAuth.getCurrentUser().getUid(),"name")
        );
        return currentUser;
    }

    /*private static String getUserField(String uid, String field) {
        return db.collection("Users")
                .document(uid)
                .get();
    }TODO*/


    public static void setUserInCollection(User currentUser){
        if(currentUser != null){
            docReference = db.collection("Users").document(currentUser.getUserId());
            docReference.set(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("", "onSuccess: User profile created in collection "+currentUser.getUserId());
                }
            });
        }
    }


    public static void updateUser(User currentUser){
        if(currentUser != null){
            db.collection("Users")
                    .document(currentUser.getUserId())
                    .update("name", currentUser.getName());
        }
    }

}