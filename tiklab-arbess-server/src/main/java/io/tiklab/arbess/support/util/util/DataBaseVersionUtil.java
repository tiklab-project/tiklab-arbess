package io.tiklab.arbess.support.util.util;

public class DataBaseVersionUtil {

    private static final String KEY = "ARBESS_IS_PGSQL";

    public static boolean isPgsql() {
        return Boolean.parseBoolean(System.getProperty(KEY, "true")); // 默认 PostgreSQL
    }

    public static boolean isMysql() {
        return !isPgsql();
    }
}
