package com.rivkoch.passwordwithconditions.conditions.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rivkoch.passwordwithconditions.MainActivity;

public class AccelerometerSensor {
    private static volatile AccelerometerSensor INSTANCE = null;

    private Sensor accelerometerSensor;
    private Boolean isAccelerometerSensorAvailable;

    private AccelerometerSensor() {
    }

    public static AccelerometerSensor getInstance(){
        if(INSTANCE == null){
            synchronized (AccelerometerSensor.class){
                if(INSTANCE == null){
                    INSTANCE = new AccelerometerSensor();
                }
            }
        }
        return INSTANCE;
    }

    public boolean setAccelerometerSensor(SensorManager sensorManager, MainActivity mainActivity) {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else {
            Toast.makeText(mainActivity, "Accelerometer sensor is not available", Toast.LENGTH_SHORT).show();
            isAccelerometerSensorAvailable = false;
        }
        return isAccelerometerSensorAvailable;
    }

    public void accelerometerSensorChanged(SensorEvent event, TextView x_tv, TextView y_tv, TextView z_tv) {
        x_tv.setText(event.values[0] + "");
        y_tv.setText(event.values[1] + "");
        z_tv.setText(event.values[2] + "");
    }

    public Double getTheX(TextView x_tv){
        return Double.parseDouble(x_tv.getText().toString());
    }

    public void resume(MainActivity mainActivity, SensorManager sensorManager) {
        sensorManager.registerListener(mainActivity, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }
}


