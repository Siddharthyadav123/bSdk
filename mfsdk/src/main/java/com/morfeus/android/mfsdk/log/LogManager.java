package com.morfeus.android.mfsdk.log;

import android.util.Log;

import com.morfeus.android.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Manager to write to the log.
 */
public final class LogManager {
    private static final LogManager sINSTANCE;
    private static boolean sLogEnabled;

    static {
        sINSTANCE = new LogManager();
        sLogEnabled = BuildConfig.ENABLE_LOG;
    }

    private LogManager() {
    }

    public static LogManager getInstance() {
        return sINSTANCE;
    }


    public static int dString(String tag, String msg) {
        if (sLogEnabled)
            return Log.d(tag, msg);
        else
            return 0;
    }


    public static int dString(String tag, String msg, Throwable tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.d(tag, msg, tr);
        } else
            return 0;
    }

    public static int eString(String tag, String msg) {
        if (sLogEnabled)
            return Log.e(tag, msg);
        else
            return 0;
    }

    public static int eString(String tag, String msg, Throwable tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.e(tag, msg, tr);
        } else
            return 0;
    }

    public static int iString(String tag, String msg) {
        if (sLogEnabled)
            return Log.i(tag, msg);
        else
            return 0;
    }

    public static int iString(String tag, String msg, Throwable tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.i(tag, msg, tr);
        } else
            return 0;
    }

    public static int wString(String tag, String msg) {
        if (sLogEnabled)
            return Log.w(tag, msg);
        else
            return 0;
    }

    public static int wString(String tag, String msg, Throwable tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.w(tag, msg, tr);
        } else
            return 0;
    }

    public static int wString(String tag, Exception tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.w(tag, tr);
        } else
            return 0;
    }

    public static int vString(String tag, String msg) {
        if (sLogEnabled)
            return Log.v(tag, msg);
        else
            return 0;
    }

    public static int vString(String tag, String msg, Throwable tr) {
        if (sLogEnabled) {
            tr.printStackTrace();
            return Log.v(tag, msg, tr);
        } else
            return 0;
    }

    public static int d(Object obj, String msg) {
        return dString(obj.toString(), msg);
    }

    public static int d(String tag, String msg, Exception e) {
        return dString(tag, msg, e);
    }

    public static int e(Object obj, String msg) {
        return eString(obj.toString(), msg);
    }

    public static int e(String tag, String msg, Exception e) {
        return eString(tag, msg, e);
    }

    public static int i(Object obj, String msg) {
        return iString(obj.toString(), msg);
    }

    public static int i(String tag, String msg, Exception e) {
        return iString(tag, msg, e);
    }

    public static int w(Object obj, String msg) {
        return wString(obj.toString(), msg);
    }

    public static int w(String tag, String msg, Exception e) {
        return wString(tag, msg, e);
    }

    public static int w(String tag, Exception e) {
        return wString(tag, e);
    }

    public static int v(Object obj, String msg) {
        return vString(obj.toString(), msg);
    }

    public static int v(String tag, String msg, Exception e) {
        return vString(tag, msg, e);
    }

    public static void exception(Object obj, Exception exception) {
        if (!sLogEnabled)
            return;
        forceException(obj, exception);
    }

    public static void forceException(Object obj, Exception exception) {
        System.err.println(obj.toString());
        System.err.println(getStackTrace(exception));
    }

    private static String getStackTrace(Exception exception) {
        final StringWriter result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        exception.printStackTrace(printWriter);
        return result.toString();
    }


}
