/**
 * this file unlike the rest of the library is on the Apache license 2.0
 * because it was adapted from https://code.google.com/p/range-seek-bar/.
 *
 *   Copyright 2013 Stephan Tittel (stephan.tittel@kom.tu-darmstadt.de)
 *   Copyright 2013 Peter Sinnott (psinnott@gmail.com)
 *   Copyright 2013 Thomas Barrasso (tbarrasso@sevenplusandroid.org)
 *   Copyright 2013 Ademar Alves de Oliveira (ademar111190@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *               http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package simbio.se.sau.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageView;

import simbio.se.sau.API;
import simbio.se.sau.view.enums.RangeSeekBarNumberType;
import simbio.se.sau.view.enums.RangeSeekBarThumb;
import simbio.se.sau.view.interfaces.IRangeSeekBar;

/**
 * Widget that lets users select a minimum and maximum value on a given numerical range. The range
 * value types can be one of Long, Double, Integer, Float, Short, Byte or BigDecimal.<br /> <br />
 * Improved {@link MotionEvent} handling for smoother use, anti-aliased painting for improved
 * anesthetics.<br /> <br />
 * note it will break if android version is small then version 5 (eclair)
 *
 * @param <T> The Number type of the range values. One of Long, Double, Integer, Float, Short, Byte
 *            or BigDecimal.
 * @author Stephan Tittel (stephan.tittel@kom.tu-darmstadt.de)
 * @author Peter Sinnott (psinnott@gmail.com)
 * @author Thomas Barrasso (tbarrasso@sevenplusandroid.org)
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date 2013-sep-30 01:38:58
 * @see https://code.google.com/p/range-seek-bar/
 * @since {@link API#Version_2_0_0}
 */
@TargetApi(Build.VERSION_CODES.ECLAIR)
public class RangeSeekBar<T extends Number> extends ImageView {

