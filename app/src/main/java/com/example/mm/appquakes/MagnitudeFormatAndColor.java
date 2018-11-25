package com.example.mm.appquakes;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.text.DecimalFormat;

public final class MagnitudeFormatAndColor {

    public static int getMagnitudeColor(Context context ,double magnitude) {
        int magnitudeColorResourceId;
        // نستخدم
        //Math.floor
        //  إيجاد أقرب عدد صحيح أقل من القيمة العشرية
        // القادمه البراميتر
        //Math.floor(magnitude)
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        //استدعاء
        // ContextCompat.getColor()
        // لتحويل color resource ID
        // الي عدد صحيح
        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

    public static String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}
