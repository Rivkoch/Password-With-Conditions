package com.rivkoch.passwordwithconditions.conditions;

import static android.content.Context.BATTERY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.widget.TextView;

import com.rivkoch.passwordwithconditions.conditions.sensors.ProximitySensor;

public class BatteryPercentage {
    private static volatile BatteryPercentage INSTANCE = null;
    private TextView battery_tv;

    public static BatteryPercentage getInstance(){
        if(INSTANCE == null){
            synchronized (ProximitySensor.class){
                if(INSTANCE == null){
                    INSTANCE = new BatteryPercentage();
                }
            }
        }
        return INSTANCE;
    }

    public BatteryPercentage() {}

    public static int getBatteryPercentage(Context context) {

        if (Build.VERSION.SDK_INT >= 21) {

            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;

            return (int) (batteryPct * 100);
        }
    }
}
