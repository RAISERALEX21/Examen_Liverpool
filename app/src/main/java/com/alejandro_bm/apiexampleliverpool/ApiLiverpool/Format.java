package com.alejandro_bm.apiexampleliverpool.ApiLiverpool;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by RaiserAlex on 29/03/17 in hour 17.
 */

public class Format {

    String TAG = "FormatsLog";
    public String formatNumberMoney(String formatInitial){
        String formatFinal;

        Double formatInitialDouble = Double.parseDouble(formatInitial);

        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        formatFinal = formatter.format(formatInitialDouble);
        Log.d(TAG,"DATA FORMAT: " + formatFinal);
        return String.valueOf(formatFinal);
    }
}
