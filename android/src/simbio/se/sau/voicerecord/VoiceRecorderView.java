/**
 * 
 */
package simbio.se.sau.voicerecord;

import simbio.se.sau.API;
import android.view.View;

/**
 * Any {@link View} can be used to process animation based on sound, implements this interface is the way to do that
 * 
 * @author Ademar Alves de Oliveira (ademar111190@gmail.com)
 * @date Dec 10, 2013 8:47:21 PM
 * @since {@link API#Version_3_1_0}
 */
public interface VoiceRecorderView {

	/**
	 * @param volume
	 *            the volume
	 * @since {@link API#Version_3_1_0}
	 */
	public void processSoundVolume(float volume);

	/**
	 * Called when the record has stopped
	 * 
	 * @since {@link API#Version_3_1_0}
	 */
	public void processEnded();

}
