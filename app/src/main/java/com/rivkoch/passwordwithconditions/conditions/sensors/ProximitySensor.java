package com.rivkoch.passwordwithconditions.conditions.sensors;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.rivkoch.passwordwithconditions.MainActivity;

public class ProximitySensor {
    private static volatile ProximitySensor INSTANCE = null;
    private Sensor proximitySensor;
    private Boolean isProximitySensorAvailable;
    private int proximityCounter = 0;
    private Vibrator vibrator;

    private ProximitySensor(){}

    public static ProximitySensor getInstance(){
        if(INSTANCE == null){
            synchronized (ProximitySensor.class){
                if(INSTANCE == null){
                    INSTANCE = new ProximitySensor();
                }
            }
        }
        return INSTANCE;
    }

    public int getProximityCounter(){
        return proximityCounter;
    }

    public boolean setProximitySensor(SensorManager sensorManager, MainActivity mainActivity) {

        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            isProximitySensorAvailable = true;
        } else {
            Toast.makeText(mainActivity, "Proximity sensor is not available", Toast.LENGTH_SHORT).show();
//            tv.setText("Proximity sensor is not available");
            isProximitySensorAvailable = false;
        }
        return isProximitySensorAvailable;
    }

    public void proximitySensorChanged(SensorEvent event, Window window, TextView tv){
        if(event.values[0] < proximitySensor.getMaximumRange()){
            proximityCounter++;
            window.getDecorView().setBackgroundColor(Color.GREEN);
            tv.setText(proximityCounter + "");
        }else{
            window.getDecorView().setBackgroundColor(Color.WHITE);
        }
    }

    public void resume(MainActivity mainActivity, SensorManager sensorManager) {
        proximityCounter = 0;
        sensorManager.registerListener(mainActivity, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
