/**
 * Copyright 2013-2014
 * Ademar Alves de Oliveira <ademar111190@gmail.com /> Simbio.se
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package simbio.se.sau.voicerecord;

import android.view.View;

import simbio.se.sau.API;

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
     * @param exception the {@link Exception} occurs
     * @since {@link API#Version_3_1_1}
     */
    public void onRecorderEndedWithFail(Exception exception);

}
