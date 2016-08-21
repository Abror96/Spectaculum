/*
 * Copyright (c) 2014 Mario Guggenberger <mg@protyposis.net>
 *
 * This file is part of Spectaculum-Effect-FlowAbs.
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

import net.protyposis.android.spectaculum.gles.Framebuffer;
import net.protyposis.android.spectaculum.gles.Texture2D;

/**
 * Created by Mario on 18.07.2014.
 */
public class FlowAbsTangentFlowMapEffect extends FlowAbsSubEffect {

    private float mSigma;

    FlowAbsTangentFlowMapEffect() {
        super();
        mSigma = 2.0f;

        addParameter(new FloatParameter("Sigma", 0f, 10f, mSigma, new FloatParameter.Delegate() {
            @Override
            public void setValue(Float value) {
                mSigma = value;
            }
        }));
    }

    @Override
    public void apply(Texture2D source, Framebuffer target) {
        mFlowAbsEffect.mFlowAbs.tangentFlowMap(source, target, mSigma);
    }
}
