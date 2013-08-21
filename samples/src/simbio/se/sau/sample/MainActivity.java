package simbio.se.sau.sample;

import simbio.se.sau.log.SimbiLog;
import simbio.se.sau.share.SimbiShare;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * @author Ademar Alves de Oliveira <ademar111190@gmail.com>
 * @date 2013-aŭg-21 06:02:21
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SimbiLog.log(this, savedInstanceState);

		setContentView(R.layout.activity_main);
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

}
