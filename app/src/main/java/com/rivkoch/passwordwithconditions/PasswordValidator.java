package com.rivkoch.passwordwithconditions;

import android.widget.EditText;

import com.rivkoch.passwordwithconditions.conditions.sensors.ProximitySensor;
import com.rivkoch.passwordwithconditions.conditions.sensors.StepsCounter;

public class PasswordValidator {

    private static volatile PasswordValidator INSTANCE = null;
    private Boolean correctPassword = false;

    private PasswordValidator(){}

    public static PasswordValidator getInstance(){
        if(INSTANCE == null){
            synchronized (ProximitySensor.class){
                if(INSTANCE == null){
                    INSTANCE = new PasswordValidator();
                }
            }
        }
        return INSTANCE;
    }

    public Boolean checkPassword(EditText password_EDT, StepsCounter stepsCounter, ProximitySensor proximity, int battery){
        // Check conditions
        if(password_EDT.getText().toString().isEmpty()
                || proximity.getProximityCounter() != 8 || stepsCounter.getSteps() != 4) {
            correctPassword = false;
        }
        // Check the string
        else correctPassword = validateString(password_EDT, battery);
        return correctPassword;
    }

    private Boolean validateString(EditText password_EDT, int battery) {
        return password_EDT.getText().toString().equals(String.valueOf(battery));
    }
}
