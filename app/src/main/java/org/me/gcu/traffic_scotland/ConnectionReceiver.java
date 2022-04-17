package org.me.gcu.traffic_scotland;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

/**
 * @author Euan Penman S1821916
 */

//The following code can be enabled to allow for a warning message if the user turns on airplane mode

    public class ConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (isAirplaneModeOn(context.getApplicationContext())) {
                Toast.makeText(context, "AirPlane mode is on", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "AirPlane mode is off", Toast.LENGTH_SHORT).show();
            }
        }

        private static boolean isAirplaneModeOn(Context context) {
            return Settings.System.getInt(context.getContentResolver(), Settings.Global.NETWORK_PREFERENCE, 0) != 0;
        }
    }

