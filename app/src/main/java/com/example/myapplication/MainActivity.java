package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    private TextView mTextSensorLight;
    private static final String TAG = "SensorSurvey";
    private TextView mTextProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextProximity = (TextView) findViewById(R.id.label_proximity);

        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensor_error = getResources().getString(R.string.erroe_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }else{
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (mSensorProximity == null) {
            mTextProximity.setText(sensor_error);
        }else{
            mSensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensor_type = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];

        switch (sensor_type) {
            case Sensor.TYPE_LIGHT:
                Log.d(TAG, "Light" + Float.toString(currentValue));
                mTextSensorLight.setText(getResources().getString(R.string.label_sensor, currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                Log.d(TAG, "Proximity" + Float.toString(currentValue));
                mTextProximity.setText(getResources().getString(R.string.label_proximity, currentValue));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


//        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        StringBuilder sensorText  = new StringBuilder();

//        for (Sensor currentSensor: sensorList){
//            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
//        }
//
//        TextView sensorTextView = (TextView) findViewById(R.id.label_light);
//        sensorTextView.setText(sensorText);

}