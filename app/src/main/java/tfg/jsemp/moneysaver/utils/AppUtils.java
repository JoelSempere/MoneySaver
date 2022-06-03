package tfg.jsemp.moneysaver.utils;

import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.ktx.Firebase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import tfg.jsemp.moneysaver.model.Transaction;

public class AppUtils {

    public static void loadImage(View view, String url, ImageView iv) {
        Glide.with(view)
            .load(url)
            .fitCenter()
            .into(iv);
    }

    /**Rellena con el rango de fechas seleccionado**/
    private static void fillDate(Date start, Date end, int cont) {
        start.setMonth(start.getMonth() + (cont - 1));
        start.setDate(1);
        end.setMonth(end.getMonth() + cont);
        end.setDate(0);
    }

    /**Devuelve un mapa con las transacciones ordenadas por categoria**/
    public static Map<String, List<Transaction>> fillTransactions(List<Transaction> transactions) {
        Map<String, List<Transaction>> transactionsByKey = new ArrayMap<>();
        for (Transaction t : transactions) {
            if (!transactionsByKey.containsKey(t.getCategoryId())) {
                transactionsByKey.put(t.getCategoryId(), new ArrayList<>());
            }
            transactionsByKey.get(t.getCategoryId()).add(t);
        }
        return transactionsByKey;
    }

    /**Recupera todas las transacciones de la cuenta y devuelve el rango seleccionado*/
    public static List<Transaction> getCurrentTransactions(List<Transaction> transactions, Date start, Date end, int monthCont) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        fillDate(start, end, monthCont);
        for (Transaction t: transactions) {
            if(t.getDate().toDate().after(start) && t.getDate().toDate().before(end) ) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }
}