    /**
     * @see ImageView#ImageView(Context, AttributeSet, int)
     * @since {@link API#Version_2_0_0}
     */
    public RangeSeekBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(getAbsoluteMinValue(), getSelectedMaxValue(), null, null, null);
    }

    /**
     * @see ImageView#ImageView(Context, AttributeSet)
     * @since {@link API#Version_2_0_0}
     */
    public RangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(getAbsoluteMinValue(), getSelectedMaxValue(), null, null, null);
    }

    /**
     * @see ImageView#ImageView(Context)
     * @since {@link API#Version_2_0_0}
     */
    public RangeSeekBar(Context context) {
        super(context);
        init(getAbsoluteMinValue(), getSelectedMaxValue(), null, null, null);
    }

    protected IRangeSeekBar<T> delegate;
    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected float thumbWidth = 0.0f;
    protected float thumbHalfWidth = 0.0f;
    protected float thumbHalfHeight = 0.0f;
    protected float lineHeight = 0.0f;
    protected float padding = thumbHalfWidth;
    protected RangeSeekBarNumberType numberType = RangeSeekBarNumberType.INTEGER;
    protected double absoluteMinValuePrim, absoluteMaxValuePrim;
    protected double normalizedMinValue = 0d;
    protected double normalizedMaxValue = 1d;
    protected RangeSeekBarThumb pressedThumb = null;
    protected boolean notifyWhileDragging = false;
    protected float mDownMotionX;
    protected int mActivePointerId = INVALID_POINTER_ID;
    protected int mScaledTouchSlop;
    protected boolean mIsDragging;
    protected Bitmap thumbImage = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    protected Bitmap thumbImagePressed = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

    @SuppressWarnings("unchecked")
    @SuppressLint("UseValueOf")
    protected T absoluteMinValue = (T) new Integer(0);

    @SuppressWarnings("unchecked")
    @SuppressLint("UseValueOf")
    protected T absoluteMaxValue = (T) new Integer(100);

    /**
     * Default color of a {@link RangeSeekBar}, #FF33B5E5. This is also known as
     * "Ice Cream Sandwich" blue.
     */
    private int lineColor = Color.argb(0xFF, 0x33, 0xB5, 0xE5);

    /**
     * An invalid pointer id.
     */
    public static int INVALID_POINTER_ID = 255;

    /**
     * Localized constants from MotionEvent for compatibility with API < 8 "Froyo".
     */
    public static int ACTION_POINTER_UP = 0x6;
    public static int ACTION_POINTER_INDEX_MASK = 0x0000ff00;
    public static int ACTION_POINTER_INDEX_SHIFT = 8;

    /**
     * On touch, this offset plus the scaled value from the position of the touch will form the
     * progress value. Usually 0.
     */
    float mTouchProgressOffset;

    /**
     * Make your new RangeSeekBar usable
     *
     * @param absoluteMinValue  The minimum value of the selectable range.
     * @param absoluteMaxValue  The maximum value of the selectable range.
     * @param thumbImage        the {@link Bitmap} to be used as thumb
     * @param thumbImagePressed the {@link Bitmap} to be used as thumb when user press it
     * @param delegate          the {@link IRangeSeekBar} with delegate methods
     * @throws IllegalArgumentException Will be thrown if min/max value type is not one of Long,
     *                                  Double, Integer, Float, Short, Byte or BigDecimal.
     * @since {@link API#Version_2_0_0}
     */
    public void init(
            T absoluteMinValue,
            T absoluteMaxValue,
            Bitmap thumbImage,
            Bitmap thumbImagePressed,
            IRangeSeekBar<T> delegate
    ) throws IllegalArgumentException {
        this.absoluteMinValue = absoluteMinValue;
        this.absoluteMaxValue = absoluteMaxValue;
        this.absoluteMinValuePrim = absoluteMinValue.doubleValue();
        this.absoluteMaxValuePrim = absoluteMaxValue.doubleValue();
        this.numberType = RangeSeekBarNumberType.fromNumber(absoluteMinValue);
        this.delegate = delegate;

        // make RangeSeekBar focusable. This solves focus handling issues in case EditText widgets
        // are being used along with the RangeSeekBar within ScrollViews.
        setFocusable(true);
        setFocusableInTouchMode(true);

        if (thumbImage != null)
            this.thumbImage = thumbImage;
        if (thumbImagePressed != null)
            this.thumbImagePressed = thumbImagePressed;

        thumbWidth = this.thumbImage.getWidth();
        thumbHalfWidth = 0.5f * thumbWidth;
        thumbHalfHeight = 0.5f * this.thumbImage.getHeight();
        lineHeight = 0.3f * thumbHalfHeight;

        Context context = getContext();
        if (android.os.Build.VERSION.SDK_INT >= 3 && context != null)
            mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * @return if should notify while dragging
     * @since {@link API#Version_2_0_0}
     */
    public boolean isNotifyWhileDragging() {
        return notifyWhileDragging;
    }

    /**
     * Should the widget notify the listener callback while the user is still dragging a thumb?
     * Default is false.
     *
     * @param flag the {@link Boolean} value to be seted
     * @since {@link API#Version_2_0_0}
     */
    public void setNotifyWhileDragging(boolean flag) {
        this.notifyWhileDragging = flag;
    }

    /**
     * Returns the absolute minimum value of the range that has been set at construction time.
     *
     * @return The absolute minimum value of the range.
     * @since {@link API#Version_2_0_0}
     */
    public T getAbsoluteMinValue() {
        return absoluteMinValue;
    }

    /**
     * Returns the absolute maximum value of the range that has been set at construction time.
     *
     * @return The absolute maximum value of the range.
     * @since {@link API#Version_2_0_0}
     */
    public T getAbsoluteMaxValue() {
        return absoluteMaxValue;
    }

    /**
     * Returns the currently selected min value.
     *
     * @return The currently selected min value.
     * @since {@link API#Version_2_0_0}
     */
    public T getSelectedMinValue() {
        return normalizedToValue(normalizedMinValue);
    }

    /**
     * Sets the currently selected minimum value. The widget will be invalidated and redrawn.
     *
     * @param value The Number value to set the minimum value to. Will be clamped to given absolute
     *              minimum/maximum range.
     * @since {@link API#Version_2_0_0}
     */
    public void setSelectedMinValue(T value) {
        // in case absoluteMinValue == absoluteMaxValue, avoid division by zero when normalizing.
        if (0 == (absoluteMaxValuePrim - absoluteMinValuePrim)) {
            setNormalizedMinValue(0d);
        } else {
            setNormalizedMinValue(valueToNormalized(value));
        }
    }

    /**
     * Returns the currently selected max value.
     *
     * @return The currently selected max value.
     * @since {@link API#Version_2_0_0}
     */
    public T getSelectedMaxValue() {
        return normalizedToValue(normalizedMaxValue);
    }

    /**
     * Sets the currently selected maximum value. The widget will be invalidated and redrawn.
     *
     * @param value The Number value to set the maximum value to. Will be clamped to given absolute
     *              minimum/maximum range.
     * @since {@link API#Version_2_0_0}
     */
    public void setSelectedMaxValue(T value) {
        // in case absoluteMinValue == absoluteMaxValue, avoid division by zero when normalizing.
        if (0 == (absoluteMaxValuePrim - absoluteMinValuePrim)) {
            setNormalizedMaxValue(1d);
        } else {
            setNormalizedMaxValue(valueToNormalized(value));
        }
    }

    /**
     * Handles thumb selection and movement. Notifies listener callback on certain events.
     *
     * @see OnTouchListener#onTouch(android.view.View, MotionEvent)
     * @since {@link API#Version_2_0_0}
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (android.os.Build.VERSION.SDK_INT < 5)
            return false;

        if (!isEnabled())
            return false;

        int pointerIndex;

        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                // Remember where the motion event started
                mActivePointerId = event.getPointerId(event.getPointerCount() - 1);
                pointerIndex = event.findPointerIndex(mActivePointerId);
                mDownMotionX = event.getX(pointerIndex);

                pressedThumb = evalPressedThumb(mDownMotionX);

                // Only handle thumb presses.
                if (pressedThumb == null)
                    return super.onTouchEvent(event);

                setPressed(true);
                invalidate();
                onStartTrackingTouch();
                trackTouchEvent(event);
                attemptClaimDrag();

                break;
            case MotionEvent.ACTION_MOVE:
                if (pressedThumb != null) {

                    if (mIsDragging) {
                        trackTouchEvent(event);
                    } else {
                        // Scroll to follow the motion event
                        pointerIndex = event.findPointerIndex(mActivePointerId);
                        final float x = event.getX(pointerIndex);

                        if (Math.abs(x - mDownMotionX) > mScaledTouchSlop) {
                            setPressed(true);
                            invalidate();
                            onStartTrackingTouch();
                            trackTouchEvent(event);
                            attemptClaimDrag();
                        }
                    }

                    if (notifyWhileDragging && delegate != null)
                        delegate.onRangeSeekBarValuesChanged(
                                this,
                                getSelectedMinValue(),
                                getSelectedMaxValue()
                        );
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsDragging) {
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                    setPressed(false);
                } else {
                    // Touch up when we never crossed the touch slop threshold
                    // should be interpreted as a tap-seek to that location.
                    onStartTrackingTouch();
                    trackTouchEvent(event);
                    onStopTrackingTouch();
                }

                pressedThumb = null;
                invalidate();
                if (delegate != null)
                    delegate.onRangeSeekBarValuesChanged(
                            this,
                            getSelectedMinValue(),
                            getSelectedMaxValue()
                    );
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int index = event.getPointerCount() - 1;
                // final int index = ev.getActionIndex();
                mDownMotionX = event.getX(index);
                mActivePointerId = event.getPointerId(index);
                invalidate();
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(event);
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsDragging) {
                    onStopTrackingTouch();
                    setPressed(false);
                }
                invalidate(); // see above explanation
                break;
        }
        return true;
    }

    /**
     * @param ev the {@link MotionEvent}
     * @since {@link API#Version_2_0_0}
     */
    protected final void onSecondaryPointerUp(MotionEvent ev) {
        if (android.os.Build.VERSION.SDK_INT < 5)
            return;

        final int pointerIndex = (ev.getAction() & ACTION_POINTER_INDEX_MASK)
                >> ACTION_POINTER_INDEX_SHIFT;

        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose
            // a new active pointer and adjust accordingly.
            // Make this decision more intelligent.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mDownMotionX = ev.getX(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    /**
     * @param event the {@link MotionEvent}
     * @since {@link API#Version_2_0_0}
     */
    protected final void trackTouchEvent(MotionEvent event) {
        if (android.os.Build.VERSION.SDK_INT < 5)
            return;

        final int pointerIndex = event.findPointerIndex(mActivePointerId);
        final float x = event.getX(pointerIndex);

        if (RangeSeekBarThumb.MIN.equals(pressedThumb)) {
            setNormalizedMinValue(screenToNormalized(x));
        } else if (RangeSeekBarThumb.MAX.equals(pressedThumb)) {
            setNormalizedMaxValue(screenToNormalized(x));
        }
    }

    /**
     * Tries to claim the user's drag motion, and requests disallowing any ancestors from stealing
     * events in the drag.
     *
     * @since {@link API#Version_2_0_0}
     */
    protected void attemptClaimDrag() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    /**
     * This is called when the user has started touching this widget.
     *
     * @since {@link API#Version_2_0_0}
     */
    void onStartTrackingTouch() {
        mIsDragging = true;
    }

    /**
     * This is called when the user either releases his touch or the touch is canceled.
     *
     * @since {@link API#Version_2_0_0}
     */
    void onStopTrackingTouch() {
        mIsDragging = false;
    }

    /**
     * Ensures correct size of the widget.
     *
     * @since {@link API#Version_2_0_0}
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = 200;
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec)) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = thumbImage.getHeight();
        if (MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec)) {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        setMeasuredDimension(width, height);
    }

    /**
     * Draws the widget on the given canvas.
     *
     * @since {@link API#Version_2_0_0}
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw seek bar background line
        final RectF rect = new RectF(
                padding,
                0.5f * (getHeight() - lineHeight),
                getWidth() - padding,
                0.5f * (getHeight() + lineHeight)
        );
        paint.setStyle(Style.FILL);
        paint.setColor(Color.GRAY);
        paint.setAntiAlias(true);
        canvas.drawRect(rect, paint);

        // draw seek bar active range line
        rect.left = normalizedToScreen(normalizedMinValue);
        rect.right = normalizedToScreen(normalizedMaxValue);

        // orange color
        paint.setColor(lineColor);
        canvas.drawRect(rect, paint);

        // draw minimum thumb
        drawThumb(
                normalizedToScreen(normalizedMinValue),
                RangeSeekBarThumb.MIN.equals(pressedThumb),
                canvas
        );

        // draw maximum thumb
        drawThumb(
                normalizedToScreen(normalizedMaxValue),
                RangeSeekBarThumb.MAX.equals(pressedThumb),
                canvas
        );
    }

    /**
     * Overridden to save instance state when device orientation changes. This method is called
     * automatically if you assign an id to the RangeSeekBar widget using the {@link #setId(int)}
     * method. Other members of this class than the normalized min and max
     * values don't need to be saved.
     *
     * @since {@link API#Version_2_0_0}
     */
    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", normalizedMinValue);
        bundle.putDouble("MAX", normalizedMaxValue);
        return bundle;
    }

    /**
     * Overridden to restore instance state when device orientation changes. This method is called
     * automatically if you assign an id to the RangeSeekBar widget using the {@link #setId(int)}
     * method.
     *
     * @since {@link API#Version_2_0_0}
     */
    @Override
    protected void onRestoreInstanceState(Parcelable parcel) {
        final Bundle bundle = (Bundle) parcel;
        super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
        normalizedMinValue = bundle.getDouble("MIN");
        normalizedMaxValue = bundle.getDouble("MAX");
    }

    /**
     * Draws the "normal" resp. "pressed" thumb image on specified x-coordinate.
     *
     * @param screenCoord The x-coordinate in screen space where to draw the image.
     * @param pressed     Is the thumb currently in "pressed" state?
     * @param canvas      The canvas to draw upon.
     * @since {@link API#Version_2_0_0}
     */
    protected void drawThumb(float screenCoord, boolean pressed, Canvas canvas) {
        canvas.drawBitmap(
                pressed
                        ? thumbImagePressed
                        : thumbImage,
                screenCoord - thumbHalfWidth,
                ((0.5f * getHeight()) - thumbHalfHeight),
                paint
        );
    }

    /**
     * Decides which (if any) thumb is touched by the given x-coordinate.
     *
     * @param touchX The x-coordinate of a touch event in screen space.
     * @return The pressed {@link RangeSeekBarThumb} or null if none has been touched.
     * @since {@link API#Version_2_0_0}
     */
    protected RangeSeekBarThumb evalPressedThumb(float touchX) {
        RangeSeekBarThumb result = null;
        boolean minThumbPressed = isInThumbRange(touchX, normalizedMinValue);
        boolean maxThumbPressed = isInThumbRange(touchX, normalizedMaxValue);
        if (minThumbPressed && maxThumbPressed) {
            // if both thumbs are pressed (they lie on top of each other), choose the one with more
            // room to drag. this avoids "stalling" the thumbs in a corner, not being able to drag
            // them apart anymore.
            result = (touchX / getWidth() > 0.5f) ? RangeSeekBarThumb.MIN : RangeSeekBarThumb.MAX;
        } else if (minThumbPressed) {
            result = RangeSeekBarThumb.MIN;
        } else if (maxThumbPressed) {
            result = RangeSeekBarThumb.MAX;
        }
        return result;
    }

    /**
     * Decides if given x-coordinate in screen space needs to be interpreted as "within" the
     * normalized thumb x-coordinate.
     *
     * @param touchX               The x-coordinate in screen space to check.
     * @param normalizedThumbValue The normalized x-coordinate of the thumb to check.
     * @return true if x-coordinate is in thumb range, false otherwise.
     * @since {@link API#Version_2_0_0}
     */
    protected boolean isInThumbRange(float touchX, double normalizedThumbValue) {
        return Math.abs(touchX - normalizedToScreen(normalizedThumbValue)) <= thumbHalfWidth;
    }

    /**
     * Sets normalized min value to value so that 0 <= value <= normalized max value <= 1. The View
     * will get invalidated when calling this method.
     *
     * @param value The new normalized min value to set.
     * @since {@link API#Version_2_0_0}
     */
    public void setNormalizedMinValue(double value) {
        normalizedMinValue = Math.max(0d, Math.min(1d, Math.min(value, normalizedMaxValue)));
        invalidate();
    }

    /**
     * Sets normalized max value to value so that 0 <= normalized min value <= value <= 1. The View
     * will get invalidated when calling this method.
     *
     * @param value The new normalized max value to set.
     * @since {@link API#Version_2_0_0}
     */
    public void setNormalizedMaxValue(double value) {
        normalizedMaxValue = Math.max(0d, Math.min(1d, Math.max(value, normalizedMinValue)));
        invalidate();
    }

    /**
     * Converts a normalized value to a Number object in the value space between absolute minimum
     * and maximum.
     *
     * @param normalized the normalized value to be normalized
     * @return the normalized value
     * @since {@link API#Version_2_0_0}
     */
    @SuppressWarnings("unchecked")
    protected T normalizedToValue(double normalized) {
        return (T) numberType.toNumber(
                absoluteMinValuePrim + normalized * (absoluteMaxValuePrim - absoluteMinValuePrim)
        );
    }

    /**
     * Converts the given Number value to a normalized double.
     *
     * @param value The Number value to normalize.
     * @return The normalized double.
     * @since {@link API#Version_2_0_0}
     */
    protected double valueToNormalized(T value) {
        if (0 == absoluteMaxValuePrim - absoluteMinValuePrim) {
            // prevent division by zero, simply return 0.
            return 0d;
        }
        return (value.doubleValue() - absoluteMinValuePrim)
                / (absoluteMaxValuePrim - absoluteMinValuePrim);
    }

    /**
     * Converts a normalized value into screen space.
     *
     * @param normalizedCoord The normalized value to convert.
     * @return The converted value in screen space.
     * @since {@link API#Version_2_0_0}
     */
    protected float normalizedToScreen(double normalizedCoord) {
        return (float) (padding + normalizedCoord * (getWidth() - 2 * padding));
    }

    /**
     * Converts screen space x-coordinates into normalized values.
     *
     * @param screenCoord The x-coordinate in screen space to convert.
     * @return The normalized value.
     * @since {@link API#Version_2_0_0}
     */
    protected double screenToNormalized(float screenCoord) {
        int width = getWidth();
        if (width <= 2 * padding) {
            // prevent division by zero, simply return 0.
            return 0d;
        } else {
            double result = (screenCoord - padding) / (width - 2 * padding);
            return Math.min(1d, Math.max(0d, result));
        }
    }

    /**
     * Callback listener interface to notify about changed range values.
     *
     * @param <T> The Number type the RangeSeekBar has been declared with.
     * @author Stephan Tittel (stephan.tittel@kom.tu-darmstadt.de)
     * @since {@link API#Version_2_0_0}
     */
    public interface OnRangeSeekBarChangeListener<T> {
        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, T minValue, T maxValue);
    }

    /**
     * @param lineColor the lineColor to set
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

}
