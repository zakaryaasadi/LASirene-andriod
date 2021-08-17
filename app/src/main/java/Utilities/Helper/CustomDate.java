package Utilities.Helper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDate {
    public static String format(Date date){

        long different = new Date().getTime() - date.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long day = different / daysInMilli;
        different = different % daysInMilli;

        long hour = different / hoursInMilli;
        different = different % hoursInMilli;

        long minutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long seconds = different / secondsInMilli;

        if(day > 7){
            SimpleDateFormat dates = new SimpleDateFormat("EEE, dd MMMM yyyy . hh:mm");
            return dates.format(date);
        }

        if(day > 1){
            SimpleDateFormat dates = new SimpleDateFormat("EEEE");
            return dates.format(date) + " ago";
        }


        if(day == 1)
            return "yesterday ago";

        if(hour >= 1)
            return hour + " hour ago";

        if(minutes >= 1)
            return minutes + " mins. ago";

        return "Just now";
    }

    public static String format(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date dateTime = dateFormat.parse(date);
            return format(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String event(Date date){
        SimpleDateFormat dates = new SimpleDateFormat("dddd, dd MMMM yyyy");
        return dates.format(date);
    }

    public static boolean isMoreDay(long date){
        long different = new Date().getTime() - date;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long day = different / daysInMilli;
        return day > 1;
    }

    public static boolean isMoreHour(long date){
        long different = new Date().getTime() - date;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long day = different / daysInMilli;
        if(day > 1)
            return  true;

        different = different % daysInMilli;

        long hour = different / hoursInMilli;
        return  hour > 1;
    }


    public static String time(int min){


        int hour = min / 60;

        if(min > 60) {
            min %= 60;
            return hour + " H " + min + " M";
        }

        return min + " M";
    }

}


