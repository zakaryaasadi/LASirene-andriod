package Utilities.Helper;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyRound {
    public static String rounded(float price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(Currency.getInstance(new Locale("ar", "AE")));
        return formatter.format(price);
    }

    public  static String rounded(double price){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setCurrency(Currency.getInstance(new Locale("ar", "AE")));
        return formatter.format(price);
    }
}
