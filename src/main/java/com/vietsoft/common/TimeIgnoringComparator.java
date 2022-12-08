package com.vietsoft.common;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class TimeIgnoringComparator implements Comparator<Date> {
    public int compare(Date d1, Date d2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(d1);
        calendar2.setTime(d2);
        int year1 = calendar1.get(Calendar.YEAR);
        int year2 = calendar2.get(Calendar.YEAR);
        int month1 = calendar1.get(Calendar.MONTH);
        int month2 = calendar2.get(Calendar.MONTH);
        int day1 = calendar1.get(Calendar.DATE);
        int day2 = calendar2.get(Calendar.DATE);
        if (year1 != year2)
            return year1 - year2;
        if (month1 != month2)
            return month1 - month2;
        return day1 - day2;
    }
}
