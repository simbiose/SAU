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
package simbio.se.sau.view.animation;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import simbio.se.sau.API;

/**
 * This class is an {@link Animation} extension to resize views with animation
 *
 * @author Ademar Alves de Oliveira <ademar111190@gmail.com>
 * @date 2013-sep-30 00:28:57
 * @see http://stackoverflow.com/questions/8140571/resizing-layouts-programatically-as-animation
 * @since {@link API#Version_2_0_0}
 */
public class ResizeAnimation extends Animation {

    private View view;
    private float fromHeight;
    private float toHeight;
    private float fromWidth;
    private float toWidth;
    private boolean shouldResizeHeight;
    private boolean shouldResizeWidth;
    private LayoutParams layoutParams;

    private static final float NAN = 0;

    /**
     * The complete constructor
     *
     * @param view       the {@link View} to be resized
     * @param fromHeight the initial height
     * @param toHeight   the final height
     * @param fromWidth  the initial width
     * @param toWidth    the final width
     * @since {@link API#Version_2_0_0}
     */
    public ResizeAnimation(
            View view,
            float fromHeight,
            float toHeight,
            float fromWidth,
            float toWidth
    ) {
        init(view, fromHeight, toHeight, fromWidth, toWidth, true, true);
    }

    /**
     * The constructor to resize only the height or only the width
     *
     * @param view         the {@link View} to be resized
     * @param from         the initial height or width
     * @param to           the final height or width
     * @param resizeHeight <code>true</code> to resize the height or <code>false</code> to resize
     *                     the width
     * @since {@link API#Version_2_0_0}
     */
    public ResizeAnimation(View view, float from, float to, boolean resizeHeight) {
        init(view,
                (resizeHeight
                        ? from
                        : NAN),
                (resizeHeight
                        ? to
                        : NAN),
                (!resizeHeight
                        ? from
                        : NAN),
                (!resizeHeight
                        ? to
                        : NAN),
                resizeHeight,
                !resizeHeight
        );
    }

    /**
     * The complete constructor, this constructor will set the initial height and the initial width
     * with current view height and width
     *
     * @param view     the {@link View} to be resized
     * @param toHeight the final height
     * @param toWidth  the final width
     * @since {@link API#Version_2_0_0}
     */
    public ResizeAnimation(View view, float toHeight, float toWidth) {
        init(view, view.getHeight(), toHeight, view.getWidth(), toWidth, true, true);
    }

    /**
     * The constructor to resize only the height or only the width, this constructor will set the
     * initial height or the initial width with current view height or width
     *
     * @param view         the {@link View} to be resized
     * @param to           the final height or width
     * @param resizeHeight <code>true</code> to resize the height or <code>false</code> to resize
     *                     the width
     * @since {@link API#Version_2_0_0}
     */
    public ResizeAnimation(View view, float to, boolean resizeHeight) {
        init(view,
                (resizeHeight
                        ? view.getHeight()
                        : NAN),
                (resizeHeight
                        ? to
                        : NAN),
                (!resizeHeight
                        ? view.getWidth()
                        : NAN),
                (!resizeHeight
                        ? to
                        : NAN),
                resizeHeight,
                !resizeHeight
        );
    }

    /**
     * Class fields start setter
     *
     * @param view               the {@link View} to be resized
     * @param fromHeight         the initial height
     * @param toHeight           the final height
     * @param fromWidth          the initial width
     * @param toWidth            the final width
     * @param shouldResizeHeight <code>true</code> to resize the height or <code>false</code> to not
     * @param shouldResizeWidth  <code>true</code> to resize the width or <code>false</code> to not
     * @since {@link API#Version_2_0_0}
     */
    private void init(
            View view,
            float fromHeight,
            float toHeight,
            float fromWidth,
            float toWidth,
            boolean shouldResizeHeight,
            boolean shouldResizeWidth
    ) {
        this.view = view;
        this.fromHeight = fromHeight;
        this.toHeight = toHeight;
        this.fromWidth = fromWidth;
        this.toWidth = toWidth;
        this.layoutParams = view.getLayoutParams();
        this.shouldResizeHeight = shouldResizeHeight;
        this.shouldResizeWidth = shouldResizeWidth;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.animation.Animation#applyTransformation(float,
     * android.view.animation.Transformation)
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (shouldResizeHeight)
            layoutParams.height = (int) ((toHeight - fromHeight) * interpolatedTime + fromHeight);
        if (shouldResizeWidth)
            layoutParams.width = (int) ((toWidth - fromWidth) * interpolatedTime + fromWidth);
        view.requestLayout();
    }

}
