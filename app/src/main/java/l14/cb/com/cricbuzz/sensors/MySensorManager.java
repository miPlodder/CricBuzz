package l14.cb.com.cricbuzz.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

/**
 * Created by ip510 feih on 10-07-2017.
 */

public class MySensorManager {

    public static final String TAG = "MySensorManager";
    private OnEventListener oel;

    public static interface OnEventListener {
        void setOnEventListener();
    }

    public static void accelSensorActivate(Context context, final OnEventListener oel) {

        final SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        new Thread(new Runnable() {
            @Override
            public void run() {

                SensorEventListener sel = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {

                        if (sensorEvent.values[0] > 9) {
/*

                            new Handler().postDelayed(new Runnable() {
                               public void run() {
                                @Override
*/


                            oel.setOnEventListener();
/*
                                }
                            },5000);*/

                            Log.d(TAG, "onSensorChanged: " + sensorEvent.values[0]);

                        }

                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }
                };

                Sensor accelMeter = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                sm.registerListener(sel, accelMeter, SensorManager.SENSOR_DELAY_NORMAL);

            }
        }).start();

    }


}
