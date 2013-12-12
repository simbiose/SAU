/**
 * 
 */
package simbio.se.sau.sample.record;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import simbio.se.sau.sample.R;
import simbio.se.sau.view.CircleVoiceRecorderView;
import simbio.se.sau.voicerecord.VoiceRecorderDelegate;
import simbio.se.sau.voicerecord.VoiceRecorderManager;
import simbio.se.sau.voicerecord.VoiceRecorderView;
import simbio.se.sau.widget.ToastMaker;
import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

/**
 * Example of how to use {@link VoiceRecorderManager}
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Dec 11, 2013 12:36:18 AM
 */
public class RecordActivity extends Activity implements VoiceRecorderDelegate, OnCompletionListener {

	private CircleVoiceRecorderView[] circleVoiceRecorderViews;
	private VoiceRecorderManager voiceRecorderManager;
	private String filePath;
	private MediaPlayer mediaPlayerBeepStart;
	private MediaPlayer mediaPlayerBeepEnd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record);

		circleVoiceRecorderViews = new CircleVoiceRecorderView[3];
		circleVoiceRecorderViews[0] = (CircleVoiceRecorderView) findViewById(R.id.circle_0);
		circleVoiceRecorderViews[1] = (CircleVoiceRecorderView) findViewById(R.id.circle_1);
		circleVoiceRecorderViews[2] = (CircleVoiceRecorderView) findViewById(R.id.circle_2);
		String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/sauRecSample";
		File file = new File(folderPath);
		if (!file.exists())
			file.mkdirs();
		filePath = folderPath + "/recorded.pcm";

		mediaPlayerBeepStart = MediaPlayer.create(getApplicationContext(), R.raw.beep);
		mediaPlayerBeepEnd = MediaPlayer.create(getApplicationContext(), R.raw.beep);

		mediaPlayerBeepStart.setOnCompletionListener(this);
	}

	public void record(View view) {
		if (!mediaPlayerBeepStart.isPlaying())
			mediaPlayerBeepStart.start();
	}

	public void stopRec(View view) {
		voiceRecorderManager.stopRecordIfNeed();
	}

	@Override
	protected void onPause() {
		voiceRecorderManager.stopRecordIfNeed();
		super.onPause();
	}

	@TargetApi(Build.VERSION_CODES.ECLAIR)
	public void play(View view) {
		File file = new File(filePath);
		int musicLength = (int) (file.length() / 2);
		short[] music = new short[musicLength];
		try {
			InputStream is = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			DataInputStream dis = new DataInputStream(bis);
			int i = 0;
			while (dis.available() > 0) {
				music[i] = dis.readShort();
				i++;
			}
			dis.close();
			AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 11025, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, musicLength, AudioTrack.MODE_STREAM);
			audioTrack.play();
			audioTrack.write(music, 0, musicLength);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public VoiceRecorderView[] getVoiceRecorderViews() {
		return circleVoiceRecorderViews;
	}

	@Override
	public void onRecorderStarted() {
		ToastMaker.toast(getApplicationContext(), R.string.record_started);
	}

	@Override
	public void onRecorderStoped() {
		if (!mediaPlayerBeepEnd.isPlaying())
			mediaPlayerBeepEnd.start();
	}

	@Override
	public void onRecorderEnded() {
		ToastMaker.toast(getApplicationContext(), R.string.record_ended);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		voiceRecorderManager = new VoiceRecorderManager(filePath, this);
		voiceRecorderManager.start();
	}

}
