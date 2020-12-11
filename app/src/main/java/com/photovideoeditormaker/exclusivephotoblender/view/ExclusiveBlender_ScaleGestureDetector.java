package com.photovideoeditormaker.exclusivephotoblender.view;

import android.view.MotionEvent;
import android.view.View;

public class ExclusiveBlender_ScaleGestureDetector {
    private static final float PRESSURE_THRESHOLD = 0.67f;
    private boolean mActive0MostRecent;
    private int mActiveId0;
    private int mActiveId1;
    private MotionEvent mCurrEvent;
    private float mCurrFingerDiffX;
    private float mCurrFingerDiffY;
    private float mCurrLen;
    private float mCurrPressure;
    private ExclusiveBlender_Vector2D mCurrSpanVector = new ExclusiveBlender_Vector2D(0.0f,0.0f);
    private float mFocusX;
    private float mFocusY;
    private boolean mGestureInProgress;
    private boolean mInvalidGesture;
    private final OnScaleGestureListener mListener;
    private MotionEvent mPrevEvent;
    private float mPrevFingerDiffX;
    private float mPrevFingerDiffY;
    private float mPrevLen;
    private float mPrevPressure;
    private float mScaleFactor;
    private long mTimeDelta;

    public interface OnScaleGestureListener {
        boolean onScale(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector);

        boolean onScaleBegin(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector);

        void onScaleEnd(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector);
    }

    public static class SimpleOnScaleGestureListener implements OnScaleGestureListener {
        public boolean onScale(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector) {
            return false;
        }

        public boolean onScaleBegin(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector) {
            return true;
        }

        public void onScaleEnd(View view, ExclusiveBlender_ScaleGestureDetector exclusiveBlender_ScaleGestureDetector) {
        }
    }

    public ExclusiveBlender_ScaleGestureDetector(OnScaleGestureListener onScaleGestureListener) {
        this.mListener = onScaleGestureListener;
    }

    private int findNewActiveIndex(MotionEvent motionEvent, int i, int i2) {
        int pointerCount = motionEvent.getPointerCount();
        int findPointerIndex = motionEvent.findPointerIndex(i);
        int i3 = 0;
        while (i3 < pointerCount) {
            if (i3 != i2 && i3 != findPointerIndex) {
                return i3;
            }
            i3++;
        }
        return -1;
    }

    private void reset() {
        if (this.mPrevEvent != null) {
            this.mPrevEvent.recycle();
            this.mPrevEvent = null;
        }
        if (this.mCurrEvent != null) {
            this.mCurrEvent.recycle();
            this.mCurrEvent = null;
        }
        this.mGestureInProgress = false;
        this.mActiveId0 = -1;
        this.mActiveId1 = -1;
        this.mInvalidGesture = false;
    }

    private void setContext(View view, MotionEvent motionEvent) {
        if (this.mCurrEvent != null) {
            this.mCurrEvent.recycle();
        }
        this.mCurrEvent = MotionEvent.obtain(motionEvent);
        this.mCurrLen = -1.0f;
        this.mPrevLen = -1.0f;
        this.mScaleFactor = -1.0f;
        this.mCurrSpanVector.set(0.0f, 0.0f);
        MotionEvent motionEvent2 = this.mPrevEvent;
        int findPointerIndex = motionEvent2.findPointerIndex(this.mActiveId0);
        int findPointerIndex2 = motionEvent2.findPointerIndex(this.mActiveId1);
        int findPointerIndex3 = motionEvent.findPointerIndex(this.mActiveId0);
        int findPointerIndex4 = motionEvent.findPointerIndex(this.mActiveId1);
        if (findPointerIndex < 0 || findPointerIndex2 < 0 || findPointerIndex3 < 0 || findPointerIndex4 < 0) {
            this.mInvalidGesture = true;
            if (this.mGestureInProgress) {
                this.mListener.onScaleEnd(view, this);
                return;
            }
            return;
        }
        float x = motionEvent2.getX(findPointerIndex);
        float y = motionEvent2.getY(findPointerIndex);
        float x2 = motionEvent2.getX(findPointerIndex2);
        float y2 = motionEvent2.getY(findPointerIndex2);
        float x3 = motionEvent.getX(findPointerIndex3);
        float y3 = motionEvent.getY(findPointerIndex3);
        x = x2 - x;
        y = y2 - y;
        x2 = motionEvent.getX(findPointerIndex4) - x3;
        y2 = motionEvent.getY(findPointerIndex4) - y3;
        this.mCurrSpanVector.set(x2, y2);
        this.mPrevFingerDiffX = x;
        this.mPrevFingerDiffY = y;
        this.mCurrFingerDiffX = x2;
        this.mCurrFingerDiffY = y2;
        this.mFocusX = (0.5f * x2) + x3;
        this.mFocusY = (0.5f * y2) + y3;
        this.mTimeDelta = motionEvent.getEventTime() - motionEvent2.getEventTime();
        this.mCurrPressure = motionEvent.getPressure(findPointerIndex3) + motionEvent.getPressure(findPointerIndex4);
        this.mPrevPressure = motionEvent2.getPressure(findPointerIndex2) + motionEvent2.getPressure(findPointerIndex);
    }

