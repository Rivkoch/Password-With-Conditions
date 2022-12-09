package com.rivkoch.passwordwithconditions;

import android.widget.EditText;

import com.rivkoch.passwordwithconditions.conditions.sensors.ProximitySensor;
import com.rivkoch.passwordwithconditions.conditions.sensors.StepsCounter;

public class PasswordValidator {

    private final String PASSWORD = "Reynolds"; // passwordexample


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

    public Boolean checkPassword(EditText password_EDT,
                                 StepsCounter stepsCounter,
                                 ProximitySensor proximity,
                                 Double x, int battery){
        // Check conditions
        if(password_EDT.getText().toString().isEmpty()
                || proximity.getProximityCounter() > 4 || stepsCounter.getSteps() > 4 || x < 1) {
            correctPassword = false;
        }
        // Check the string
        else correctPassword = validateString(password_EDT, battery);
        return correctPassword;
    }

    private Boolean validateString(EditText password_EDT, int battery) {

        String[] tokens=password_EDT.getText().toString().split("%");
        if(tokens[0].equals(PASSWORD) && tokens[1].equals(String.valueOf(battery))){
            return true;
        }


        return false;
    }
}
