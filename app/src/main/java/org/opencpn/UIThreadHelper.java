package org.opencpn;

import android.app.Activity;

public class UIThreadHelper {

    public static void runOnUiThread(Activity activity, Runnable r) {
        activity.runOnUiThread(r);
    }
}