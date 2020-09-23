package in.neel.desai.Demo.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.multidex.MultiDex;


import java.io.PrintWriter;
import java.io.StringWriter;

import in.neel.desai.Demo.utils.CommonMethod;
import in.neel.desai.Demo.utils.MyLifecycleHandler;


public class Application extends android.app.Application {

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        MultiDex.install(this);



        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });
        registerActivityLifecycleCallbacks(new MyLifecycleHandler());

    }

    public static Context getAppContext() {
        return Application.context;
    }


    /**
     * @param thread
     * @param exception Exception Message and store in Log File
     */

    public void handleUncaughtException(Thread thread, Throwable exception) {


        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
        }

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        appendLogs("APP_EXCEPTION");
        appendLogs("Date " + new CommonMethod().getCurrentDateTime());
        appendLogs("Exception : " + stackTrace.toString());

        appendLogs("\n------- DEVICE INFORMATION------\n");
        appendLogs("Brand: ");
        appendLogs(Build.BRAND);
        String LINE_SEPARATOR = "\n";
        appendLogs(LINE_SEPARATOR);
        appendLogs("Model: ");
        appendLogs(Build.MODEL);
        appendLogs(LINE_SEPARATOR);
        appendLogs("\n---FIRMWARE---\n");
        appendLogs("SDK: ");
        appendLogs(Build.VERSION.SDK);
        appendLogs(LINE_SEPARATOR);
        appendLogs("Release: ");
        appendLogs(Build.VERSION.RELEASE);
        appendLogs(LINE_SEPARATOR);
        appendLogs("\n--- VERSION---\n");
        appendLogs("Version Code: ");
        Object versionCode = info == null ? "(null)" : info.versionCode;
        appendLogs(String.valueOf(versionCode));
        appendLogs(LINE_SEPARATOR);
        appendLogs("Version Name: ");
        appendLogs(info == null ? "(null)" : info.versionName);
        appendLogs("--------------------------------------------");
    }

    private void appendLogs(String logtext) {
        try {

            Log.i("expation", logtext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
