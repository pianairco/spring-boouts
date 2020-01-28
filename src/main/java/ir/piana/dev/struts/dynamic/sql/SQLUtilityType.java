package ir.piana.dev.struts.dynamic.sql;

import ir.piana.dev.struts.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * Created by mj.rahmati on 12/29/2019.
 */
public enum SQLUtilityType {
    TODAY_JALALI("date.today-jalali", SQLUtilityType::getTodayJalali),
    DATE_NOW("date.now", SQLUtilityType::getDateNow),
    TIME_NOW("time.now", SQLUtilityType::getTimeNow),
    CUSTOMER_ID("customer.id", SQLUtilityType::getCustomerId);

    private String name;
    private Function<HttpServletRequest, String> funtion;

    SQLUtilityType(String name, Function<HttpServletRequest, String> funtion) {
        this.name = name;
        this.funtion = funtion;
    }

    public static String execute(String sqlUtilityTypeName, HttpServletRequest request) {
        String name = sqlUtilityTypeName;
        String type = "s";
        String strings[];
        if((strings = sqlUtilityTypeName.split(":")).length > 1) {
            name = strings[0];
            type = strings[1];
        }
        for (SQLUtilityType sqlUtilityType : SQLUtilityType.values()) {
            if (sqlUtilityType.name.equals(name)) {
                return type.equals("s") ? "'" + sqlUtilityType.funtion.apply(request) + "'" : sqlUtilityType.funtion.apply(request);
            }
        }
        return "null";
    }

    private static String getTodayJalali(HttpServletRequest request) {
        return DateUtils.getTodayJalali();
    }

    private static String getDateNow(HttpServletRequest request) {
        return DateUtils.getTodayJalali();
    }

    static final String TIME_NOW_PATTERN = "HH:mm";
    static final SimpleDateFormat TIME_NOW_FORMATTER =
            new SimpleDateFormat(TIME_NOW_PATTERN);

    private static String getTimeNow(HttpServletRequest request) {
        return TIME_NOW_FORMATTER.format(new Date());
    }

    private static String getCustomerId(HttpServletRequest request) {
        return "0";
    }
}
