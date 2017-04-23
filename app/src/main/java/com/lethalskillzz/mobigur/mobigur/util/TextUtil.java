package com.lethalskillzz.mobigur.mobigur.util;

import java.text.DecimalFormat;

/**
 * Created by ibrahimabdulkadir on 23/04/2017.
 */

public class TextUtil {

    public static String formatDouble(double value){

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return String.valueOf(formatter.format(value));
    }

}
