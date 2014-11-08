package ru.shelfcatcher.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import ru.shelfcatcher.app.R;

/**
 * Created by gadfil on 12.09.2014.
 */
public class MyViewBorder extends View implements View.OnTouchListener {

    private static final int WEIGHT_BORDER = 2;
    private final Paint paint;
    private final int maskColor;
    //    private final int frameColor;
    private final int mBorderColor;
    private Rect frame;
    private Point screenResolution;

    private int lastX, lastY;
    private int displayWidth;
    private int displayHeight;
    private static final int MIN_FRAME_WIDTH = 50; // originally 240
    private static final int MIN_FRAME_HEIGHT = 20; // originally 240
    private static final int MAX_FRAME_WIDTH = 800; // originally 480
    private static final int MAX_FRAME_HEIGHT = 600; // originally 360

    public MyViewBorder(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskColor = Color.argb(200, 100, 100, 100);
        mBorderColor = Color.WHITE;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();

        Log.d("log", "width " + displayWidth +
                " height " +
                displayHeight);
        screenResolution = new Point(displayWidth, displayHeight);
        calcFramingRect();

    }

    public MyViewBorder(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyViewBorder);

//        displayWidth = a.getDimensionPixelOffset(R.styleable.)
//        displayHeight = display.getHeight()


        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskColor = Color.argb(200, 100, 100, 100);
//        frameColor = Color.LTGRAY;
//        mBorderColor = Color.WHITE;
        mBorderColor = a.getColor(R.styleable.MyViewBorder_border_color, Color.WHITE);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
//        displayWidth = display.getWidth();
//        displayHeight = display.getHeight();
//        displayWidth = this.getLayoutParams().width;
//        displayHeight = this.getLayoutParams().height;
//        Log.d("log", "width " + displayWidth + " height " + displayHeight);
//        screenResolution = new Point(displayWidth, displayHeight);
//        calcFramingRect();
    }

    public Rect getFramingRect() {
        return frame;
    }

    private void adjustFramingRect(int deltaWidth, int deltaHeight,
                                   Point screenResolution) {

        // Set maximum and minimum sizes
        if ((frame.width() + deltaWidth > screenResolution.x - 4)
                || (frame.width() + deltaWidth < 50)) {
            deltaWidth = 0;
        }
        if ((frame.height() + deltaHeight > screenResolution.y - 4)
                || (frame.height() + deltaHeight < 50)) {
            deltaHeight = 0;
        }

        int newWidth = frame.width() + deltaWidth;
        int newHeight = frame.height() + deltaHeight;
        int leftOffset = (screenResolution.x - newWidth) / 2;
        int topOffset = (screenResolution.y - newHeight) / 2;
        frame = new Rect(leftOffset, topOffset, leftOffset + newWidth,
                topOffset + newHeight);
    }

    @Override
    public void onDraw(Canvas canvas) {
        displayWidth = this.getWidth();
        displayHeight = this.getHeight();
        screenResolution = new Point(displayWidth, displayHeight);
        calcFramingRect();
        if (frame == null) {
            return;
        }
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1,
                paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        // Draw a two pixel solid border inside the framing rect
        paint.setAlpha(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mBorderColor);
        canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2,
                paint);
        canvas.drawRect(frame.left, frame.top + 2, frame.left + 2,
                frame.bottom - 1, paint);
        canvas.drawRect(frame.right - 1, frame.top, frame.right + 1,
                frame.bottom - 1, paint);
        canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1,
                frame.bottom + 1, paint);

        // Draw the framing rect corner UI elements
        paint.setColor(mBorderColor);
        canvas.drawRect(frame.left, 0, frame.left + WEIGHT_BORDER, frame.top, paint);

        canvas.drawRect(0, frame.top, frame.left, frame.top + WEIGHT_BORDER, paint);
        canvas.drawRect(frame.right - WEIGHT_BORDER, frame.top, frame.right, 0, paint);

        canvas.drawRect(frame.right, frame.top, displayWidth, frame.top + WEIGHT_BORDER, paint);

        canvas.drawRect(frame.left, frame.bottom, frame.left + WEIGHT_BORDER, displayHeight, paint);
        canvas.drawRect(frame.left, frame.bottom - WEIGHT_BORDER, 0, frame.bottom, paint);
        canvas.drawRect(frame.right, frame.bottom - WEIGHT_BORDER, displayWidth, frame.bottom, paint);
        canvas.drawRect(frame.right, frame.bottom, frame.right - WEIGHT_BORDER, displayHeight, paint);

    }

    private void calcFramingRect() {
        if (frame == null) {
//            int width = screenResolution.x * 3 / 5;
            int width = 100;
            if (width < MIN_FRAME_WIDTH) {
                width = MIN_FRAME_WIDTH;
            } else if (width > MAX_FRAME_WIDTH) {
                width = MAX_FRAME_WIDTH;
            }
            int height = screenResolution.y * 1 / 5;
            if (height < MIN_FRAME_HEIGHT) {
                height = MIN_FRAME_HEIGHT;
            } else if (height > MAX_FRAME_HEIGHT) {
                height = MAX_FRAME_HEIGHT;
            }
            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - height) / 2;
//            frame = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            int b = getResources().getDimensionPixelOffset(R.dimen.border);
            frame = new Rect(b, b, displayWidth - b, displayHeight - b);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = -1;
                lastY = -1;
                return true;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();

                try {
                    Rect rect = getFramingRect();

                    final int BUFFER = 50;
                    final int BIG_BUFFER = 60;
                    if (lastX >= 0) {
                        if (((currentX >= rect.left - BIG_BUFFER && currentX <= rect.left
                                + BIG_BUFFER) || (lastX >= rect.left - BIG_BUFFER && lastX <= rect.left
                                + BIG_BUFFER))
                                && ((currentY <= rect.top + BIG_BUFFER && currentY >= rect.top
                                - BIG_BUFFER) || (lastY <= rect.top
                                + BIG_BUFFER && lastY >= rect.top
                                - BIG_BUFFER))) {
                            adjustFramingRect(2 * (lastX - currentX),
                                    2 * (lastY - currentY), screenResolution);
                        } else if (((currentX >= rect.right - BIG_BUFFER && currentX <= rect.right
                                + BIG_BUFFER) || (lastX >= rect.right - BIG_BUFFER && lastX <= rect.right
                                + BIG_BUFFER))
                                && ((currentY <= rect.top + BIG_BUFFER && currentY >= rect.top
                                - BIG_BUFFER) || (lastY <= rect.top
                                + BIG_BUFFER && lastY >= rect.top
                                - BIG_BUFFER))) {
                            adjustFramingRect(2 * (currentX - lastX),
                                    2 * (lastY - currentY), screenResolution);
                        } else if (((currentX >= rect.left - BIG_BUFFER && currentX <= rect.left
                                + BIG_BUFFER) || (lastX >= rect.left - BIG_BUFFER && lastX <= rect.left
                                + BIG_BUFFER))
                                && ((currentY <= rect.bottom + BIG_BUFFER && currentY >= rect.bottom
                                - BIG_BUFFER) || (lastY <= rect.bottom
                                + BIG_BUFFER && lastY >= rect.bottom
                                - BIG_BUFFER))) {
                            adjustFramingRect(2 * (lastX - currentX),
                                    2 * (currentY - lastY), screenResolution);
                        } else if (((currentX >= rect.right - BIG_BUFFER && currentX <= rect.right
                                + BIG_BUFFER) || (lastX >= rect.right - BIG_BUFFER && lastX <= rect.right
                                + BIG_BUFFER))
                                && ((currentY <= rect.bottom + BIG_BUFFER && currentY >= rect.bottom
                                - BIG_BUFFER) || (lastY <= rect.bottom
                                + BIG_BUFFER && lastY >= rect.bottom
                                - BIG_BUFFER))) {
                            adjustFramingRect(2 * (currentX - lastX),
                                    2 * (currentY - lastY), screenResolution);
                        } else if (((currentX >= rect.left - BUFFER && currentX <= rect.left
                                + BUFFER) || (lastX >= rect.left - BUFFER && lastX <= rect.left
                                + BUFFER))
                                && ((currentY <= rect.bottom && currentY >= rect.top) || (lastY <= rect.bottom && lastY >= rect.top))) {
                            adjustFramingRect(2 * (lastX - currentX), 0,
                                    screenResolution);
                        } else if (((currentX >= rect.right - BUFFER && currentX <= rect.right
                                + BUFFER) || (lastX >= rect.right - BUFFER && lastX <= rect.right
                                + BUFFER))
                                && ((currentY <= rect.bottom && currentY >= rect.top) || (lastY <= rect.bottom && lastY >= rect.top))) {
                            adjustFramingRect(2 * (currentX - lastX), 0,
                                    screenResolution);
                        } else if (((currentY <= rect.top + BUFFER && currentY >= rect.top
                                - BUFFER) || (lastY <= rect.top + BUFFER && lastY >= rect.top
                                - BUFFER))
                                && ((currentX <= rect.right && currentX >= rect.left) || (lastX <= rect.right && lastX >= rect.left))) {
                            adjustFramingRect(0, 2 * (lastY - currentY),
                                    screenResolution);
                        } else if (((currentY <= rect.bottom + BUFFER && currentY >= rect.bottom
                                - BUFFER) || (lastY <= rect.bottom + BUFFER && lastY >= rect.bottom
                                - BUFFER))
                                && ((currentX <= rect.right && currentX >= rect.left) || (lastX <= rect.right && lastX >= rect.left))) {
                            adjustFramingRect(0, 2 * (currentY - lastY),
                                    screenResolution);
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                v.invalidate();
                lastX = currentX;
                lastY = currentY;
                return true;
            case MotionEvent.ACTION_UP:
                lastX = -1;
                lastY = -1;
                return true;
        }
        return false;
    }
}
