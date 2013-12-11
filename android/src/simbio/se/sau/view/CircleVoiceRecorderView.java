/**
 * 
 */
package simbio.se.sau.view;

import simbio.se.sau.API;
import simbio.se.sau.R;
import simbio.se.sau.voicerecord.VoiceRecorderView;
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

	protected Runnable animateRunnable = new Runnable() {
		@Override
		public void run() {
			animate().scaleX(volume).scaleY(volume).setListener(CircleVoiceRecorderView.this).setDuration(animationDuration).start();
		}
	};

	protected Runnable endAnimateRunnable = new Runnable() {
		@Override
		public void run() {
			animate().scaleX(1.0f).scaleY(1.0f).setListener(CircleVoiceRecorderView.this).setDuration(animationDuration).start();
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
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleVoiceRecorderView);
		paint.setColor(typedArray.getColor(R.styleable.CircleVoiceRecorderView_color, Color.BLACK));
		setMinSize(typedArray.getFloat(R.styleable.CircleVoiceRecorderView_minSize, minSize));
		setMaxSize(typedArray.getFloat(R.styleable.CircleVoiceRecorderView_maxSize, maxSize));
		setAnimationDuration((long) typedArray.getInt(R.styleable.CircleVoiceRecorderView_animationDuration, (int) animationDuration));
		typedArray.recycle();
	}

	/**
	 * @param color
	 *            the color to be used on {@link Paint}
	 * @since {@link API#Version_3_1_0}
	 */
	public void setColor(int color) {
		paint.setColor(color);
	}

	/**
	 * @param minSize
	 *            the minSize to set
	 * @since {@link API#Version_3_1_0}
	 */
	public void setMinSize(float minSize) {
		this.minSize = minSize;
	}

	/**
	 * @param maxSize
	 *            the maxSize to set
	 * @since {@link API#Version_3_1_0}
	 */
	public void setMaxSize(float maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * @param animationDuration
	 *            the animationDuration to set
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
		canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 4, paint);
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
		post(endAnimateRunnable);
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
