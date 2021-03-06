/**
 * Copyright 2013-2014
 * Ademar Alves de Oliveira <ademar111190@gmail.com /> Simbio.se
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simbio.se.sau.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

import simbio.se.sau.API;
import simbio.se.sau.R;
import simbio.se.sau.voicerecord.VoiceRecorderView;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Dec 11, 2013 12:19:53 AM
 * @since {@link API#Version_3_1_0}
 */
@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class CircleVoiceRecorderView extends View implements VoiceRecorderView, AnimatorListener {

    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    protected float volume = 0.0f;
    protected boolean animationRunning = false;
    protected float minSize = 0.75f;
    protected float maxSize = 1.25f;
    protected long animationDuration = 50l;
    protected int paintColor = Color.BLACK;
    protected int paintColorOff = Color.BLACK;

    protected Runnable animateRunnable = new Runnable() {
        @Override
        public void run() {
            ViewPropertyAnimator viewPropertyAnimator = animate();
            if (viewPropertyAnimator == null)
                return;
            viewPropertyAnimator.scaleX(volume)
                    .scaleY(volume)
                    .setListener(CircleVoiceRecorderView.this)
                    .setDuration(animationDuration)
                    .start();
        }
    };

    protected Runnable endAnimateRunnable = new Runnable() {
        @Override
        public void run() {
            ViewPropertyAnimator viewPropertyAnimator = animate();
            if (viewPropertyAnimator == null)
                return;
            viewPropertyAnimator.scaleX(1.0f)
                    .scaleY(1.0f)
                    .setListener(CircleVoiceRecorderView.this)
                    .setDuration(animationDuration)
                    .start();
        }
    };

    /**
     * @see View#View(Context)
     * @since {@link API#Version_3_1_0}
     */
    public CircleVoiceRecorderView(Context context) {
        super(context);
    }

    /**
     * @see View#View(Context, AttributeSet)
     * @since {@link API#Version_3_1_0}
     */
    public CircleVoiceRecorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * @see View#View(Context, AttributeSet, int)
     * @since {@link API#Version_3_1_0}
     */
    public CircleVoiceRecorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs == null)
            return;
        Context context = getContext();
        if (context == null)
            return;
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CircleVoiceRecorderView
        );
        if (typedArray == null)
            return;
        setColor(typedArray.getColor(R.styleable.CircleVoiceRecorderView_color, Color.BLACK));
        setColorOff(typedArray.getColor(R.styleable.CircleVoiceRecorderView_colorOff, Color.BLACK));
        setMinSize(typedArray.getFloat(R.styleable.CircleVoiceRecorderView_minSize, minSize));
        setMaxSize(typedArray.getFloat(R.styleable.CircleVoiceRecorderView_maxSize, maxSize));
        setAnimationDuration((long) typedArray.getInt(
                R.styleable.CircleVoiceRecorderView_animationDuration,
                (int) animationDuration
        ));
        typedArray.recycle();
    }

    /**
     * @param color the color to be used on {@link Paint}
     * @since {@link API#Version_3_1_0}
     */
    public void setColor(int color) {
        paintColor = color;
        paint.setColor(color);
    }

    /**
     * @param color the color to be used on {@link Paint} when it off
     * @since {@link API#Version_3_1_0}
     */
    public void setColorOff(int color) {
        paintColorOff = color;
        paint.setColor(color);
    }

    /**
     * @param minSize the minSize to set
     * @since {@link API#Version_3_1_0}
     */
    public void setMinSize(float minSize) {
        this.minSize = minSize;
    }

    /**
     * @param maxSize the maxSize to set
     * @since {@link API#Version_3_1_0}
     */
    public void setMaxSize(float maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * @param animationDuration the animationDuration to set
     * @since {@link API#Version_3_1_0}
     */
    public void setAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                Math.min(getWidth(), getHeight()) / 4,
                paint
        );
    }

    /*
     * (non-Javadoc)
     *
     * @see simbio.se.sau.voicerecord.VoiceRecorderView#processSoundBuffer(short[], int)
     */
    @Override
    public void processSoundVolume(float volume) {
        if (animationRunning)
            return;
        this.volume = volume;
        if (this.volume > maxSize)
            this.volume = maxSize;
        else if (this.volume < minSize)
            this.volume = minSize;
        post(animateRunnable);
    }

    /*
     * (non-Javadoc)
     *
     * @see simbio.se.sau.voicerecord.VoiceRecorderView#processEnded()
     */
    @Override
    public void processEnded() {
    }

    @Override
    public void processStarted() {
    }

    @Override
    public void processHasTheFirstSong() {
        paint.setColor(paintColor);
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    @Override
    public void processHasTheLastSong() {
        post(endAnimateRunnable);
        paint.setColor(paintColorOff);
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see android.animation.Animator.AnimatorListener#onAnimationCancel(android.animation.Animator)
     */
    @Override
    public void onAnimationCancel(Animator animation) {
        animationRunning = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.animation.Animator.AnimatorListener#onAnimationEnd(android.animation.Animator)
     */
    @Override
    public void onAnimationEnd(Animator animation) {
        animationRunning = false;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.animation.Animator.AnimatorListener#onAnimationRepeat(android.animation.Animator)
     */
    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    /*
     * (non-Javadoc)
     *
     * @see android.animation.Animator.AnimatorListener#onAnimationStart(android.animation.Animator)
     */
    @Override
    public void onAnimationStart(Animator animation) {
        animationRunning = true;
    }
}
