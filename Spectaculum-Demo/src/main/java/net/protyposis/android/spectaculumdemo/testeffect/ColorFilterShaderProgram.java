/*
 * Copyright (c) 2014 Mario Guggenberger <mg@protyposis.net>
 *
 * This file is part of MediaPlayer-Extended.
 *
 * MediaPlayer-Extended is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MediaPlayer-Extended is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MediaPlayer-Extended.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.protyposis.android.spectaculumdemo.testeffect;

import android.opengl.GLES20;

import net.protyposis.android.spectaculum.gles.GLUtils;
import net.protyposis.android.spectaculum.gles.TextureShaderProgram;

/**
 * Created by Mario on 19.07.2014.
 */
public class ColorFilterShaderProgram extends TextureShaderProgram {

    protected int mColorHandle;

    public ColorFilterShaderProgram() {
        super("fs_colorfilter.s");

        mColorHandle = GLES20.glGetUniformLocation(mProgramHandle, "color");
        GLUtils.checkError("glGetUniformLocation color");
    }

    public void setColor(float r, float g, float b, float a) {
        use();
        GLES20.glUniform4f(mColorHandle, r, g, b, a);
    }
}
