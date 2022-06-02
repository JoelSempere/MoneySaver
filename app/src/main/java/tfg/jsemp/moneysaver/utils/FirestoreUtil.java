package tfg.jsemp.moneysaver.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
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
import tfg.jsemp.moneysaver.model.Category;
import tfg.jsemp.moneysaver.model.Economy;
import tfg.jsemp.moneysaver.model.Transaction;
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
                        FirestoreUtil.initUserInCollection(currentUserAsync.getValue());
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
                            setAccounts(doc,currentUser.getUserId(), "Principal");
                            setAccounts(doc,currentUser.getUserId(), "Secundaria");
                        }
                    }
                    //getAccounts(currentUser.getUserId());
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


    public static MutableLiveData<Economy> getEconomy(String userId) {
        MutableLiveData<Economy> economy = new MutableLiveData<>();
       Query economyId = db.collection("Users/" + userId + "/Economy/")
                    .whereEqualTo("userId",  userId);
            economyId.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(QueryDocumentSnapshot doc : task.getResult()) {
                        economy.setValue(doc.toObject(Economy.class));
                    }
                }
            });
            return economy;
    }


    public static MutableLiveData<List<Account>> getAccounts(String userId) {
        MutableLiveData<List<Account>> accounts = new MutableLiveData<>();
        Query query = db.collectionGroup("Accounts")
                .whereEqualTo("userId", userId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               accounts.setValue(task.getResult().toObjects(Account.class));
            }
        });
        return accounts;
    }


    public static void setNewAccountValue(String id, float val) {
        db.collection("Users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/Economy/")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Log.d("SubBrands Name: ", doc.getDocument().getId());

                        updateAccountValue(doc, id, val);
                    }
                }
            }
        });
    }

    public static void setAccounts(DocumentChange doc, String id, String name) {
        db.collection("Users").document(id)
                .collection("Economy").document(doc.getDocument().getId())
                .collection("Accounts").document()
                .set(new Account(id, name, 0));
    }

    private static void updateAccountValue(DocumentChange doc, String id , float value) {
        db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Economy").document(doc.getDocument().getId())
                .collection("Accounts").document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       float oldTotal = task.getResult().toObject(Account.class).getTotal();
                       db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                               .collection("Economy").document(doc.getDocument().getId())
                               .collection("Accounts").document(id)
                               .update("total", oldTotal + value);

                   }
               });
    }


    public static MutableLiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> categories = new MutableLiveData<>();
        db.collection("Categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                categories.setValue(task.getResult().toObjects(Category.class));
            }
        });
        return categories;
    }


    public static MutableLiveData<List<Transaction>> getTransactionsByCategoryId(String categoryId) {
        MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();
        Query query = db.collectionGroup("Transactions")
                .whereEqualTo("categoryId", categoryId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                transactions.setValue(task.getResult().toObjects(Transaction.class));
            }
        });
        return transactions;
    }

    /***Devuelve la Id de cualquier documento almacenado en firestore***/
    public static MutableLiveData<String> getIdByName(String name, String collection) {
        MutableLiveData<String> objId = new MutableLiveData<>();
        Query query = db.collectionGroup(collection)
                .whereEqualTo("name", name);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc : task.getResult()) {
                    objId.setValue(doc.getId());
                }
            }
        });
        return objId;
    }

    /***Por como esta en la coleccion tiene que ser una busqueda más especifica***/
    public static MutableLiveData<String> getAccountIdByName(String name, String collection) {
        MutableLiveData<String> objId = new MutableLiveData<>();
        Query query = db.collectionGroup(collection)
                .whereEqualTo("name", name)
                .whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot doc : task.getResult()) {
                    objId.setValue(doc.getId());
                }
            }
        });
        return objId;
    }

    public static void setTransactionsIntoCategories(String categoryId, Transaction transaction) {
        db.collection("Categories").document(categoryId)
                .collection("Transactions").document()
                .set(transaction);
    }


    public static MutableLiveData<List<Transaction>> getTransactions() {
        MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();
        db.collectionGroup("Transactions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                transactions.setValue(task.getResult().toObjects(Transaction.class));
            }
        });
        return transactions;
    }
}