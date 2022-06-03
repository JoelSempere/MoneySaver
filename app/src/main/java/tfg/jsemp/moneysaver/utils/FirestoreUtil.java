package tfg.jsemp.moneysaver.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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

import java.util.ArrayList;
import java.util.Date;
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


    /**Recupera la información del usuario o la genera si es la primera vez que accede a la aplicación**/
    public static MutableLiveData<User> getUserinfo(@NonNull FirebaseAuth firebaseAuth) {
        MutableLiveData<User> currentUserAsync = new MutableLiveData<>();
        docReference = db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(firebaseAuth.getCurrentUser().getUid());
        if (docReference != null) {
            docReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot document) {
                    if(document.exists()) {
                        currentUserAsync.setValue(new User(
                                firebaseAuth.getCurrentUser().getUid(),
                                firebaseAuth.getCurrentUser().getEmail(),
                                document.getString(ConstantsUtil.ConstantsFirestore.NAME)
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
        return currentUserAsync;
    }


    /*****Inicio de sesion por primera vez: Genera y recupera los documentos necesarios para la aplicación*****/
    public static void initUserInCollection(User currentUser){
        if(currentUser != null){
            docReference = db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(currentUser.getUserId());
            docReference.set(currentUser);
            db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(currentUser.getUserId())
                    .collection(ConstantsUtil.ConstantsFirestore.ECONOMY).document()
                    .set(new Economy(currentUser.getUserId(), ConstantsUtil.ConstantsFirestore.MIN_VALUE));
            db.collection(ConstantsUtil.ConstantsFirestore.USERS+ "/" + currentUser.getUserId()
                    + "/" + ConstantsUtil.ConstantsFirestore.ECONOMY +"/").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    for(DocumentChange doc : value.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            setAccounts(doc,currentUser.getUserId(), ConstantsUtil.ConstantsFirestore.WALLET_NAME_1);
                            setAccounts(doc,currentUser.getUserId(), ConstantsUtil.ConstantsFirestore.WALLET_NAME_2);
                        }
                    }
                }
            });
        }
    }


    public static void updateUserName(User currentUser){
        if(currentUser != null){
            db.collection(ConstantsUtil.ConstantsFirestore.USERS)
                    .document(currentUser.getUserId())
                    .update(ConstantsUtil.ConstantsFirestore.NAME, currentUser.getName());
        }
    }


    public static MutableLiveData<List<Account>> getAccounts(String userId) {
        MutableLiveData<List<Account>> accounts = new MutableLiveData<>();
        Query query = db.collectionGroup(ConstantsUtil.ConstantsFirestore.ACCOUNTS)
                .whereEqualTo(ConstantsUtil.ConstantsFirestore.USER_ID, userId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               accounts.setValue(task.getResult().toObjects(Account.class));
            }
        });
        return accounts;
    }


    public static void setAccounts(DocumentChange doc, String id, String name) {
        db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(id)
                .collection(ConstantsUtil.ConstantsFirestore.ECONOMY).document(doc.getDocument().getId())
                .collection(ConstantsUtil.ConstantsFirestore.ACCOUNTS).document()
                .set(new Account(id, name, ConstantsUtil.ConstantsFirestore.MIN_VALUE));
    }


    /**Recupera la cuenta y llama a otro metodo que establece el nuevo valor**/
    public static void setNewAccountValue(String id, float val) {
        db.collection(ConstantsUtil.ConstantsFirestore.USERS + "/" + FirebaseAuth.getInstance().getCurrentUser().getUid()
                + "/" + ConstantsUtil.ConstantsFirestore.ECONOMY + "/")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange doc : value.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        updateAccountValue(doc, id, val);
                    }
                }
            }
        });
    }

    /**Metodo interno, actualiza el valor en la cuenta: Tiene en cuenta el anterior valor más el llegado**/
    private static void updateAccountValue(DocumentChange doc, String id , float value) {
        db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(ConstantsUtil.ConstantsFirestore.ECONOMY).document(doc.getDocument().getId())
                .collection(ConstantsUtil.ConstantsFirestore.ACCOUNTS).document(id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       float oldTotal = task.getResult().toObject(Account.class).getTotal();
                       db.collection(ConstantsUtil.ConstantsFirestore.USERS).document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                               .collection(ConstantsUtil.ConstantsFirestore.ECONOMY).document(doc.getDocument().getId())
                               .collection(ConstantsUtil.ConstantsFirestore.ACCOUNTS).document(id)
                               .update(ConstantsUtil.ConstantsFirestore.TOTAL, oldTotal + value);

                   }
               });
    }


    /**Obtiene el Id de la cuenta/cartera **/
    public static MutableLiveData<String> getAccountIdByName(String name, String collection) {
        MutableLiveData<String> objId = new MutableLiveData<>();
        Query query = db.collectionGroup(collection)
                .whereEqualTo(ConstantsUtil.ConstantsFirestore.NAME, name)
                .whereEqualTo(ConstantsUtil.ConstantsFirestore.USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
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


    public static MutableLiveData<List<Category>> getCategories() {
        MutableLiveData<List<Category>> categories = new MutableLiveData<>();
        db.collection(ConstantsUtil.ConstantsFirestore.CATEGORIES).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                categories.setValue(task.getResult().toObjects(Category.class));
            }
        });
        return categories;
    }


    /***Devuelve la Id de cualquier documento almacenado en firestore***/
    public static MutableLiveData<String> getIdByName(String name, String collection) {
        MutableLiveData<String> objId = new MutableLiveData<>();
        Query query = db.collectionGroup(collection)
                .whereEqualTo(ConstantsUtil.ConstantsFirestore.NAME, name);
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
        db.collection(ConstantsUtil.ConstantsFirestore.CATEGORIES).document(categoryId)
                .collection(ConstantsUtil.ConstantsFirestore.TRANSACTIONS).document()
                .set(transaction);
    }


    /**Devuelve todas las transacciónes del usuario**/
    public static MutableLiveData<List<Transaction>> getTransactions() {
        MutableLiveData<List<Transaction>> transactions = new MutableLiveData<>();
        Query query = db.collectionGroup(ConstantsUtil.ConstantsFirestore.TRANSACTIONS)
                .whereEqualTo(ConstantsUtil.ConstantsFirestore.USER_ID, FirebaseAuth.getInstance().getCurrentUser().getUid());
                //.whereGreaterThanOrEqualTo("date", start)
                //.whereLessThanOrEqualTo("date", end);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                transactions.setValue(task.getResult().toObjects(Transaction.class));
            }
        });
        return transactions;
    }
}