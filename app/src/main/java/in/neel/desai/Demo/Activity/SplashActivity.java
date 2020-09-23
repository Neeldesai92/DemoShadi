package in.neel.desai.Demo.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import in.neel.desai.Demo.R;
import in.neel.desai.Demo.root_check.CheckBusyBox;
import in.neel.desai.Demo.root_check.CheckRoot;
import in.neel.desai.Demo.root_check.CheckSuApp;
import in.neel.desai.Demo.utils.CommonMethod;


public class SplashActivity extends AppCompatActivity {

    public ProgressDialog dialog;
    private long _splashTime = 2000;


    /**
     * create View
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(ContextCompat.getColor(SplashActivity.this,
                    android.R.color.transparent));
        }
        setContentView(R.layout.activity_splash);

    }


    /**
     * Resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        CheckRootStatus();
    }

    /**
     * Destroy
     */
    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    boolean IsDeviceRooted = true;

    /**
     * Check Root status
     */
    public void CheckRootStatus() {

        dialog = ProgressDialog.show(this, getString(R.string.verifying),
                getString(R.string.wait), false);
        dialog.setCanceledOnTouchOutside(false);

        new CheckRoot(SplashActivity.this, new CheckBusyBox(SplashActivity.this).t, new CheckSuApp(SplashActivity.this).t);
    }

    public void setText(final CharSequence s) {
        try {
            final String strResult = s.toString();

            if (strResult.equalsIgnoreCase(getString(R.string.not_rooted))) {
                IsDeviceRooted = false;
            }
            if (strResult.equalsIgnoreCase(getString(R.string.check_complete))) {
                SplashActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (dialog != null) {
                            dialog.dismiss();
                        }
                        if (!IsDeviceRooted) {
                            callNextScreen();
                        } else {
                            rootedDevice();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Root dialog
     */
    private void rootedDevice() {
        CommonMethod.showAlertDailogueWithOK(SplashActivity.this,
                getString(R.string.alert), getString(R.string.Root_Message), getString(R.string.action_ok));
    }

    public static String[] permissionList() {
        return new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION};
    }


    /**
     * call next activity thread
     */
    private void callNextScreen() {

        new Thread() {

            public void run() {
                try {
                    synchronized (this) {
                        wait(_splashTime);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    goNextScreen();
                }
            }
        }.start();

    }

    private void goNextScreen() {
        // Provide one time selection.
        Intent obj;

        obj = new Intent(SplashActivity.this, HomeActivity.class);

        startActivity(obj);
        finish();
    }

}