/**
 * 
 */
package simbio.se.sau.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import simbio.se.sau.API;
import simbio.se.sau.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * A class that extends {@link ImageView} but it get images from web and store it on device, so you need android.permission.INTERNET and android.permission.WRITE_EXTERNAL_STORAGE permissions. The image is cached to be used later
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Feb 3, 2014 6:25:40 PM
 * @since {@link API#Version_3_1_2}
 */
public class RemoteImageView extends ImageView {

	protected int defaultImage;
	protected int errorImage;
	protected String imageUrl;
	protected boolean imageExistsOnCache = false;

	protected Runnable getImageRunnable = new Runnable() {
		@Override
		public void run() {
			String imagePath = getFileNameByUrl(imageUrl);
			if (imagePath == null) {
				getImageHandler.post(new Runnable() {
					@Override
					public void run() {
						setImageResource(errorImage);
					}
				});
				return;
			}
			File file = new File(getContext().getFilesDir(), imagePath);
			if (file.exists()) {
				Bitmap bitmap;
				try {
					bitmap = BitmapFactory.decodeFile(file.getPath());
				} catch (Exception exception) {
					bitmap = null;
				}
				final Bitmap bitmap1 = bitmap;
				if (bitmap1 != null) {
					getImageHandler.post(new Runnable() {
						@Override
						public void run() {
							setImageBitmap(bitmap1);
						}
					});
					return;
				}
			}
			try {
				Bitmap bitmap2 = BitmapFactory.decodeStream(new URL(imageUrl).openConnection().getInputStream());
				FileOutputStream out = getContext().openFileOutput(imagePath, Context.MODE_PRIVATE);
				bitmap2.compress(Bitmap.CompressFormat.PNG, 100, out);
				out.close();
			} catch (Exception exception) {
				getImageHandler.post(new Runnable() {
					@Override
					public void run() {
						setImageResource(errorImage);
					}
				});
				return;
			}
			run();
		}
	};
	protected Handler getImageHandler;

	/**
	 * @see ImageView#ImageView(Context, AttributeSet, int)
	 * @since {@link API#Version_3_1_2}
	 */
	public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	/**
	 * @see ImageView#ImageView(Context, AttributeSet)
	 * @since {@link API#Version_3_1_2}
	 */
	public RemoteImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	/**
	 * @see ImageView#ImageView(Context)
	 * @since {@link API#Version_3_1_2}
	 */
	public RemoteImageView(Context context) {
		super(context);
		init(null);
	}

	/**
	 * Start some variables
	 * 
	 * @since {@link API#Version_3_1_2}
	 */
	protected void init(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RemoteImageView);
			setDefaultImage(typedArray.getResourceId(R.styleable.RemoteImageView_defaultImage, defaultImage));
			setErrorImage(typedArray.getResourceId(R.styleable.RemoteImageView_errorImage, errorImage));
			setImageUrl(typedArray.getString(R.styleable.RemoteImageView_imageUrl));
			typedArray.recycle();
			start();
		}
	}

	/**
	 * Set image, it starts a download if the image is not cached
	 * 
	 * @since {@link API#Version_3_1_2}
	 */
	public void start() {
		if (imageExistsOnCache) {
			try {
				Bitmap bitmap = BitmapFactory.decodeFile(new File(getContext().getFilesDir(), getFileNameByUrl(imageUrl)).getPath());
				setImageBitmap(bitmap);
			} catch (Exception exception) {
				setImageResource(defaultImage);
			}
		} else {
			setImageResource(defaultImage);
		}
		getImageHandler = new Handler();
		new Thread(getImageRunnable).start();
	}

	/**
	 * Set the image url, this method starts a {@link Thread} with internet and memory access
	 * 
	 * @param imageUrl
	 *            a {@link String} with the url to be getted
	 * @since {@link API#Version_3_1_2}
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		if (imageUrl != null) {
			String fileName = getFileNameByUrl(imageUrl);
			imageExistsOnCache = new File(getContext().getFilesDir(), fileName).exists();
		} else {
			imageExistsOnCache = false;
		}
	}

	/**
	 * @param defaultImage
	 *            the int resource id to set the Default image
	 * @since {@link API#Version_3_1_2}
	 */
	public void setDefaultImage(int defaultImage) {
		this.defaultImage = defaultImage;
	}

	/**
	 * @param errorImage
	 *            the int resource id to set the Error image if downloads fails
	 * @since {@link API#Version_3_1_2}
	 */
	public void setErrorImage(int errorImage) {
		this.errorImage = errorImage;
	}

	/**
	 * Delete the cached image
	 * 
	 * @since {@link API#Version_3_1_2}
	 */
	public void deleteCachedImage() {
		this.imageExistsOnCache = false;
		String fileName = getFileNameByUrl(imageUrl);
		if (fileName != null)
			new File(getContext().getFilesDir(), fileName).delete();
		setImageResource(defaultImage);
	}

	/**
	 * @param imageUrl
	 *            the image url
	 * @return the file name basead on file url, returns <code>null</code> if catch a {@link UnsupportedEncodingException} on utf8 encode or if the imageUrl param is <code>null</code>
	 * @since {@link API#Version_3_1_2}
	 */
	public String getFileNameByUrl(String imageUrl) {
		if (imageUrl == null)
			return null;
		try {
			return URLEncoder.encode(imageUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
