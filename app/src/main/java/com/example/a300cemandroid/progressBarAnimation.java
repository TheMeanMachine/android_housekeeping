package com.example.a300cemandroid;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

public class progressBarAnimation extends Animation {
    private ProgressBar progressBar;
    private float from;
    private float  to;

    /**
     *
     * @param progressBar The progress bar to animate
     * @param from value from
     * @param to value to
     */
    public progressBarAnimation(ProgressBar progressBar, float from, float to) {
        super();
        this.progressBar = progressBar;
        this.from = from;
        this.to = to;
    }

    /**
     * Applies the transformation to the progressbar
     * @param interpolatedTime - time to calculate speed
     * @param t tranformation class
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        progressBar.setProgress((int) value);
    }
}
