/**
 * 
 */
package simbio.se.sau.voicerecord;

import simbio.se.sau.API;
import android.view.View;

/**
 * The delegate to provide the {@link View} to be manipuled on {@link VoiceRecorderManager}
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Dec 9, 2013 6:25:29 PM
 * @since {@link API#Version_3_1_0}
 */
public interface VoiceRecorderDelegate {

	/**
	 * @return an array of {@link VoiceRecorderView} to receive the buffer readed
	 * @since {@link API#Version_3_1_0}
	 */
	public VoiceRecorderView[] getVoiceRecorderViews();

	/**
	 * called when record starts
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void onRecorderStarted();

	/**
	 * called when record stoped
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void onRecorderStoped();

	/**
	 * called when record ends
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void onRecorderEnded();

	/**
	 * called when record ends
	 * 
	 * @param exception
	 *            the {@link Exception} occurs
	 * @since {@link API#Version_3_1_1}
	 */
	public void onRecorderEndedWithFail(Exception exception);

}
