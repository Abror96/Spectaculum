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

package net.protyposis.android.spectaculumdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import net.protyposis.android.spectaculum.CameraView;


public class GLCameraViewActivity extends Activity {

    private static final String TAG = GLCameraViewActivity.class.getSimpleName();

    private CameraView mGLCameraView;

    private GLEffects mEffectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glcameraview);

        mGLCameraView = (CameraView) findViewById(R.id.glcv);

        mEffectList = new GLEffects(this, R.id.parameterlist, mGLCameraView);
        mEffectList.addEffects();

        mGLCameraView.setOnFrameCapturedCallback(new Utils.OnFrameCapturedCallback(this, "glcameraview"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.glvideoview, menu);
        mEffectList.addToMenu(menu);
        menu.findItem(R.id.action_switch_camera).setVisible(mGLCameraView.supportsCameraSwitch());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(mEffectList.doMenuActions(item)) {
            return true;
        } else if(id == R.id.action_save_frame) {
            mGLCameraView.captureFrame();
        } else if(id == R.id.action_switch_camera) {
            mGLCameraView.switchCamera();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLCameraView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLCameraView.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // hand the event to the video view to process zoom/pan gestures
        mGLCameraView.onTouchEvent(event);

        return super.onTouchEvent(event);
    }
}
