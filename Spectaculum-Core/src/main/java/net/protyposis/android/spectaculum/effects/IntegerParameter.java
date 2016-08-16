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

/**
 * Created by maguggen on 21.08.2014.
 */
public class IntegerParameter extends Parameter {

    public interface Delegate {
        void setValue(int value);
    }

    private int mMin;
    private int mMax;
    private int mDefault;
    private int mValue;
    private Delegate mDelegate;

    public IntegerParameter(String name, int min, int max, int init, Delegate delegate, String description) {
        super(name, Type.INTEGER, description);
        mMin = min;
        mMax = max;
        mDefault = init;
        mValue = init;
        mDelegate = delegate;
    }

    public IntegerParameter(String name, int min, int max, int init, Delegate delegate) {
        this(name, min, max, init, delegate, null);
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
        setDelegateValue();
    }

    public int getMin() {
        return mMin;
    }

    public int getMax() {
        return mMax;
    }

    public int getDefault() {
        return mDefault;
    }

    @Override
    public void reset() {
        mValue = mDefault;
        setDelegateValue();
    }

    private void setDelegateValue() {
        mDelegate.setValue(mValue);
        fireParameterChanged();
    }
}
