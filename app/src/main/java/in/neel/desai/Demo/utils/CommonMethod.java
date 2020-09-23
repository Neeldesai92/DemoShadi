package in.neel.desai.Demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import org.apache.http.conn.ConnectTimeoutException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import in.neel.desai.Demo.R;
import in.neel.desai.Demo.application.Application;


/**
 *
 */
public class CommonMethod {

    static AlertDialog alertDialog;





    /**
     * For alert dialog with only OK button
     *
     * @param context
     * @param title   as Title
     * @param message as message
     * @param posiBtn as button text
     */
    public static void showAlertDailogueWithOK(final Context context, String title,
                                               String message, String posiBtn) {
        if (alertDialog == null) {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            alertDialogBuilder.setTitle(title);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                alertDialogBuilder.setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
            }else{
                alertDialogBuilder.setMessage(Html.fromHtml(message));
            }


          /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                alertDialogBuilder.setMessage(Html.fromHtml("Hello " + "<b>" + "World" + "</b>", Html.FROM_HTML_MODE_LEGACY));
            } else {

                        alertDialogBuilder.setMessage(Html.fromHtml("Hello " + "<b>" + "World" + "</b>"));
            }*/
            alertDialogBuilder.setPositiveButton(posiBtn, null);

            alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    btnPositive.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else if (!alertDialog.isShowing()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
            alertDialogBuilder.setTitle(title);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                alertDialogBuilder.setMessage(Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY));
            }else{
                alertDialogBuilder.setMessage(Html.fromHtml(message));
            }

            alertDialogBuilder.setPositiveButton(posiBtn, null);

            alertDialog = alertDialogBuilder.create();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {

                    Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    btnPositive.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        }
    }

    /**
     * Connection alert dialog
     *
     * @param context
     */
    public static void showConnectionAlert(final Context context) {

        showAlertDailogueWithOK(context, context.getResources().getString(R.string.title_connection_alert),
                context.getResources().getString(R.string.msg_try_with_active_network),
                context.getResources().getString(R.string.action_ok));
    }


    /**
     * Check whether internet is connected or not
     *
     * @param activtiy
     * @return
     */
    public static boolean isNetworkConnected(Context activtiy) {
        ConnectivityManager cm = (ConnectivityManager) activtiy.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } else {
            return false;
        }

    }





    public static Dialog mDialog;
    public static boolean IsShowing = false;
    public static boolean IsShowingResume = false;

    public static void cancelProgressDialog() {
        if (CommonMethod.IsShowingResume) {
            CommonMethod.IsShowing = true;
            IsShowingResume = false;
        } else {
            IsShowing = false;
        }


        if (mDialog != null
                && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    /**
     * For progress dialog
     *
     * @param activity as context
     */

    public static void showProgressDialog(Activity activity) {
        if (activity != null
                && mDialog == null) {
            IsShowing = true;
            mDialog = new Dialog(activity);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setContentView(R.layout.dialog);

            mDialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(activity, android.R.color.transparent));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(mDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            mDialog.getWindow().setAttributes(lp);
            mDialog.show();
        }
    }


    /**
     * Get current time
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.US);
        Date date = new Date();
        System.out.println(dateFormat.format(date)); // 2014/08/06 15:59:48
        return dateFormat.format(date);
    }

    @SuppressLint("NewApi")
    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
            Log.e("Hide keyboard: ", "" + e);
        }
    }
}
