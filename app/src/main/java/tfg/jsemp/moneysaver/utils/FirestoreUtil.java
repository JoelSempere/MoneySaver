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
        );
        return currentUser;
    }


    public static void setUserInCollection(User currentUser){
        if (currentUser != null){
            docReference = db.collection("Users").document(currentUser.getUserId());
            docReference.set(currentUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("", "onSuccess: User profile created in collection "+currentUser.getUserId());
                }
            });
        }
    }


   public static void getCollection(String collection){
       db.collection(collection)
               .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                       if (task.isSuccessful()) {
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Log.d("", document.getId() + " => " + document.getData());
                           }
                       } else {
                           Log.w("", "Error getting documents.", task.getException());
                       }
                   }
               });
    }


    public static void setObjectToCollection(String collection, Object object){
        db.collection(collection)
                .add(object)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("", "Error adding document", e);
                    }
                });
    }
}