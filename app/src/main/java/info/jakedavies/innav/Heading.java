package info.jakedavies.innav;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Heading implements SensorEventListener {

    private SensorManager mSensorManager;

    private long mLastUpdate;
    //update ever 300 seconds
    private int mUpdateFrequency = 300;
    private HeadingChangedListener mCallback;

    private float[] mGravity;
    private float[] mMagnetic;

    public interface HeadingChangedListener {
        void headingChanged(int heading);
    }

    public Heading(Context context, HeadingChangedListener headingChangedListener) {
        mCallback = headingChangedListener;
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void registerListener() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY) {
            mGravity = sensorEvent.values.clone();
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagnetic = sensorEvent.values.clone();
        }
        if (mGravity != null && mMagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mMagnetic);

            if (success) {
                long currentTime = System.currentTimeMillis();

                if ((currentTime - mLastUpdate) > mUpdateFrequency) {
                    mLastUpdate = currentTime;
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    float inRads = orientation[0];
                    int inDegs = (int)(Math.toDegrees(inRads) + 360) % 360;

                    if (null != mCallback) {
                        mCallback.headingChanged(inDegs);
                    }
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}