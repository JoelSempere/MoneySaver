package tfg.jsemp.moneysaver.utils;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.ktx.Firebase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AppUtils {

    public static void loadImage(View view, String url, ImageView iv) {
        Glide.with(view)
            .load(url)
            .fitCenter()
            .into(iv);
    }


   /* public static void fillDateRange(Timestamp currentTime, Date start, Date end) {
        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, month);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
    } */
}
