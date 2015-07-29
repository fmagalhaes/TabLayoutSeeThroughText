package pt.fmagalhaes.tablayoutseethroughtextlib.TextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressWarnings("deprecation")
final public class SeeThroughTextView extends TextView
{
    private Bitmap mMaskBitmap;
    private Canvas mMaskCanvas;
    private Paint mPaint;

    private Drawable mBackground;
    private Bitmap mBackgroundBitmap;
    private Canvas mBackgroundCanvas;
    private boolean mSetBoundsOnSizeAvailable = false;

    public SeeThroughTextView(Context context)
    {
        super(context);
        init();
    }

    public SeeThroughTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeeThroughTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        super.setTextColor(Color.BLACK);
        super.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void setBackground(Drawable background) {
        //noinspection deprecation
        setBackgroundDrawable(background);
    }

    @Override
    @Deprecated
    public void setBackgroundDrawable(Drawable bg)
    {
        mBackground = bg;
        int w = bg.getIntrinsicWidth();
        int h = bg.getIntrinsicHeight();

        // Drawable has no dimensions, retrieve View's dimensions
        if (w == -1 || h == -1)
        {
            w = getWidth();
            h = getHeight();
        }

        // Layout has not run
        if (w == 0 || h == 0)
        {
            mSetBoundsOnSizeAvailable = true;
            return;
        }

        mBackground.setBounds(0, 0, w, h);
        invalidate();
    }

    @Override
    public void setBackgroundColor(int color)
    {
        setBackgroundDrawable(new ColorDrawable(color));
    }

    @Override
    public void setBackgroundResource(int resid) {
        Drawable d = null;
        if (resid != 0) {
            d = getContext().getResources().getDrawable(resid);
        }
        setBackgroundDrawable(d);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mBackgroundBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mBackgroundCanvas = new Canvas(mBackgroundBitmap);
        mMaskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mMaskCanvas = new Canvas(mMaskBitmap);

        if (mSetBoundsOnSizeAvailable)
        {
            mBackground.setBounds(0, 0, w, h);
            mSetBoundsOnSizeAvailable = false;
        }
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void onDraw(Canvas canvas)
    {
        // Draw background
        mBackground.draw(mBackgroundCanvas);

        // Draw mask
        mMaskCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        super.onDraw(mMaskCanvas);

        mBackgroundCanvas.drawBitmap(mMaskBitmap, 0.f, 0.f, mPaint);
        canvas.drawBitmap(mBackgroundBitmap, 0.f, 0.f, null);
    }
}
