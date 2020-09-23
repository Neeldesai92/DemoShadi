/**
 * Root Verifier - Android App
 * Copyright (C) 2014 Madhav Kanbur
 * <p>
 * This file is a part of Root Verifier.
 * <p>
 * Root Verifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * Root Verifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Root Verifier. If not, see <http://www.gnu.org/licenses/>.
 */

package in.neel.desai.Demo.root_check;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import in.neel.desai.Demo.Activity.SplashActivity;
import in.neel.desai.Demo.R;


public class CheckSuApp implements Runnable {

    public Thread t;
    private static Activity activity;

    public CheckSuApp(Activity activity) {
        this.activity=activity;
        t = new Thread(this, "CheckSuApp");
        t.start();
    }

    @Override
    public void run() {
        su_app();
    }

    private static void su_app() {

        final String[] packages = {"eu.chainfire.supersu",
                "eu.chainfire.supersu.pro", "com.koushikdutta.superuser",
                "com.noshufou.android.su", "com.dianxinos.superuser", "com.kingouser.com",
                "com.mueskor.superuser.su", "org.masteraxe.superuser", "com.yellowes.su",
                "com.kingroot.kinguser"};
        PackageManager pm = ((SplashActivity)activity).getPackageManager();
        int i, l = packages.length;
        String superuser = null;

        for (i = 0; i < l; i++) {
            try {
                ApplicationInfo info = pm.getApplicationInfo(packages[i], 0);
                PackageInfo info2 = pm.getPackageInfo(packages[i], 0);
                superuser = pm.getApplicationLabel(info).toString() + " "
                        + info2.versionName;
                break;
            } catch (PackageManager.NameNotFoundException e) {
                continue;
            }
        }

        if (superuser != null) {
            ((SplashActivity) activity).setText(activity.getString(R.string.su_app) + " " + superuser);
        } else {
            su_alternative();
        }
    }

    private static void su_alternative() {
        String line;
        try {
            Process p = Runtime.getRuntime().exec("su -v");// Superuser version
            InputStreamReader t = new InputStreamReader(p.getInputStream());
            BufferedReader in = new BufferedReader(t);
            line = in.readLine();

            char[] chars = line.toCharArray();
            boolean flag = false;// Check if su -v returns the package name
            // instead of just the version number
            for (char c : chars) {
                if (Character.isLetter(c)) {
                    flag = true;
                }
            }
            if (!flag) {
                line = activity.getString(R.string.app_unknown);
                ((SplashActivity) activity).setText(line);
            } else {
                ((SplashActivity) activity).setText(activity.getString(R.string.su_app) + " " + line);
            }
        } catch (Exception e) {
            line = activity.getString(R.string.app_unknown);
            ((SplashActivity) activity).setText(line);
        }
    }
}