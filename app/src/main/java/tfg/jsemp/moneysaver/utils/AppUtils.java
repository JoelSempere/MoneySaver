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

    public static void fillDate(Date start, Date end) {
        start.setMonth(start.getMonth());
        start.setDate(1);
        end.setMonth(end.getMonth() + 1);
        end.setDate(0);
    }
}
