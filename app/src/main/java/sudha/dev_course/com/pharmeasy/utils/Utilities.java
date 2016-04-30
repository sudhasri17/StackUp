package sudha.dev_course.com.pharmeasy.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by sudharshi on 21/4/16.
 */
public class Utilities
{
    public static int pixelsToDp(int pixels, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(pixels / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPixel(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
