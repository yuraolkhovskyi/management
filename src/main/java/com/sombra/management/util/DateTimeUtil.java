package com.sombra.management.util;

import java.time.LocalDate;

public class DateTimeUtil {

    private DateTimeUtil() {

    }

    public static boolean isLocalDateBetweenDatesIncludingLimits(final LocalDate startDate, final LocalDate endDate) {
        final LocalDate now = LocalDate.now();
        return (now.isAfter(startDate) || now.isEqual(startDate) ) && (now.isBefore(endDate) || now.isEqual(endDate));
    }

}
