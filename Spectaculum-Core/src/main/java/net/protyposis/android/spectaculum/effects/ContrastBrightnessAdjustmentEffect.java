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

package net.protyposis.android.spectaculum.effects;

import net.protyposis.android.spectaculum.gles.ContrastBrightnessAdjustmentShaderProgram;
import net.protyposis.android.spectaculum.gles.TextureShaderProgram;

/**
 * Created by Mario on 02.10.2014.
 */
public class ContrastBrightnessAdjustmentEffect extends ShaderEffect {

    private float mContrast;
    private float mBrightness;

    @Override
    protected TextureShaderProgram initShaderProgram() {
        final ContrastBrightnessAdjustmentShaderProgram adjustmentsShader = new ContrastBrightnessAdjustmentShaderProgram();

        mContrast = 1.0f;
        mBrightness = 1.0f;

        addParameter(new FloatParameter("Contrast", 0.0f, 5.0f, mContrast, new FloatParameter.Delegate() {
            @Override
            public void setValue(float value) {
                mContrast = value;
                adjustmentsShader.setContrast(mContrast);
            }
        }));
        addParameter(new FloatParameter("Brightness", 0.0f, 5.0f, mBrightness, new FloatParameter.Delegate() {
            @Override
            public void setValue(float value) {
                mBrightness = value;
                adjustmentsShader.setBrightness(mBrightness);
            }
        }));

        return adjustmentsShader;
    }
}
