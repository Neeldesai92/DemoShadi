/**
Root Verifier - Android App
Copyright (C) 2014 Madhav Kanbur

This file is a part of Root Verifier.

Root Verifier is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

Root Verifier is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Root Verifier. If not, see <http://www.gnu.org/licenses/>.*/

package in.neel.desai.Demo.root_check;

import android.app.Activity;

import java.util.Scanner;

import in.neel.desai.Demo.Activity.SplashActivity;
import in.neel.desai.Demo.R;


public class CheckBusyBox implements Runnable {

	public Thread t;
	private static Activity activity;

	public CheckBusyBox(Activity activity) {
		CheckBusyBox.activity =activity;
		t = new Thread(this, "CheckBusyBox");
		t.start();
	}

	@Override
	public void run() {
		busybox();
	}

	private static void busybox() {
		char n[] = null;
		String line = null;

		try {

			Process p = Runtime.getRuntime().exec("busybox");
			Scanner in = new Scanner(p.getInputStream());

			busybox: while (in.hasNextLine()) {
				line = in.nextLine();
				n = line.toCharArray();

				for (char c : n) {

					if (Character.isDigit(c)) {
						break busybox;
					}
				}
			}

			in.close();
			((SplashActivity) activity).setText(new StringBuilder(activity.getString(R.string.yes_busybox)).append(" ").append(line));

		} catch (Exception e) {
			((SplashActivity) activity).setText(activity.getString(R.string.no_busybox));
		}
	}
}