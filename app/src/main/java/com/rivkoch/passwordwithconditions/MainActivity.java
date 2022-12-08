package com.rivkoch.passwordwithconditions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rivkoch.passwordwithconditions.conditions.BatteryPercentage;
import com.rivkoch.passwordwithconditions.conditions.sensors.AccelerometerSensor;
import com.rivkoch.passwordwithconditions.conditions.sensors.ProximitySensor;
import com.rivkoch.passwordwithconditions.conditions.sensors.StepsCounter;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private final String PASSWORD = "Reynolds%100%"; // passwordexample

    /*
     * The password should include the current percentage of battery
     * 8 time sensor of proximity
     * steps: 4
     *
     * TODO:
     * Accelerometer: 8 shaking
     *
     * ( x= 29 y= 08 z =23)
     * */
    private ProximitySensor proximity;
    private AccelerometerSensor accelerometer;
    private PasswordValidator passwordValidator;
    private BatteryPercentage batteryPercentage;
    private StepsCounter stepsCounter;

    private Button enter_BTN;
    private EditText password_EDT;

    private SensorManager sensorManager;
    private Boolean isAccelerometerSensorAvailable;
    private Boolean isProximitySensorAvailable;
    private Boolean isCounterStepsAvailable;
    private Boolean isStepsCounterAvailable;
    private Boolean passwordOk = false;

    private TextView x_tv, y_tv, z_tv, proximity_TV, error_TV, steps_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning sensors as singletons
        proximity = ProximitySensor.getInstance();
        accelerometer = AccelerometerSensor.getInstance();
        stepsCounter = StepsCounter.getInstance();
        batteryPercentage = BatteryPercentage.getInstance();

        // Define views
        findViews();
        passwordValidator = PasswordValidator.getInstance();

        // Init the activity variables
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        isProximitySensorAvailable = proximity.setProximitySensor(sensorManager, MainActivity.this);
        isAccelerometerSensorAvailable = accelerometer.setAccelerometerSensor(sensorManager, MainActivity.this);
        isStepsCounterAvailable = stepsCounter.setStepsCounter(sensorManager, this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
            }
        }

        setListeners();
    }

    private void setListeners() {
        enter_BTN.setOnClickListener(v -> {
            passwordOk = ifWifiOnCheckPassword();
            if (!passwordOk)
                error_TV.setText("Wrong password. Can't enter.");
            else
            startActivity(new Intent(MainActivity.this, SuccessActivity.class));

        });
    }

    private Boolean ifWifiOnCheckPassword(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            passwordValidator.checkPassword(password_EDT, stepsCounter, proximity,
                    batteryPercentage.getBatteryPercentage(getApplicationContext()));
        }

        return passwordOk;
    }

    private void findViews() {
        enter_BTN = findViewById(R.id.enter_BTN);
        password_EDT = findViewById(R.id.password_EDT);
        x_tv = findViewById(R.id.x_TV);
        y_tv = findViewById(R.id.y_TV);
        z_tv = findViewById(R.id.z_TV);
        proximity_TV = findViewById(R.id.proximity_TV);
        error_TV = findViewById(R.id.error_TV);
        steps_tv = findViewById(R.id.steps_TV);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            proximity.proximitySensorChanged(event, getWindow(), proximity_TV);
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometer.accelerometerSensorChanged(event, x_tv, y_tv, z_tv);
        }

        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR ){
            stepsCounter.stepsCounterChanged(event, steps_tv);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null){
            stepsCounter.resumeDet(this, sensorManager);
        }
        if (isProximitySensorAvailable) {
            proximity.resume(MainActivity.this, sensorManager);
        }
        if (isAccelerometerSensorAvailable) {
            accelerometer.resume(MainActivity.this, sensorManager);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)!=null){
            sensorManager.unregisterListener(this);
        }
        if (isProximitySensorAvailable) {
            sensorManager.unregisterListener(this);
        }
        if (isAccelerometerSensorAvailable) {
            sensorManager.unregisterListener(this);
        }

    }
}