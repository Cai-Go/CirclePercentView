package com.wzh.circlepercentview;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by WZH on 2017/12/27.
 */

public class DensityUtils {
    private DensityUtils() {
        throw new UnsupportedOperationException("can not  be instantiated");
    }


    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal,
                context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                spVal,
                context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    public static float px2sp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (pxVal / scale);
    }
}
