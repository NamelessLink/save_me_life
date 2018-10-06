package com.lxs.util;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date ExpectDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        int min = calendar.get(calendar.MINUTE);
        int sec = calendar.get(calendar.SECOND);
        calendar.set(year, month, day, hour, min + 45, sec);
        Date new_date = calendar.getTime();
        return new_date;
    }
     public static void main(String [] args) throws ParseException {
        Date date = new Date();
        date = ExpectDate(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = sdf.parse(sdf.format(date));
        System.out.println(time);
     }
}
