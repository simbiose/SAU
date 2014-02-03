package simbio.se.sau.sample;

import static simbio.se.sau.iterable.Range.range;
import simbio.se.sau.device.DeviceInformationsManager;
import simbio.se.sau.log.SimbiLog;
import simbio.se.sau.sample.documents.ActivityDocumentUtils;
import simbio.se.sau.sample.record.RecordActivity;
import simbio.se.sau.share.SimbiShare;
import simbio.se.sau.view.ClipboardTextView;
import simbio.se.sau.view.ClipboardTextView.CopiedInterface;
import simbio.se.sau.view.RangeSeekBar;
import simbio.se.sau.view.RemoteImageView;
import simbio.se.sau.view.animation.ResizeAnimation;
import simbio.se.sau.view.interfaces.IRangeSeekBar;
import simbio.se.sau.widget.ToastMaker;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Toast;

/**
 * @author Ademar Alves de Oliveira <ademar111190@gmail.com>
 * @date 2013-aŭg-21 06:02:21
 * 
 */
public class MainActivity extends Activity implements AnimationListener, IRangeSeekBar<Integer>, CopiedInterface {

	private boolean hasResized = false;
	private boolean hasHeightResized = false;
	private boolean hasWidthResized = false;

	private static final int resizeChange = 200;
	private static final int resizeDuration = 300;

	private RemoteImageView remoteImageView;

	// Activity methods with override implementation

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimbiLog.log(this, savedInstanceState);

		setContentView(R.layout.activity_main);

		// range and log examples
		for (int i : range(3))
			SimbiLog.print("range example a", i);
		for (int i : range(-4))
			SimbiLog.print("range example b", i);
		for (int i : range(4, 7))
			SimbiLog.print("range example c", i);
		for (int i : range(-5, -30, 10))
			SimbiLog.print("range example d", i);

		// range seek bar
		@SuppressWarnings("unchecked")
		RangeSeekBar<Integer> rangeSeekBar = (RangeSeekBar<Integer>) findViewById(R.id.range_seek_bar);
		rangeSeekBar.init(0, 100, BitmapFactory.decodeResource(getResources(), R.drawable.scrubber_control_normal_holo), BitmapFactory.decodeResource(getResources(), R.drawable.scrubber_control_pressed_holo), this);
		rangeSeekBar.setNotifyWhileDragging(true);
		rangeSeekBar.setNormalizedMinValue(0.3f);
		rangeSeekBar.setNormalizedMaxValue(0.7f);
		rangeSeekBar.setNotifyWhileDragging(true);
		rangeSeekBar.setLineColor(Color.rgb(0, 153, 0));

		((ClipboardTextView) findViewById(R.id.clipboard_text_view)).setCopyListener(this);

		// Remote image view example, using java you can use just xml, the other 2 images are in xml, one get a real image and the other force the fail
		remoteImageView = (RemoteImageView) findViewById(R.id.remote_imageview_in_java_code);
		// I want reload ever time when the image is clicked.
		View.OnClickListener remoteImageViewClickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				remoteImageView.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d7/Android_robot.svg/200px-Android_robot.svg.png");
				remoteImageView.setErrorImage(R.drawable.remote_image_fail);
				remoteImageView.setDefaultImage(R.drawable.remote_image_default);
				remoteImageView.deleteCachedImage();
				remoteImageView.start();
			}
		};
		remoteImageView.setOnClickListener(remoteImageViewClickListener);
		// this line just force the first request when app opens
		remoteImageViewClickListener.onClick(remoteImageView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SimbiLog.log(this);

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SimbiLog.log(this);

		switch (item.getItemId()) {
		case R.id.action_share:
			SimbiLog.print("Share action!");
			SimbiShare.shareWithChooser(this, R.string.hello_world, R.string.app_name, R.string.action_share);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// onclicks

	public void openSqlActivity(View view) {
		startActivity(new Intent(getApplicationContext(), SqlExampleActivity.class));
	}

	public void openRecActivity(View view) {
		startActivity(new Intent(getApplicationContext(), RecordActivity.class));
	}

	public void openDocActivity(View view) {
		startActivity(new Intent(getApplicationContext(), ActivityDocumentUtils.class));
	}

	public void showToaster(View view) {
		int random = (int) (Math.random() * 5.0);
		switch (random) {
		case 0:
			ToastMaker.toast(getApplicationContext(), "uses length short", Toast.LENGTH_SHORT);
			break;
		case 1:
			ToastMaker.toast(getApplicationContext(), R.string.app_name, Toast.LENGTH_SHORT);
			break;
		case 2:
			ToastMaker.toast(getApplicationContext(), "Lorem ipsum");
			break;
		case 3:
			ToastMaker.toast(getApplicationContext(), R.string.hello_world);
			break;
		default:
			ToastMaker.toast(getApplicationContext());
			break;
		}
	}

	public void showUserEmail(View view) {
		ToastMaker.toast(getApplicationContext(), new DeviceInformationsManager(getApplicationContext()).getPrimaryUserEmailOrNull());
	}

	public void resize(View view) {
		ResizeAnimation resizeAnimation;
		if (hasResized)
			resizeAnimation = new ResizeAnimation(view, view.getHeight() - resizeChange, view.getWidth() + resizeChange);
		else
			resizeAnimation = new ResizeAnimation(view, view.getHeight() + resizeChange, view.getWidth() - resizeChange);
		hasResized = !hasResized;
		resizeAnimation.setDuration(resizeDuration);
		resizeAnimation.setAnimationListener(this);
		view.startAnimation(resizeAnimation);
	}

	public void resizeHeight(View view) {
		ResizeAnimation resizeAnimation;
		if (hasHeightResized)
			resizeAnimation = new ResizeAnimation(view, view.getHeight() - resizeChange, true);
		else
			resizeAnimation = new ResizeAnimation(view, view.getHeight() + resizeChange, true);
		hasHeightResized = !hasHeightResized;
		resizeAnimation.setDuration(resizeDuration);
		resizeAnimation.setAnimationListener(this);
		view.startAnimation(resizeAnimation);
	}

	public void resizeWidth(View view) {
		ResizeAnimation resizeAnimation;
		if (hasWidthResized)
			resizeAnimation = new ResizeAnimation(view, view.getWidth() + resizeChange, false);
		else
			resizeAnimation = new ResizeAnimation(view, view.getWidth() - resizeChange, false);
		hasWidthResized = !hasWidthResized;
		resizeAnimation.setDuration(resizeDuration);
		resizeAnimation.setAnimationListener(this);
		view.startAnimation(resizeAnimation);
	}

	// interfaces methods

	@Override
	public void onAnimationEnd(Animation animation) {
		SimbiLog.log(this);
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		SimbiLog.log(this);
	}

	@Override
	public void onAnimationStart(Animation animation) {
		SimbiLog.log(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simbio.se.sau.view.RangeSeekBar.OnRangeSeekBarChangeListener#onRangeSeekBarValuesChanged(simbio.se.sau.view.RangeSeekBar, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		SimbiLog.print(minValue, maxValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		SimbiLog.printText("clicked", v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see simbio.se.sau.view.ClipboardTextView.CopiedInterface#textHasCopied(simbio.se.sau.view.ClipboardTextView, java.lang.String, boolean)
	 */
	@Override
	public void textHasCopied(ClipboardTextView clipboardTextView, String stringCopied, boolean hasSucces) {
		SimbiLog.print(stringCopied, hasSucces);
		ToastMaker.toast(getApplicationContext(), stringCopied);
	}
}
