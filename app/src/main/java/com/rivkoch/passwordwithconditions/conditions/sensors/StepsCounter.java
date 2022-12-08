package com.rivkoch.passwordwithconditions.conditions.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rivkoch.passwordwithconditions.MainActivity;

public class StepsCounter {
    private static volatile StepsCounter INSTANCE = null;

    private Sensor stepsDetector;
    private Boolean isStepsDetectorAvailable;
    int theCounter = 0;

    private StepsCounter() {}

    public static StepsCounter getInstance(){
        if(INSTANCE == null){
            synchronized (AccelerometerSensor.class){
                if(INSTANCE == null){
                    INSTANCE = new StepsCounter();
                }
            }
        }
        return INSTANCE;
    }

    public int getSteps() {
        return theCounter;
    }

    public Boolean setStepsCounter(SensorManager sensorManager, MainActivity mainActivity) {

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            stepsDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isStepsDetectorAvailable = true;
        }
        else{
            Toast.makeText(mainActivity, "Steps detector is not available", Toast.LENGTH_SHORT).show();
            isStepsDetectorAvailable = false;
        }


        return isStepsDetectorAvailable;
    }

    public void stepsCounterChanged(SensorEvent event, TextView steps_tv) {
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            theCounter = (int) (theCounter+event.values[0]);
            steps_tv.setText("Steps: " + theCounter);

        }
    }

    public void resumeDet(MainActivity mainActivity, SensorManager sensorManager) {
        theCounter=0;
        sensorManager.registerListener(mainActivity, stepsDetector, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
