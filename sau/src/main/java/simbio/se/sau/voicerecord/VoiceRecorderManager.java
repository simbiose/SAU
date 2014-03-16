/**
 * 
 */
package simbio.se.sau.voicerecord;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayDeque;

import simbio.se.sau.API;
import android.annotation.TargetApi;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder.AudioSource;
import android.os.Build;
import android.os.Handler;

/**
 * A record Thread
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Dec 9, 2013 6:05:10 PM
 * @since {@link API#Version_3_1_0}
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class VoiceRecorderManager implements Runnable {

	private static final long WRITE_INTERVAL = 1000l;
	private boolean stopRequired;
	private String filePathToSave;
	private VoiceRecorderDelegate delegate;
	private Handler handler;
	private float minimunVolume = 1.0f;
	private int minimunVolumeSequenceLimite = 100;
	private int minimunVolumeSequenceCount = 0;
	private boolean sholdRecord = false;
	private DataOutputStream dataOutputStream = null;
	private ArrayDeque<Short> arrayDeque = new ArrayDeque<Short>();
	private ArrayDeque<Short> arrayDequeNeedAproval = new ArrayDeque<Short>();
	private boolean speakStarted = false;
	private Thread mainThread;
	private Thread recordFileThread;
	private Runnable recordFileRunnable = new Runnable() {
		@Override
		public void run() {
			while (true) {
				while (!arrayDeque.isEmpty())
					try {
						dataOutputStream.writeShort(arrayDeque.pop());
					} catch (Exception e) {
						e.printStackTrace();
					}
				try {
					Thread.sleep(WRITE_INTERVAL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};

	/**
	 * Default constructor
	 * 
	 * @param filePathToSave
	 *            the path to save the audio, send <code>null</code> to not save
	 * @param delegate
	 *            the {@link VoiceRecorderDelegate} to send itens to {@link VoiceRecorderManager}, can be <code>null</code>
	 * @since {@link API#Version_3_1_0}
	 */
	public VoiceRecorderManager(String filePathToSave, VoiceRecorderDelegate delegate) {
		this.filePathToSave = filePathToSave;
		this.delegate = delegate;
		this.handler = new Handler();
	}

	/**
	 * start the {@link Thread} to get audio, the {@link Thread} to make animations and the {@link Thread} to write the audio on storage
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void start() {
		stopRequired = false;
		speakStarted = false;
		arrayDeque.clear();
		arrayDequeNeedAproval.clear();
		mainThread = new Thread(this);
		mainThread.start();
		recordFileThread = new Thread(recordFileRunnable);
		recordFileThread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (delegate != null)
			handler.post(new Runnable() {
				@Override
				public void run() {
					delegate.onRecorderStarted();
				}
			});
		VoiceRecorderView[] voiceRecorderViews = (delegate == null ? null : delegate.getVoiceRecorderViews());
		if (voiceRecorderViews != null)
			for (VoiceRecorderView voiceRecorderView : voiceRecorderViews)
				if (voiceRecorderView != null)
					voiceRecorderView.processStarted();
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		AudioRecord audioRecord = null;
		if (filePathToSave != null) {
			try {
				File file = new File(filePathToSave);
				if (file.exists())
					file.delete();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				dataOutputStream = new DataOutputStream(fileOutputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		short[][] buffers = new short[256][160];
		int ix = 0;
		float volume;
		sholdRecord = dataOutputStream != null;
		try {
			int readSize = AudioRecord.getMinBufferSize(11025, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
			audioRecord = new AudioRecord(AudioSource.MIC, 11025, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, readSize * 10);
			audioRecord.startRecording();
			while (!stopRequired) {
				if (!speakStarted)
					arrayDequeNeedAproval.clear();
				short[] buffer = buffers[ix++ % buffers.length];
				readSize = audioRecord.read(buffer, 0, buffer.length);
				volume = 0.0f;
				for (short bufferIten : buffer) {
					if (sholdRecord)
						arrayDequeNeedAproval.add(bufferIten);
					volume += bufferIten * bufferIten;
				}
				volume = (float) Math.sqrt(volume / readSize) / 2000.0f;
				if (speakStarted && voiceRecorderViews != null && readSize > 0)
					for (VoiceRecorderView voiceRecorderView : voiceRecorderViews)
						if (voiceRecorderView != null)
							voiceRecorderView.processSoundVolume(volume);
				if (volume < minimunVolume)
					minimunVolumeSequenceCount++;
				else {
					minimunVolumeSequenceCount = 0;
					arrayDeque.addAll(arrayDequeNeedAproval);
					arrayDequeNeedAproval.clear();
					if (!speakStarted && voiceRecorderViews != null)
						for (VoiceRecorderView voiceRecorderView : voiceRecorderViews)
							if (voiceRecorderView != null)
								voiceRecorderView.processHasTheFirstSong();
					speakStarted = true;
				}
				if (minimunVolumeSequenceCount > minimunVolumeSequenceLimite)
					stopRecordIfNeed();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (delegate != null)
				handler.post(new Runnable() {
					@Override
					public void run() {
						delegate.onRecorderStoped();
					}
				});
			if (voiceRecorderViews != null)
				for (VoiceRecorderView voiceRecorderView : voiceRecorderViews)
					if (voiceRecorderView != null)
						voiceRecorderView.processHasTheLastSong();
			if (audioRecord != null && audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
			}
			if (sholdRecord) {
				try {
					while (!arrayDeque.isEmpty())
						Thread.sleep(WRITE_INTERVAL / 100l);
					dataOutputStream.flush();
					dataOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// TODO compress audio
			if (voiceRecorderViews != null)
				for (VoiceRecorderView voiceRecorderView : voiceRecorderViews)
					if (voiceRecorderView != null)
						voiceRecorderView.processEnded();
			if (delegate != null)
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (speakStarted)
							delegate.onRecorderEnded();
						else
							delegate.onRecorderEndedWithFail(new FileNotFoundException("Nothing speaked"));
					}
				});
			mainThread = null;
			recordFileThread = null;
		}
	}

	/**
	 * stop the record if need
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void stopRecordIfNeed() {
		stopRequired = true;
	}

}