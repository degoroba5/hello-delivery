package com.example.hellodelivery.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class Formatter {
    public static String formatCurrency(double amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(amount);
    }
}
