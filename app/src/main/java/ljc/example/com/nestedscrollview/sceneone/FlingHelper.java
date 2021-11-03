package ljc.example.com.nestedscrollview.sceneone;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.ViewConfiguration;

/**
 * Created by guanqinghua on 2021/9/22
 * Describe:
 */
public class FlingHelper {

    private Context context;
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
    // Fling friction
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    // A context-specific coefficient adjusted to physical values.
    private float mPhysicalCoeff;
    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));


    public FlingHelper(Context context) {
        this.context = context;
        final float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
        mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f; // look and feel tuning
    }



    public double getSplineDeceleration(int velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    public double getSplineFlingDistance(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return mFlingFriction * mPhysicalCoeff * Math.exp(DECELERATION_RATE / decelMinusOne * l);
    }

    /* Returns the duration, expressed in milliseconds */
    private int getSplineFlingDuration(int velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return (int) (1000.0 * Math.exp(l / decelMinusOne));
    }
}
