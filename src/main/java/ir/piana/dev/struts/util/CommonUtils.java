package ir.piana.dev.struts.util;

import javax.servlet.http.HttpSession;

public class CommonUtils {
    public static boolean hasPermission(HttpSession session, String permission) {
        return true;
    }
}
