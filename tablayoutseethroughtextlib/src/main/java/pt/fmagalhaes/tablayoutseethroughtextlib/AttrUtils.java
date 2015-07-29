package pt.fmagalhaes.tablayoutseethroughtextlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

@SuppressWarnings("unused")
public class AttrUtils {
    public static int fetchPrimaryColor(Context context) {
        return fetchAttrColor(R.attr.colorPrimary, context);
    }

    public static int fetchPrimaryDarkColor(Context context) {
        return fetchAttrColor(R.attr.colorPrimaryDark, context);
    }

    public static int fetchAccentColor(Context context) {
        return fetchAttrColor(R.attr.colorAccent, context);
    }

    @SuppressWarnings("WeakerAccess")
    public static int fetchAttrColor(int resId, Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{resId});
        int color = a.getColor(0, 0);

        a.recycle();

        return color;
    }
}
