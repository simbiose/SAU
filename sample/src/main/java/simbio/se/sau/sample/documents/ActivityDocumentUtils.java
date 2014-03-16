/**
 * 
 */
package simbio.se.sau.sample.documents;

import simbio.se.sau.activities.ActivityUtils;
import simbio.se.sau.sample.R;
import simbio.se.sau.widget.ToastMaker;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

/**
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Nov 27, 2013 3:38:42 PM
 * 
 */
public class ActivityDocumentUtils extends Activity {

	private ActivityUtils activityUtils;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_documents);
		activityUtils = new ActivityUtils(this);

		// we need files on sdcard, so we go copy from asses to a folder called sauDocSample on sdcard
		path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sauDocSample/";
		try {
			activityUtils.copyAssetsFilesToFolder(path);
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

	public void openTxt(View view) {
		try {
			activityUtils.openDocumentWithCorrectActivity(path + "text.txt");
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

	public void openOds(View view) {
		try {
			activityUtils.openDocumentWithCorrectActivity(path + "sinonimos.ods");
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

	public void openOgg(View view) {
		try {
			activityUtils.openDocumentWithCorrectActivity(path + "theme.ogg");
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

	public void openVoid(View view) {
		try {
			activityUtils.openDocumentWithCorrectActivity(path + "void");
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

	public void openNull(View view) {
		try {
			activityUtils.openDocumentWithCorrectActivity(null);
		} catch (Exception e) {
			ToastMaker.toast(getApplicationContext(), e);
		}
	}

}