    public float getCurrentSpan() {
        if (this.mCurrLen == -1.0f) {
            float f = this.mCurrFingerDiffX;
            float f2 = this.mCurrFingerDiffY;
            this.mCurrLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
        return this.mCurrLen;
    }

    public ExclusiveBlender_Vector2D getCurrentSpanVector() {
        return this.mCurrSpanVector;
    }

    public float getCurrentSpanX() {
        return this.mCurrFingerDiffX;
    }

    public float getCurrentSpanY() {
        return this.mCurrFingerDiffY;
    }

    public long getEventTime() {
        return this.mCurrEvent.getEventTime();
    }

    public float getFocusX() {
        return this.mFocusX;
    }

    public float getFocusY() {
        return this.mFocusY;
    }

    public float getPreviousSpan() {
        if (this.mPrevLen == -1.0f) {
            float f = this.mPrevFingerDiffX;
            float f2 = this.mPrevFingerDiffY;
            this.mPrevLen = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        }
        return this.mPrevLen;
    }

    public float getPreviousSpanX() {
        return this.mPrevFingerDiffX;
    }

    public float getPreviousSpanY() {
        return this.mPrevFingerDiffY;
    }

    public float getScaleFactor() {
        if (this.mScaleFactor == -1.0f) {
            this.mScaleFactor = getCurrentSpan() / getPreviousSpan();
        }
        return this.mScaleFactor;
    }

    public long getTimeDelta() {
        return this.mTimeDelta;
    }

    public boolean isInProgress() {
        return this.mGestureInProgress;
    }

    public boolean onTouchEvent(View view, MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset();
        }
        if (this.mInvalidGesture) {
            return false;
        }
        int i;
        if (this.mGestureInProgress) {
            switch (actionMasked) {
                case 1:
                    reset();
                    return true;
                case 2:
                    setContext(view, motionEvent);
                    if (this.mCurrPressure / this.mPrevPressure <= PRESSURE_THRESHOLD || !this.mListener.onScale(view, this)) {
                        return true;
                    }
                    this.mPrevEvent.recycle();
                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    return true;
                case 3:
                    this.mListener.onScaleEnd(view, this);
                    reset();
                    return true;
                case 5:
                    this.mListener.onScaleEnd(view, this);
                    i = this.mActiveId0;
                    actionMasked = this.mActiveId1;
                    reset();
                    this.mPrevEvent = MotionEvent.obtain(motionEvent);
                    if (this.mActive0MostRecent) {
                        actionMasked = i;
                    }
                    this.mActiveId0 = actionMasked;
                    this.mActiveId1 = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.mActive0MostRecent = false;
                    if (motionEvent.findPointerIndex(this.mActiveId0) < 0 || this.mActiveId0 == this.mActiveId1) {
                        this.mActiveId0 = motionEvent.getPointerId(findNewActiveIndex(motionEvent, this.mActiveId1, -1));
                    }
                    setContext(view, motionEvent);
                    this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                    return true;
                case 6:
                    boolean z;
                    actionMasked = motionEvent.getPointerCount();
                    i = motionEvent.getActionIndex();
                    int pointerId = motionEvent.getPointerId(i);
                    if (actionMasked > 2) {
                        if (pointerId == this.mActiveId0) {
                            actionMasked = findNewActiveIndex(motionEvent, this.mActiveId1, i);
                            if (actionMasked >= 0) {
                                this.mListener.onScaleEnd(view, this);
                                this.mActiveId0 = motionEvent.getPointerId(actionMasked);
                                this.mActive0MostRecent = true;
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                setContext(view, motionEvent);
                                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                                z = false;
                            } else {
                                z = true;
                            }
                        } else if (pointerId == this.mActiveId1) {
                            actionMasked = findNewActiveIndex(motionEvent, this.mActiveId0, i);
                            if (actionMasked >= 0) {
                                this.mListener.onScaleEnd(view, this);
                                this.mActiveId1 = motionEvent.getPointerId(actionMasked);
                                this.mActive0MostRecent = false;
                                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                                setContext(view, motionEvent);
                                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                                z = false;
                            } else {
                                z = true;
                            }
                        } else {
                            z = false;
                        }
                        this.mPrevEvent.recycle();
                        this.mPrevEvent = MotionEvent.obtain(motionEvent);
                        setContext(view, motionEvent);
                    } else {
                        z = true;
                    }
                    if (!z) {
                        return true;
                    }
                    setContext(view, motionEvent);
                    actionMasked = pointerId == this.mActiveId0 ? this.mActiveId1 : this.mActiveId0;
                    i = motionEvent.findPointerIndex(actionMasked);
                    this.mFocusX = motionEvent.getX(i);
                    this.mFocusY = motionEvent.getY(i);
                    this.mListener.onScaleEnd(view, this);
                    reset();
                    this.mActiveId0 = actionMasked;
                    this.mActive0MostRecent = true;
                    return true;
                default:
                    return true;
            }
        }
        switch (actionMasked) {
            case 0:
                this.mActiveId0 = motionEvent.getPointerId(0);
                this.mActive0MostRecent = true;
                return true;
            case 1:
                reset();
                return true;
            case 5:
                if (this.mPrevEvent != null) {
                    this.mPrevEvent.recycle();
                }
                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                this.mTimeDelta = 0;
                actionMasked = motionEvent.getActionIndex();
                i = motionEvent.findPointerIndex(this.mActiveId0);
                this.mActiveId1 = motionEvent.getPointerId(actionMasked);
                if (i < 0 || i == actionMasked) {
                    this.mActiveId0 = motionEvent.getPointerId(findNewActiveIndex(motionEvent, this.mActiveId1, -1));
                }
                this.mActive0MostRecent = false;
                setContext(view, motionEvent);
                this.mGestureInProgress = this.mListener.onScaleBegin(view, this);
                return true;
            default:
                return true;
        }
    }
}
