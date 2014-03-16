/**
 * 
 */
package simbio.se.sau.share;

import simbio.se.sau.API;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;

/**
 * A class to simplify the share actions
 * 
 * @author Ademar Alves de Oliveira ademar111190@gmail.com
 * @date 2013-aŭg-21 05:30:46
 * 
 * @since {@link API#Version_2_0_0}
 * 
 */
public class SimbiShare {

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the {@link String} will be shared, can be <code>null</code> but it no make sense
	 * @param subject
	 *            the {@link String} subject, can be <code>null</code>
	 * @param title
	 *            the {@link String} title of dialog, can be <code>null</code>
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, String content, String subject, String title) {
		Intent intent = new Intent(android.content.Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		if (subject != null)
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		if (content != null)
			intent.putExtra(Intent.EXTRA_TEXT, content);
		activity.startActivity(Intent.createChooser(intent, title));
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the {@link String} will be shared, can be <code>null</code> but it no make sense
	 * @param subject
	 *            the {@link String} subject, can be <code>null</code>
	 * @param title
	 *            the title of dialog, is a reference to strings.xml
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, String content, String subject, int title) {
		shareWithChooser(activity, content, subject, activity.getString(title));
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the {@link String} will be shared, can be <code>null</code> but it no make sense
	 * @param subject
	 *            the subject, is a reference to strings.xml
	 * @param title
	 *            the {@link String} title of dialog, can be <code>null</code>
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, String content, int subject, String title) {
		shareWithChooser(activity, content, activity.getString(subject), title);
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the {@link String} will be shared, can be <code>null</code> but it no make sense
	 * @param subject
	 *            the subject, is a reference to strings.xml
	 * @param title
	 *            the title of dialog, is a reference to strings.xml
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, String content, int subject, int title) {
		shareWithChooser(activity, content, activity.getString(subject), activity.getString(title));
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the text will be shared, it is a reference to strings.xml
	 * @param subject
	 *            the {@link String} subject, can be <code>null</code>
	 * @param title
	 *            the {@link String} title of dialog, can be <code>null</code>
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, int content, String subject, String title) {
		shareWithChooser(activity, activity.getString(content), subject, title);
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the text will be shared, it is a reference to strings.xml
	 * @param subject
	 *            the {@link String} subject, can be <code>null</code>
	 * @param title
	 *            the title of dialog, is a reference to strings.xml
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, int content, String subject, int title) {
		shareWithChooser(activity, activity.getString(content), subject, activity.getString(title));
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the text will be shared, it is a reference to strings.xml
	 * @param subject
	 *            the subject, is a reference to strings.xml
	 * @param title
	 *            the {@link String} title of dialog, can be <code>null</code>
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, int content, int subject, String title) {
		shareWithChooser(activity, activity.getString(content), activity.getString(subject), title);
	}

	/**
	 * Share with the standard android share dialog any {@link String}
	 * 
	 * @param activity
	 *            needed to method {@link Activity#startActivity(Intent)}, it will start {@link Intent#createChooser(Intent, CharSequence)}
	 * @param content
	 *            the text will be shared, it is a reference to strings.xml
	 * @param subject
	 *            the subject, is a reference to strings.xml
	 * @param title
	 *            the title of dialog, is a reference to strings.xml
	 * @since {@link API#Version_2_0_0}
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void shareWithChooser(Activity activity, int content, int subject, int title) {
		shareWithChooser(activity, activity.getString(content), activity.getString(subject), activity.getString(title));
	}

}
